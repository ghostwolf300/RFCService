package org.rfc.material;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.rfc.material.dto.FieldValueDTO;
import org.rfc.material.dto.HeaderColumnDTO;
import org.rfc.material.dto.TemplateDTO;
import org.rfc.material.run.Run;
import org.rfc.material.run.RunRepository;
import org.rfc.material.runmaterial.RunMaterial;
import org.rfc.material.runmaterial.RunMaterialRepository;
import org.rfc.material.template.Template;
import org.rfc.material.template.TemplateRepository;
import org.rfc.material.template.TemplateValue;
import org.rfc.material.template.TemplateValueRepository;
import org.rfc.material.dto.ResponseDTO;
import org.rfc.material.dto.RunDTO;
import org.rfc.material.dto.RunDataRowDTO;
import org.rfc.material.dto.RunDataWrapperDTO;
import org.rfc.material.dto.RunMaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
	
	private Map<String,List<BAPIField>> fieldMap;
	
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
	private TemplateRepository templateRepo;
	
	@Autowired
	private TemplateValueRepository templateValueRepo;
	
	@Autowired
	private RunRepository runRepo;
	
	@Autowired
	private RunMaterialRepository runMaterialRepo;
	
	@Autowired
	private MaterialRepository materialRepo;
	
	
	
	public Map<String,List<BAPIField>> getFieldMap(){
		if(fieldMap==null) {
			initFieldMap();
		}
		return fieldMap;
	}
	
	private void initFieldMap() {
		fieldMap=new HashMap<String,List<BAPIField>>();
		
		List<BAPIField> fieldsHEADDATA=new ArrayList<BAPIField>();
		fieldsHEADDATA.add(new BAPIField("MATERIAL","material",18));
		fieldsHEADDATA.add(new BAPIField("IND_SECTOR","indSector",1));
		fieldsHEADDATA.add(new BAPIField("MATL_TYPE","matlType",4));
		fieldMap.put("HEADDATA", fieldsHEADDATA);
		
		List<BAPIField> fieldsCLIENTDATA=new ArrayList<BAPIField>();
		fieldsCLIENTDATA.add(new BAPIField("MATL_GROUP","matlGroup",9));
		fieldsCLIENTDATA.add(new BAPIField("OLD_MAT_NO","oldMatNo",18));
		fieldsCLIENTDATA.add(new BAPIField("BASE_UOM","baseUom",3));
		fieldsCLIENTDATA.add(new BAPIField("ITEM_CAT","itemCat",4));
		fieldsCLIENTDATA.add(new BAPIField("PUR_STATUS","purStatus",2));
		fieldsCLIENTDATA.add(new BAPIField("NET_WEIGHT","netWeight",13));
		fieldsCLIENTDATA.add(new BAPIField("UNIT_OF_WT","unitOfWt",3));
		fieldsCLIENTDATA.add(new BAPIField("STOR_CONDS","storConds",2));
		fieldsCLIENTDATA.add(new BAPIField("PUR_VALKEY","purValKey",4));
		fieldsCLIENTDATA.add(new BAPIField("TRANS_GRP","transGrp",4));
		fieldsCLIENTDATA.add(new BAPIField("LABEL_TYPE","labelType",2));
		fieldsCLIENTDATA.add(new BAPIField("LABEL_FORM","labelForm",2));
		fieldMap.put("CLIENTDATA", fieldsCLIENTDATA);
		
		List<BAPIField> fieldsMATERIALDESCRIPTION=new ArrayList<BAPIField>();
		fieldsMATERIALDESCRIPTION.add(new BAPIField("LANGU","langu",1));
		fieldsMATERIALDESCRIPTION.add(new BAPIField("MATL_DESC","matlDesc",40));
		fieldMap.put("MATERIALDESCRIPTION", fieldsMATERIALDESCRIPTION);
		
		List<BAPIField> fieldsUNITSOFMEASURE=new ArrayList<BAPIField>();
		fieldsUNITSOFMEASURE.add(new BAPIField("LENGTH","length",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("WIDTH","width",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("HEIGHT","height",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("UNIT_DIM","unitDim",3));
		fieldsUNITSOFMEASURE.add(new BAPIField("VOLUMEUNIT","volumeUnit",3));
		fieldsUNITSOFMEASURE.add(new BAPIField("GROSS_WT","grossWt",13));
		fieldsUNITSOFMEASURE.add(new BAPIField("UNIT_OF_WT","unitOfWt",3));
		fieldMap.put("UNITSOFMEASURE",fieldsUNITSOFMEASURE);
		
		List<BAPIField> fieldsSALESDATA=new ArrayList<BAPIField>();
		fieldsSALESDATA.add(new BAPIField("SALES_ORG","salesOrg",4));
		fieldsSALESDATA.add(new BAPIField("DISTR_CHAN","distrChan",2));
		fieldsSALESDATA.add(new BAPIField("MATL_STATS","matlStats",1));
		fieldsSALESDATA.add(new BAPIField("ITEM_CAT","itemCat",4));
		fieldsSALESDATA.add(new BAPIField("ACCT_ASSGT","acctAssgt",2));
		fieldsSALESDATA.add(new BAPIField("DELYG_PLNT","delygPlnt",4));
		fieldMap.put("SALESDATA",fieldsSALESDATA);
		
		List<BAPIField> fieldsTAXCLASSIFICATIONS=new ArrayList<BAPIField>();
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("DEPCOUNTRY","depCountry",3));
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("TAX_TYPE_1","taxType1",4));
		fieldsTAXCLASSIFICATIONS.add(new BAPIField("TAXCLASS_1","taxClass1",1));
		fieldMap.put("TAXCLASSIFICATIONS",fieldsTAXCLASSIFICATIONS);
		
		List<BAPIField> fieldsPLANTDATA=new ArrayList<BAPIField>();
		fieldsPLANTDATA.add(new BAPIField("PLANT","plant",4));
		fieldsPLANTDATA.add(new BAPIField("PROFIT_CTR","profitCtr",10));
		fieldsPLANTDATA.add(new BAPIField("PUR_GROUP","purGroup",3));
		fieldsPLANTDATA.add(new BAPIField("GR_PR_TIME","grPrTime",3));
		fieldsPLANTDATA.add(new BAPIField("MRP_TYPE","mrpType",2));
		fieldsPLANTDATA.add(new BAPIField("REORDER_PT","reorderPt",13));
		fieldsPLANTDATA.add(new BAPIField("MRP_CTRLER","mrpCtrler",3));
		fieldsPLANTDATA.add(new BAPIField("LOTSIZEKEY","lotSizeKey",2));
		fieldsPLANTDATA.add(new BAPIField("MINLOTSIZE","minLotSize",13));
		fieldsPLANTDATA.add(new BAPIField("PROC_TYPE","procType",1));
		fieldsPLANTDATA.add(new BAPIField("SPPROCTYPE","spProcType",2));
		fieldsPLANTDATA.add(new BAPIField("ISS_ST_LOC","issStLoc",4));
		fieldsPLANTDATA.add(new BAPIField("SLOC_EXPRC","slocExprc",4));
		fieldsPLANTDATA.add(new BAPIField("PLND_DELRY","plndDelry",3));
		fieldsPLANTDATA.add(new BAPIField("PERIOD_IND","periodInd",1));
		fieldsPLANTDATA.add(new BAPIField("AVAILCHECK","availCheck",2));
		fieldsPLANTDATA.add(new BAPIField("DEP_REQ_ID","depReqId",1));
		fieldsPLANTDATA.add(new BAPIField("LOADINGGRP","loadingGrp",4));
		fieldsPLANTDATA.add(new BAPIField("NO_COSTING","noCosting",1));
		fieldsPLANTDATA.add(new BAPIField("AUTO_RESET","autoReset",1));
		fieldsPLANTDATA.add(new BAPIField("COUNTRYORI","countryOri",3));
		fieldsPLANTDATA.add(new BAPIField("COMM_CODE","commCode",17));
		fieldMap.put("PLANTDATA",fieldsPLANTDATA);
		
		List<BAPIField> fieldsVALUATIONDATA=new ArrayList<BAPIField>();
		fieldsVALUATIONDATA.add(new BAPIField("VAL_AREA","valArea",4));
		fieldsVALUATIONDATA.add(new BAPIField("PRICE_CTRL","priceCtrl",1));
		fieldsVALUATIONDATA.add(new BAPIField("VAL_CLASS","valClass",4));
		fieldsVALUATIONDATA.add(new BAPIField("MOVING_PR","movingPr",23));
		fieldsVALUATIONDATA.add(new BAPIField("STD_PRICE","stdPrice",23));
		fieldsVALUATIONDATA.add(new BAPIField("PRICE_UNIT","priceUnit",5));
		fieldsVALUATIONDATA.add(new BAPIField("QTY_STRUCT","qtyStruct",1));
		fieldsVALUATIONDATA.add(new BAPIField("ORIG_MAT","origMat",1));
		fieldMap.put("VALUATIONDATA", fieldsVALUATIONDATA);
		
		List<BAPIField> fieldsSTORAGELOCATIONDATA=new ArrayList<BAPIField>();
		fieldsSTORAGELOCATIONDATA.add(new BAPIField("PLANT","plant",4));
		fieldsSTORAGELOCATIONDATA.add(new BAPIField("STGE_LOC","stgeLoc",4));
		fieldMap.put("STORAGELOCATIONDATA", fieldsSTORAGELOCATIONDATA);
		
		List<BAPIField> fieldsFORECASTPARAMETERS=new ArrayList<BAPIField>();
		fieldsFORECASTPARAMETERS.add(new BAPIField("PLANT","plant",4));
		fieldsFORECASTPARAMETERS.add(new BAPIField("FORE_MODEL","foreModel",1));
		fieldsFORECASTPARAMETERS.add(new BAPIField("HIST_VALS","histVals",3));
		fieldsFORECASTPARAMETERS.add(new BAPIField("FORE_PDS","forePds",3));
		fieldsFORECASTPARAMETERS.add(new BAPIField("INITIALIZE","initialize",1));
		fieldsFORECASTPARAMETERS.add(new BAPIField("TRACKLIMIT","trackLimit",5));
		fieldsFORECASTPARAMETERS.add(new BAPIField("MODEL_SP","modelSp",1));
		fieldMap.put("FORECASTPARAMETERS", fieldsFORECASTPARAMETERS);
		
	}

	@Override
	public ResponseDTO saveTemplate(TemplateDTO dto) {
		Template template=new Template(dto);
		System.out.println("id: "+template.getId()+"\t"+template.getName());
		templateRepo.saveAndFlush(template);
		System.out.println("template saved ok. now fields...");
		
		String dataTypeName=null;
		int rowIndex;
		dataTypeName=dto.getHeadData().getName();
		for(FieldValueDTO fv : dto.getHeadData().getFields()) {
			saveStructureValue(template.getId(),dataTypeName,fv);
		}
		dataTypeName=dto.getClientData().getName();
		for(FieldValueDTO fv : dto.getClientData().getFields()) {
			saveStructureValue(template.getId(),dataTypeName,fv);
		}
		dataTypeName=dto.getMaterialDescription().getName();
		rowIndex=0;
		for(List<FieldValueDTO> fvList : dto.getMaterialDescription().getRows()) {
			saveTableEntries(template.getId(),dataTypeName,rowIndex,fvList);
			rowIndex++;
		}
		dataTypeName=dto.getUnitsOfMeasure().getName();
		for(FieldValueDTO fv : dto.getUnitsOfMeasure().getFields()) {
			saveStructureValue(template.getId(),dataTypeName,fv);
		}
		dataTypeName=dto.getSalesData().getName();
		
		rowIndex=0;
		for(List<FieldValueDTO> fvList : dto.getSalesData().getRows()) {
			saveTableEntries(template.getId(),dataTypeName,rowIndex,fvList);
			rowIndex++;
		}
		dataTypeName=dto.getTaxClassifications().getName();
		for(FieldValueDTO fv : dto.getTaxClassifications().getFields()) {
			saveStructureValue(template.getId(),dataTypeName,fv);
		}
		dataTypeName=dto.getPlantData().getName();
		rowIndex=0;
		for(List<FieldValueDTO> fvList : dto.getPlantData().getRows()) {
			saveTableEntries(template.getId(),dataTypeName,rowIndex,fvList);
			rowIndex++;
		}
		dataTypeName=dto.getValuationData().getName();
		rowIndex=0;
		for(List<FieldValueDTO> fvList : dto.getValuationData().getRows()) {
			saveTableEntries(template.getId(),dataTypeName,rowIndex,fvList);
			rowIndex++;
		}
		dataTypeName=dto.getStorageLocationData().getName();
		rowIndex=0;
		for(List<FieldValueDTO> fvList : dto.getStorageLocationData().getRows()) {
			saveTableEntries(template.getId(),dataTypeName,rowIndex,fvList);
			rowIndex++;
		}
		dataTypeName=dto.getForecastParameters().getName();
		rowIndex=0;
		for(List<FieldValueDTO> fvList : dto.getForecastParameters().getRows()) {
			saveTableEntries(template.getId(),dataTypeName,rowIndex,fvList);
			rowIndex++;
		}
		
		return null;
	}
	
	private void saveStructureValue(int templateId,String dataTypeName,FieldValueDTO fv) {
		TemplateValue mtv=new TemplateValue(templateId,dataTypeName,0,fv);
		templateValueRepo.saveAndFlush(mtv);
	}
	
	private void saveTableEntries(int templateId,String dataTypeName,int rowIndex,List<FieldValueDTO> fvList) {
		for(FieldValueDTO fv : fvList) {
			TemplateValue mtv=new TemplateValue(templateId,dataTypeName,rowIndex,fv);
			templateValueRepo.saveAndFlush(mtv);
		}
		
	}

	@Override
	public List<TemplateDTO> getTemplates() {
		List<Template> templates=templateRepo.findAll();
		List<TemplateDTO> dtoList=new ArrayList<TemplateDTO>();
		for(Template t : templates) {
			dtoList.add(new TemplateDTO(t));
		}
		return dtoList;
	}

	@Override
	public TemplateDTO getTemplate(int id) {
		Optional<Template> template=templateRepo.findById(id);
		if(template.isPresent()) {
			Template t=template.get();
			TemplateDTO dto=new TemplateDTO(t);
			return dto;
		}
		else {
			return null;
		}
	}

	@Override
	public List<HeaderColumnDTO> getUploadHeaders(int templateId) {
		List<HeaderColumnDTO> headerColumns=templateRepo.getUploadHeader(templateId);
		List<HeaderColumnDTO> combinedColumns=combineHeaderColumns(headerColumns);
		return combinedColumns;
	}
	
	private List<HeaderColumnDTO> combineHeaderColumns(List<HeaderColumnDTO> headerColumns){
		Map<Integer,List<String>> columnMap=new HashMap<Integer,List<String>>();
		List<HeaderColumnDTO> combinedColumns=new ArrayList<HeaderColumnDTO>();
		int fieldIndex;
		
		for(HeaderColumnDTO hdrCol : headerColumns) {
			fieldIndex=hdrCol.getFieldIndex();
			if(columnMap.containsKey(fieldIndex)) {
				columnMap.get(fieldIndex).add(hdrCol.getHeaderText());
			}
			else {
				List<String> hdrTexts = new ArrayList<String>();
				hdrTexts.add(hdrCol.getHeaderText());
				columnMap.put(fieldIndex, hdrTexts);
			}
		}
		
		Set<Integer> indexes=columnMap.keySet();
		HeaderColumnDTO hc=null;
		for(int i : indexes) {	
			String combinedText=null;
			List<String> hdrTexts=columnMap.get(i);
			if(hdrTexts.size()>1) {
				combinedText=null;
				for(String hdrText : columnMap.get(i)) {
					if(combinedText==null) {
						combinedText=hdrText;
					}
					else {
						combinedText=combinedText+"\r\nAnd\r\n"+hdrText;
					}
				}
				hc=new HeaderColumnDTO(combinedText,i);
			}
			else {
				hc=new HeaderColumnDTO(hdrTexts.get(0),i);
			}
			combinedColumns.add(hc);
			
		}
		
		return combinedColumns;
	}

	@Override
	public RunDTO saveRun(RunDTO dto) {
		Run run=new Run(dto);
		run=runRepo.saveAndFlush(run);
		System.out.println("Saved run id: "+run.getId());
		RunDTO savedRun=new RunDTO(run);
		System.out.println("DTO id: "+savedRun.getId());
		return savedRun;
	}

	@Override
	public List<RunDTO> getRuns(int templateId) {
		List<Run> runs=runRepo.findAllByTemplateId(templateId);
		List<RunDTO> dtos=new ArrayList<RunDTO>();
		for(Run run : runs) {
			dtos.add(new RunDTO(run));
		}
		return dtos;
	}

	@Override
	public ResponseDTO saveRunData(RunDataWrapperDTO runData) {

		System.out.println("Run id is: "+runData.getRunId()+" Row count: "+runData.getRunDataList().size());

		Map<String,List<Map<String,FieldValueDTO>>> templateValuesMap=createTemplateValuesMap(runData.getTemplateId());
		
		List<Material> allMaterials=createMaterialList(runData,templateValuesMap);
		System.out.println("All materials: "+allMaterials.size());
		materialRepo.deleteAll();
		materialRepo.saveAll(allMaterials);
		
		List<RunMaterial> runMaterials=new ArrayList<RunMaterial>();
		for(Material m : allMaterials) {
			runMaterials.add(new RunMaterial(m.getRunId(),m.getMaterialId(),m.getRowNumber(),0,new Timestamp(System.currentTimeMillis())));
		}
		runMaterialRepo.saveAll(runMaterials);
		runRepo.updateMaterialCount(runData.getRunId(), runMaterials.size());
		
		
		return new ResponseDTO(119,"Run data saved");
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
	
	private List<Material> createMaterialList(RunDataWrapperDTO runData,Map<String,List<Map<String,FieldValueDTO>>> templateValuesMap){
		
		List<Material> materials=new ArrayList<Material>();
		Material m=null;
		
		for(RunDataRowDTO rowData : runData.getRunDataList()) {
			m=new Material();
			m.setRunId(runData.getRunId());
			m.setRowNumber(rowData.getRowNumber());
			for(int i=0;i<STRUCTURES.length;i++) {
				List<Map<String,FieldValueDTO>> rows=templateValuesMap.get(STRUCTURES[i]);
				for(Map<String,FieldValueDTO> row : rows) {
					fillValues(m,STRUCTURES[i],row,rowData);
				}
			}
			materials.add(m);
		}
		
		return materials;
	}
	
	private void fillValues(Material m,String structure,Map<String,FieldValueDTO> templateFields,RunDataRowDTO rowData) {
		FieldValueDTO tf=null;
		String val;
		List<BAPIField> fields=getFieldMap().get(structure);
		Map<String,FieldValueDTO> rowDataMap=new HashMap<String,FieldValueDTO>();
		FieldValueDTO fieldData=null;
		for(BAPIField field : fields) {
			tf=templateFields.get(field.getFieldName());
			if(tf.getValueType().equals("CONSTANT")){
				val=tf.getValue();
			}
			else {
				Integer index=Integer.parseInt(tf.getValue());
				val=rowData.getRowData()[index];
			}
			//System.out.println(structure+"-"+field.getFieldName()+"\t"+val);
			fieldData=new FieldValueDTO(tf.getField(),tf.getValueType(),val);
			rowDataMap.put(fieldData.getField(), fieldData);
		}
		m.setRowData(structure, rowDataMap);
	}
	
	@Override
	public ResponseDTO deleteRun(int runId) {
		runRepo.deleteById(runId);
		return new ResponseDTO(120,"Run id: "+runId+" deleted");
	}

	@Override
	public RunDTO getRun(int runId) {
		Optional<Run> opt=runRepo.findById(runId);
		RunDTO dto=null;
		if(opt.isPresent()) {
			Run run=opt.get();
			dto=new RunDTO(run);
		}
		return dto;
	}
	
}
