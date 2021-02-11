package org.rfc.material.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.rfc.material.BAPIField;
import org.rfc.material.MaterialService;
import org.rfc.material.dto.FieldValueDTO;
import org.rfc.material.dto.MaterialDTO;
import org.rfc.material.dto.WorkerDTO;
import org.rfc.material.dto.WorkerResultDTO;
import org.rfc.material.run.Run;
import org.rfc.material.run.RunRepository;
import org.rfc.material.rundata.RunData;
import org.rfc.material.rundata.RunDataRepository;
import org.rfc.material.runmaterial.RunMaterial;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.rfc.material.template.TemplateValue;
import org.rfc.material.template.TemplateValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("workerService")
public class WorkerServiceImpl implements WorkerService {
	
	private ExecutorService executor;
	private Map<Integer,Callable<WorkerResultDTO>> workers=null;
	private ResultHandler resultHandler=null;
	
	private final static String STRUCTURES[]= {
			"HEADDATA",
			"CLIENTDATA",
			"MATERIALDESCRIPTION",
			"UNITSOFMEASURE",
			"SALESDATA",
			"TAXCLASSIFICATIONS",
			"PLANTDATA",
			"VALUATIONDATA",
			"STORAGELOCATIONDATA",
			"FORECASTPARAMETERS"
		};
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private RunRepository runRepo;
	
	@Autowired
	private RunMaterialRepository runMaterialRepo;
	
	@Autowired
	private TemplateValueRepository templateValueRepo;
	
	@Autowired
	private RunDataRepository runDataRepo;
	
	@Override
	public List<WorkerDTO> createWorkers(int runId, int maxMaterials) {
		List<RunMaterial> materials=runMaterialRepo.findByIdRunIdAndStatus(runId, 0);
		Optional<Run> opt=runRepo.findById(runId);
		int templateId=-1;
		if(opt.isPresent()) {
			templateId=opt.get().getTemplateId();
		}
		List<TemplateValue> templateValues=templateValueRepo.findByKeyTemplateId(templateId);
		
		System.out.println("No# run materials: "+materials.size());
		System.out.println("TemplateValues "+templateValues.size());
		
		Map<String,Map<Integer,String>> materialValuesMap=this.createMaterialValuesMap(runId);
		Map<String,List<Map<String,FieldValueDTO>>> templateValuesMap=createTemplateValuesMap(templateId);
		
		List<MaterialDTO> allMaterials=createMaterialList(materialValuesMap,templateValuesMap);
		for(MaterialDTO m : allMaterials) {
			System.out.println(m.getMaterialId());
		}
		System.out.println("All materials: "+allMaterials.size());
		
		List<WorkerDTO> dtos=getTestWorkerList();
		executor=Executors.newFixedThreadPool(dtos.size()+1);
		resultHandler=new ResultHandler();
		executor.submit(resultHandler);
		workers=new HashMap<Integer,Callable<WorkerResultDTO>>();
		for(WorkerDTO w : dtos) {
			workers.put(w.getId(), new CreateMaterialWorker(w.getId(),1,null,resultHandler));
		}
		return dtos;
	}
	
	private Map<String,List<Map<String,FieldValueDTO>>> createTemplateValuesMap(int templateId){
		
		Map<String,List<Map<String,FieldValueDTO>>> templateValuesMap=new HashMap<String,List<Map<String,FieldValueDTO>>>();
		
		for(int i=0;i<STRUCTURES.length;i++) {
			templateValuesMap.put(STRUCTURES[i],createRowList(templateId,STRUCTURES[i]));
		}
		
		return templateValuesMap;
	}
	
	private List<Map<String,FieldValueDTO>> createRowList(int templateId, String structure){
		Integer rowIndexes[]=templateValueRepo.getTableRowIndexes(templateId, structure);
		List<Map<String,FieldValueDTO>> rows=new ArrayList<Map<String,FieldValueDTO>>(); 
		for(int i=0;i<rowIndexes.length;i++) {
			List<TemplateValue> templateValues=templateValueRepo.findByKeyTemplateIdAndKeyBapiStructureAndKeyRowId(templateId, structure,rowIndexes[i]);
			Map<String,FieldValueDTO> rowMap=new HashMap<String,FieldValueDTO>();
			for(TemplateValue tv : templateValues) {
				rowMap.put(tv.getKey().getBapiField(),new FieldValueDTO(tv));
			}
			rows.add(rowMap);
		}
		return rows;
	}
	
	private Map<String,Map<Integer,String>> createMaterialValuesMap(int runId){
		List<RunData> runDataList=runDataRepo.findByIdRunId(runId);
		Map<String,Map<Integer,String>> materialValuesMap=new HashMap<String,Map<Integer,String>>();
		String material=null;
		Map<Integer,String> fieldValueMap=null;
		for(RunData rd : runDataList) {
			int fieldIndex=rd.getId().getFieldIndex();
			String value=rd.getValue();
			if(fieldIndex==0) {
				material=value;
				fieldValueMap=new HashMap<Integer,String>();
				materialValuesMap.put(material, fieldValueMap);
			}
			fieldValueMap.put(fieldIndex,value);
		}
		return materialValuesMap;
	}
	
	private List<MaterialDTO> createMaterialList(Map<String,Map<Integer,String>> materialValuesMap,Map<String,List<Map<String,FieldValueDTO>>> templateValuesMap){
		Set<String> materialCodes=materialValuesMap.keySet();
		List<MaterialDTO> materials=new ArrayList<MaterialDTO>();
		MaterialDTO m=null;
		for(String mc : materialCodes) {
			m=new MaterialDTO();
			Map<Integer,String> variables=materialValuesMap.get(mc);
			for(int i=0;i<STRUCTURES.length;i++) {
				List<Map<String,FieldValueDTO>> rows=templateValuesMap.get(STRUCTURES[i]);
				for(Map<String,FieldValueDTO> row : rows) {
					fillValues(m,STRUCTURES[i],row,variables);
				}
				
			}
			materials.add(m);
			
		}
		return materials;
	}
	
	private void fillValues(MaterialDTO m,String structure,Map<String,FieldValueDTO> templateFields,Map<Integer,String> fieldValues) {
		FieldValueDTO tf=null;
		String val;
		Map<String,List<BAPIField>> fieldMap=materialService.getFieldMap();
		List<BAPIField> fields=fieldMap.get(structure);
		Map<String,FieldValueDTO> rowData=new HashMap<String,FieldValueDTO>();
		FieldValueDTO fieldData=null;
		for(BAPIField field : fields) {
			tf=templateFields.get(field.getFieldName());
			if(tf.getValueType().equals("CONSTANT")){
				val=tf.getValue();
			}
			else {
				Integer index=Integer.parseInt(tf.getValue());
				val=fieldValues.get(index);
			}
			//System.out.println(structure+"-"+field.getFieldName()+"\t"+val);
			fieldData=new FieldValueDTO(tf.getField(),tf.getValueType(),val);
			rowData.put(fieldData.getField(), fieldData);
		}
		m.setRowData(structure, rowData);
	}
	
	private List<WorkerDTO> getTestWorkerList() {
		List<WorkerDTO> workers=new ArrayList<WorkerDTO>();
		workers.add(new WorkerDTO(1,5000,0,0,0));
		workers.add(new WorkerDTO(2,5000,0,0,0));
		workers.add(new WorkerDTO(3,5000,0,0,0));
		workers.add(new WorkerDTO(4,5000,0,0,0));
		return workers;
	}

	@Override
	public void startOne(int workerId) {
		// TODO Auto-generated method stub
		Future<WorkerResultDTO> result=executor.submit(workers.get(workerId));
	}

	@Override
	public void startAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopOne(int workerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopAll() {
		// TODO Auto-generated method stub
		
	}

}
