/**
 * 
 */

var $body;
var contextPath;
var spinnerDisabled=false;

$(document).ready(initPage);
$(document).on({
	ajaxStart : function(){
		if(!spinnerDisabled){
			$body.addClass('loading');
		}
	},
	ajaxStop : function(){
		$body.removeClass('loading');
	}
});

function _globalSetup(){
	$body=$('body');
	$.ajaxSetup({
        headers:
        { 
        	'Accept': 'application/json'
	        //'Content-Type': 'application/json' //<==what if you want to send formdata?! this defaults every ajax call to application/json!
        }
    });
	contextPath=$('#contextPath').val();
}

function initPage(){
	console.log('initializing page');
	_globalSetup();
 	var viewId=$('meta[name="viewId"]').attr('content');
 	if(viewId==1){
 		MessageBar.init();
 		console.log('menu');
 	}
 	else if(viewId==2){
 		MessageBar.init();
 		SAPConnection.init();
 		PurchaseOrder.init();
 		console.log('create po');
 	}
 	else if(viewId==3){
 		MessageBar.init();
 		SAPConnection.init();
 		PODetails.init();
 		console.log('view po');
 	}
 	else if(viewId==4){
 		MessageBar.init();
 		SAPConnection.init();
 		SalesPrice.init();
 		console.log('change sales price');
 	}
 	else if(viewId==5){
 		MessageBar.init();
 		SAPConnection.init();
 		Confirmations.init();
 		console.log('order confirmations');
 	}
 	else if(viewId==6){
 		
 	}
 	else if(viewId==7){
 		MessageBar.init();
 		MaterialRun.init();
 	}
 	else if(viewId==8){
 		MessageBar.init();
 		MaterialTemplates.init();
 	}
 	else if(viewId==9){
 		MessageBar.init();
 		SAPConnection.init();
 		MaterialExecute.init();
 	}
 	
 	
}

var MessageBar=(function(){
	
	var $messageDiv;
	var $messageBar;
	var errorClass='errorMessage';
	var successClass='successMessage';
	var infoClass='infoMessage';
	var warningClass='warningClass'
	
	function init(){
		$messageDiv=$('div#message_container');
		$messageBar=$('span#message_line');
		$messageDiv.removeClass(errorClass);
		$messageDiv.removeClass(successClass);
		$messageDiv.removeClass(infoClass);
		$messageDiv.removeClass(warningClass);
		$messageBar.text('');
		$messageDiv.hide();
	}
	
	function showSuccess(txt){
		$messageDiv.show();
		$messageBar.text(txt);
		$messageDiv.addClass(successClass);
		$messageDiv.removeClass(errorClass);
	}
	
	function showError(txt){
		$messageDiv.show();
		$messageBar.text(txt);
		$messageDiv.addClass(errorClass);
		$messageDiv.removeClass(successClass);
	}
	
	return{
		init : init,
		showSuccess : showSuccess,
		showError : showError
	}
	
})();

var Util=(function(){
	
	function getHtmlDate(dateValue){
		var d=_convertExcelDateValue(dateValue);
		var day=("0" + d.getDate()).slice(-2);
		var month=("0" + (d.getMonth() + 1)).slice(-2);
		var dateString=d.getFullYear()+"-"+month+"-"+day
		//console.log(dateString);
		return dateString;
		
	}
	
	function getHtmlDate2(ms){
		var d=new Date(ms);
		var day=("0" + d.getDate()).slice(-2);
		var month=("0" + (d.getMonth() + 1)).slice(-2);
		var dateString=d.getFullYear()+"-"+month+"-"+day
		//console.log(dateString);
		return dateString;
	}
	
	function _convertExcelDateValue(dateValue){
		//console.log(dateValue);
		return new Date((dateValue - 25569)*86400*1000);
	}
	
	return{
		getHtmlDate : getHtmlDate,
		getHtmlDate2 : getHtmlDate2
	}
	
})();

var MaterialTemplates=(function(){
	
	
	var dataTableIds=[
		'headdata',
		'clientdata',
		'materialdescription',
		'unitsofmeasure',
		'salesdata',
		'taxclassifications',
		'plantdata',
		'valuationdata',
		'storagelocationdata',
		'forecastparameters'
		];
	
	function init(){
		console.log('MaterialTemplates.init');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$('.template-link').click(_loadTemplate);
		$('select.function-field-input-type').on('change',_functionFieldInputType);
		$('button.add-bapi-table-entry').click(_addTableEntry);
		$('button.copy-bapi-table-entry').click(_copyTableEntry);
		$('button.del-bapi-table-entry').click(_delTableEntry);
		$('button#btn_new_template').click(_newTemplate);
		$('button#btn_save_template').click(_saveTemplate);
		//$('button#btn_test').click(_getUploadHeaders(1));
	}
	
	function _loadTemplate(){
		//var id=$(this).attr('data-id');
		var id=$(this).data('id');
		console.log('loading template: '+id);
		var params={
				'templateId' : id
		}
		AjaxUtil.get('material/getMaterialTemplate',params,'json',_displayTemplate,ErrorHandler.handle)
	}
	
	function _displayTemplate(template){
		console.log(JSON.stringify(template));
		console.log('Displaying loaded template...');
		$('input#template_id').val(template.id);
		$('input#template_name').val(template.name);
		_setStructValues(template.headData);
		_setStructValues(template.clientData);
		_setStructValues(template.unitsOfMeasure);
		_setStructValues(template.taxClassifications);
		_setTableValues(template.materialDescription);
		_setTableValues(template.salesData);
		_setTableValues(template.plantData);
		_setTableValues(template.valuationData);
		_setTableValues(template.storageLocationData);
		_setTableValues(template.forecastParameters);
		_getUploadHeaders(template.id);
	}
	
	function _setStructValues(struct){
		_clearFieldValues(struct);
		_setFieldValues(struct,0);
	}
	
	function _clearFieldValues(struct){
		var structName=struct.name.toLowerCase();
		$('div#list_'+structName).remove();
	}
	
	function _setFieldValues(struct,rowIndex){
		var structName=struct.name.toLowerCase();
		var $templateList=$('div#list_'+structName+'_template');
		var $fieldList=$templateList.clone(true);
		$fieldList.attr('id','list_'+structName);
		$fieldList.attr('data-row',rowIndex);
		$fieldList.removeClass('hidden-fields');
		$fieldList.insertAfter($templateList);
		struct.fields.forEach(function(fld,index){
			let id=struct.name+'_'+fld.field;
			let $tr=$('tr#'+id+'[data-row='+rowIndex+']');
			let $select=$('tr#'+id+'>td.input-type>select');
			let $oldInput=$('tr#'+id+'>td.input-value>input');
			let $newInput;
			$select.val(fld.valueType);
			
			if(fld.valueType=='FIELD'){
				$newInput=$('<input type="number" min="0" max="255">')
			}
			else if(fld.valueType=='CONSTANT'){
				$newInput=$('<input type="text">')
			}
			$newInput.insertBefore($oldInput);
			$newInput.val(fld.value);
			$oldInput.remove();
		});
	}
	
	function _setTableValues(table){
		_clearFieldValues(table);
		var tableName=table.name.toLowerCase();
		var $entryDiv=$('div#entry_'+tableName);
		var $templateList=$('div#list_'+tableName+'_template');
		table.rows.forEach(function(row,index){
			var $fieldList=$templateList.clone(true);
			$fieldList.attr('id','list_'+tableName);
			$fieldList.attr('data-row',index);
			$fieldList.removeClass('hidden-fields');
			row.forEach(function(fld,index){
				let id=table.name+'_'+fld.field;
				let $tr=$fieldList.find('tr#'+id);
				let $select=$fieldList.find('tr#'+id+'>td.input-type>select');
				let $oldInput=$fieldList.find('tr#'+id+'>td.input-value>input');
				let $newInput;
				$select.val(fld.valueType);
				
				if(fld.valueType=='FIELD'){
					$newInput=$('<input type="number" min="0" max="255">')
				}
				else if(fld.valueType=='CONSTANT'){
					$newInput=$('<input type="text">')
				}
				$newInput.insertBefore($oldInput);
				$newInput.val(fld.value);
				$oldInput.remove();
			});
			$fieldList.insertBefore($entryDiv);
		});
	}
	
	function _functionFieldInputType(event){
		var inputType=$(event.target).val();
		var $tdInputType=$(event.target).parent();
		var $tr=$tdInputType.parent();
		var $input;
		
		$tdInputValue=$tr.find('td.input-value');
		$tdInputValue.empty();
		if(inputType=='FIELD'){
			console.log('FIELD');
			$input=$('<input type="number" min="0" max="255">')
		}
		else if(inputType=='CONSTANT'){
			console.log('CONSTANT');
			$input=$('<input type="text">')
		}
		$tdInputValue.append($input);
	}
	
	function _addTableEntry(event){
		console.log('add table entry');
		var $btn=$(event.target);
		var $entryDiv=$btn.parent();
		var id=$entryDiv.attr('id');
		var structure=$btn.data('structure');
		console.log('entry div: '+id);
		var $rowDiv=$entryDiv.parent();
		var $fieldsDiv=$rowDiv.find('.babi-field-list:not(.bapi-add-table-entry)').first().clone(true);
		$fieldsDiv.attr('id','list_'+structure);
		$fieldsDiv.removeClass('hidden-fields');
		$fieldsDiv.insertBefore('#'+id);
	}
	
	function _copyTableEntry(event){
		console.log('copy table entry');
		var $btn=$(event.target).parent();
		var structure=$btn.data('structure');
		console.log(structure);
		var $toolbar=$btn.parent();
		var $srcDiv=$toolbar.parent();
		var $rowDiv=$srcDiv.parent();
		var $copyDiv=$srcDiv.clone(true);
		
		var $srcSelects=$srcDiv.find('select');
		$copyDiv.find('select').each(function(index,item){
			$(item).val($srcSelects.eq(index).val());
		});
		console.log('#entry_'+structure);
		$copyDiv.insertBefore('#entry_'+structure);
	}
	
	function _delTableEntry(event){
		var $btn=$(event.target).parent();
		var $controlDiv=$btn.parent();
		var $fieldsDiv=$controlDiv.parent();
		$fieldsDiv.remove();
	}
	
	function _newTemplate(){
		dataTableIds.forEach(function(dataListId,index){
			$('div#list_'+dataListId).remove();
			let $templateList=$('div#list_'+dataListId+'_template');
			let $dataList=$templateList.clone(true);
			$dataList.attr('id','list_'+dataListId);
			$dataList.attr('data-row',0);
			$dataList.removeClass('hidden-fields');
			$dataList.insertAfter($templateList);
			
		});
		$('input#template_id').val('New');
		$('input#template_name').val('');
	}
	
	function _saveTemplate(){
		var templateJson=_getTemplateJSON();
		var data=JSON.stringify(templateJson);
		console.log(data);
		AjaxUtil.post('material/saveMaterialTemplate',data,'json',_test,ErrorHandler.handle);
	}
	
	function _test(template){
		MessageBar.showSuccess('Template saved');
		if(template){
			console.log('Template saved');
			console.log(template);
		}
	}
	
	function _getTemplateJSON(){
		var id;
		if($('input#template_id').val()=='New'){
			id=-1;
		}
		else{
			id=parseInt($('input#template_id').val());
		}
		
		var template={
				'id'						: id,
				'name' 						: $('input#template_name').val(),
				'headData'					: _getStructureData('headdata'),
				'clientData'				: _getStructureData('clientdata'),
				'materialDescription'		: _getTableData('materialdescription'),
				'unitsOfMeasure'			: _getStructureData('unitsofmeasure'),
				'salesData'					: _getTableData('salesdata'),
				'taxClassifications'		: _getStructureData('taxclassifications'),
				'plantData'					: _getTableData('plantdata'),
				'valuationData'				: _getTableData('valuationdata'),
				'storageLocationData'		: _getTableData('storagelocationdata'),
				'forecastParameters'		: _getTableData('forecastparameters')
		}
		return template;
		
	}
	
	function _getStructureData(name){
		var structureData={
				'name'		: name.toUpperCase(),
				'fields'	: []
		};
		var $divRow=$('div#'+name+'>div.bapi-field-list-viewport>div.bapi-field-list-container>div.bapi-field-list-container-row');
		var $tbodies=$divRow.find('div#list_'+name+'>div.babi-field-list-fields>table>tbody');
		$tbodies.each(function(index,tbody){
			var $rows=$(tbody).find('tr');
			$rows.each(function(index,tr){
				let field=$(tr).find('td.function-field').text();
				let propertyName=$(tr).attr('data-propertyname');
				let valueType=$(tr).find('td.input-type>select').val();
				let value=$(tr).find('td.input-value>input').val();
				if(valueType=='FIELD'){
					value=parseInt(value);
				}
				let fieldValue={
						'field'		: field,
						'valueType' : valueType,
						'value'		: value
				};
				structureData.fields.push(fieldValue);
			});
		});
		return structureData;
	}
	
	function _getTableData(name){
		var tableData={
				'name'		: name.toUpperCase(),
				'rows'		: []
		};
		var $divRow=$('div#'+name+'>div.bapi-field-list-viewport>div.bapi-field-list-container>div.bapi-field-list-container-row');
		var $tbodies=$divRow.find('div#list_'+name+'>div.babi-field-list-fields>table>tbody');
		var index=0;
		$tbodies.each(function(index,tbody){
			var $rows=$(tbody).find('tr');
			var templateData={};
			var fieldValueArr=[];
			$rows.each(function(index,tr){
				let field=$(tr).find('td.function-field').text();
				let propertyName=$(tr).attr('data-propertyname');
				let valueType=$(tr).find('td.input-type>select').val();
				let value=$(tr).find('td.input-value>input').val();
				if(valueType=='FIELD'){
					value=parseInt(value);
				}
				let fieldValue={
						'field'		: field,
						'valueType' : valueType,
						'value'		: value
				};
				fieldValueArr.push(fieldValue);
				
			});
			tableData.rows[index]=fieldValueArr;
			index++;
		});
		return tableData;
	}
	
	function _getUploadHeaders(templateId){
		var params={
				'templateId' : templateId
		}
		AjaxUtil.get('material/getUploadHeaders',params,'param',_displayUploadHeaders,
				function(error){
					console.log('Failed!');
				}
		);
	}
	
	function _displayUploadHeaders(headerColumns){
		$headerRow=$('div#upload_template>table>thead>tr');
		$indexRow=$('div#upload_template>table>tbody>tr');
		headerColumns.forEach(function(hc){
			$headerRow.append($('<th>'+hc.headerText+'</th>'));
			$indexRow.append($('<td>'+hc.fieldIndex+'</td>'))
		});	
	}
	
	
	return{
		init : init
	}
	
})();

var MaterialRun=(function(){
	
	var $modalDiv;
	var $btnCreateRun;
	var $runTableDiv;
	var $dataFileInput;
	var $btnSaveRun;
	var $btnStartRun;
	var $btnDeleteRun;
	var $btnResetRun;
	var $uploadProgressBar;
	
	function init(){
		console.log('MaterialRun.init');
		$modalDiv=$('div#create_run_modal');
		$btnCreateRun=$('button#btn_create_run');
		$runTableDiv=$('div#run_table');
		$dataFileInput=$('input#customFile');
		$btnSaveRun=$('button#btn_save_run');
		$btnStartRun=$('button#btn_start_run');
		$btnDeleteRun=$('button#btn_delete_run');
		$btnResetRun=$('button#btn_reset_run');
		$uploadProgressBar=$('div#upload_progress');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$('.template-list-item').click(_selectTemplate);
		$btnCreateRun.click(_initUploadProgressBar);
		$dataFileInput.on('change',_selectFile);
		$btnSaveRun.click(_saveRun);
		$btnDeleteRun.click(_deleteRun);
		$btnStartRun.click(_startRun);
		$btnResetRun.click(_resetRun);
	}
	
	function _selectTemplate(){
		$('.template-list-item').removeClass('active');
		$(this).addClass('active');
		var templateId=parseInt($(this).attr('data-id'));
		var params={
				'templateId' : templateId
		}
		AjaxUtil.get('material/runs',params,'param',_displayRuns,ErrorHandler.handle);
		
	}
	
	function _displayRuns(runs){
		_clearRuns();
		runs.forEach(_addRun);
	}
	
	function _clearRuns(){
		$runTableDiv.find('div.run-table-row')
			.not('div.add-entry-row')
			.not('div#row_template')
			.remove();
	}
	
	function _addRun(run,index){
		var $rowDiv=$('div#row_template').clone(true);
		$rowDiv.attr('id','row-'+index);
		$rowDiv.attr('data-run_id',run.id);
		$rowDiv.removeClass('hidden-fields');
		$rowDiv.addClass('run-table-row');
		$rowDiv.find('h4').text(run.name);
		$rowDiv.find('#btn_delete_run').attr('data-run_id',run.id);
		$rowDiv.find('#btn_start_run').attr('data-run_id',run.id);
		$rowDiv.find('#btn_reset_run').attr('data-run_id',run.id);
		let text;
		if(run.testRun){
			text='TEST';
		}
		else{
			text='PROD';
		}
		$rowDiv.find('#txt_testOrProd').text(text);
		$runTableDiv.append($rowDiv);
		console.log(run.name);
	}
	
	function _selectFile(){
        var fileName = $(this).val();
        $(this).next('.custom-file-label').html(fileName);
	}
	
	function _saveRun(){
		console.log('saving run');
		spinnerDisabled=true;
		_enableModal(false);
		var runJson=_createRunJson();
		console.log(runJson);
		var data=JSON.stringify(runJson);
		AjaxUtil.post('material/saveRun',data,'json',_saveData,ErrorHandler.handle);
	}
	
	function _enableModal(enable){
		if(enable){
			$modalDiv.find('input,button').attr('disabled',false);
		}
		else{
			$modalDiv.find('input,button').attr('disabled',true);
		}
	}
	
	function _deleteRun(event){
		var runId=$(this).data('run_id');
		console.log(runId);
		var params={
				'runId' : runId
		}
		AjaxUtil.get('material/deleteRun',params,'param',function(){
			MessageBar.showSuccess('Run id: '+runId+' deleted');
			_refreshRuns();
		},
		ErrorHandler.handle
		);
	}
	
	function _refreshRuns(){
		var params={
			'templateId' : _getSelectedTemplateId()
		}
		AjaxUtil.get('material/runs',params,'param',_displayRuns,ErrorHandler.handle);
	}
	
	function _getSelectedTemplateId(){
		var templateId=parseInt($('.template-list-item.active').attr('data-id'));
		return templateId;
	}
	
	function _startRun(event){
		var runId=$(this).data('run_id');
		console.log(runId);
		var target=contextPath+'material/execute?runId='+runId;
		console.log(target);
		window.location.assign(target);
	}
	
	function _resetRun(event){
		var runId=$(this).data('run_id');
		console.log(runId);
	}
	
	function _createRunJson(){
		var runJson={
			'id'			: -1,
			'name'			: $('#run_name').val(),
			'templateId'	: parseInt($('.template-list-item.active').attr('data-id')),
			'testRun'		: $('#chk_test_run').is(':checked')
		}
		return runJson;
	}
	
	function _saveData(run){
		MessageBar.showSuccess('Run id: '+run.id+" saved succesfully");
		
		var file=$dataFileInput[0].files[0];
		var reader=new FileReader();
		
		reader.onload=function(event){
			var csv=event.target.result;
			console.log('File read succesfully');
			var templateId=run.templateId;
			console.log("template: "+run.templateId+" run: "+run.id);
			var data={
					'templateId'	: templateId,
					'runId'			: run.id,
					'runDataList'	: _csvToJsonArray(run.id,csv)
			}
			console.log(data);
			AjaxUtil.post('material/saveRunData',JSON.stringify(data),'json',_handleSuccess,ErrorHandler.handle,_uploadProgressHandler);
		}
		reader.onerror=function(event){
			MessageBar.showError('Failed to read file!');
		}
		reader.readAsText(file);
		
	}
	
	function _initUploadProgressBar(){
		$uploadProgressBar.attr('aria-valuenow',0);
		$uploadProgressBar.width('0%');
		$uploadProgressBar.removeClass('progress-bar-striped');
		$uploadProgressBar.removeClass('progress-bar-animated');
		$uploadProgressBar.text('');
	}
	
	function _uploadProgressHandler(e){
		var progress;
		if(e.lengthComputable){
			progress=Math.floor(100*(e.loaded/e.total));
		}
		if(progress<=100){
			$uploadProgressBar.attr('aria-valuenow',progress);
			$uploadProgressBar.width(progress+'%');
		}
		if(progress==100 && !$uploadProgressBar.hasClass('progress-bar-animated')){
			$uploadProgressBar.addClass('progress-bar-striped');
			$uploadProgressBar.addClass('progress-bar-animated');
			$uploadProgressBar.text('Saving to database...');
		}
	}
	
	function _handleSuccess(response){
		$modalDiv.modal('hide');
		_refreshRuns();
		MessageBar.showSuccess('Run data saved succesfully');
		_enableModal(true);
		spinnerDisabled=false;
		
	}
	
	function _csvToArray(csv){
		var allTextLines = csv.split(/\r\n|\n/);
        var lines = [];
        for (var i=0; i<allTextLines.length; i++) {
            var data = allTextLines[i].split(';');
                var tarr = [];
                for (var j=0; j<data.length; j++) {
                	data[j]=data[j].replace(/"/g, "");
                    tarr.push(data[j]);
                }
                lines.push(tarr);
        }
        return lines;
	}
	
	function _csvToJsonArray(runId,csv){
		var allTextLines = csv.split(/\r\n|\n/);
        var jsonArr = [];
        var rowNumber=0;
        for (var i=0; i<allTextLines.length; i++) {
            var data = allTextLines[i].split(';');
            if(data!=""){
	            for (var j=0; j<data.length; j++) {
	            	data[j]=data[j].replace(/"/g, "");
	            }
	            jsonArr.push(_createDataJson(runId,rowNumber,data));
            }
            rowNumber++;
        }
        return jsonArr;
	}
	
	function _createDataJson(runId,rowNumber,data){
		var dataJson={
				'runId'		: runId,
				'rowNumber'	: rowNumber,
				'rowData'	: data
		}
		return dataJson;
	}
	
	return{
		init : init
	}
	
})();

var MaterialExecute=(function(){
	
	var runId;
	var statusPieChart;
	var $btnCreateWorkers;
	
	function init(){
		console.log('MaterialExecute.init')
		runId=$('#run_id').data('runid');
		$btnCreateWorkers=$('#btn_create_workers');
		_bindEventHandlers();
		_initChart();
		_refreshRunStatus();
	}
	
	function _bindEventHandlers(){
		$btnCreateWorkers.click(_createWorkers);
		$('button.worker-start').click(_startWorker);
		$('button.worker-stop').click(_stopWorker);
		$('button#refresh_all_workers').click(_refreshRunStatus);
		$('button#start_all_workers').click(_startAllWorkers);
		$('button#stop_all_workers').click(_stopAllWorkers);
		$('button#reset_run').click(_resetRun);
		
	}
	
	function _initChart(){
		var $ctx=$('#status_pie');
		var colorSuccess='rgba(63, 191, 63, 1.0)';
		var colorError='rgba(191, 63, 63, 1.0)';
		var colorNoRun='rgba(114, 140, 140, 0.6)';
		statusPieChart = new Chart($ctx, {
            type: 'pie',
            data: {
                labels: ["Success", "Error", "No run"],
                datasets: [{
                    data: [1200, 1700, 800],
                    backgroundColor: [
                    	colorSuccess, 
                    	colorError, 
                    	colorNoRun
                    ]
                }]
            },
            options: {
            	responsive: false,
                title: {
                    display: true,
                    text: 'Run status'
                }
            }
        });
	}
	
	function _updateStatusPie(successCount,errorCount,noRunCount){
		var newData=[successCount,errorCount,noRunCount];
		statusPieChart.data.datasets[0].data=newData;		
		statusPieChart.update();
	}
	
	function _createWorkers(){
		console.log(runId);
		var params={
				'runId' 		: runId,
				'maxMaterials'	: parseInt($('#worker_max_materials').val())
		}
		AjaxUtil.get('material/workers/create',params,'param',_createWorkerRows,ErrorHandler.handle);
	}
	
	function _createWorkerRows(workers){
		var $tbody=$('#workers_tbody');
		var $row;
		workers.forEach(function(worker,index){
			$row=$('#template_worker_row').clone(true);
			$row.attr('data-id',worker.id);
			$row.find('button.worker-start').attr('data-id',worker.id);
			$row.find('button.worker-stop').attr('data-id',worker.id);
			$row.attr('hidden',false);
			_updateWorkerRow($row,worker);
			$tbody.append($row);
		});
	}
	
	function _updateWorkerRows(workers){
		var $tbody=$('#workers_tbody');
		var $row;
		workers.forEach(function(worker,index){
			$row=$tbody.find('tr[data-id="'+worker.id+'"]');
			_updateWorkerRow($row,worker);
		});
	}
	
	function _updateWorkerRow($row,worker){
		//console.log(worker);
		$row.find('.worker-id').text(worker.id);
		$row.find('.worker-materials').text(worker.materialCount);
		$row.find('.worker-success').text(worker.successCount);
		$row.find('.worker-errors').text(worker.errorCount);
		//console.log(worker.id+'\tprogress: '+worker.progress);
		$progressBar=$row.find('div#worker_progress');
		$progressBar.attr('aria-valuenow',worker.progress);
		$progressBar.width(worker.progress+'%');
		$progressBar.text(worker.progress+'%');
		$row.find('.worker-status').text(worker.currentStatus);
	}
	
	function _startWorker(){
		var id=$(this).data('id');
		console.log('starting worker: '+id);
		var params={
				'id' 		: id
		}
		AjaxUtil.get('material/workers/start',params,'param',
				function(response){
					MessageBar.showSuccess(response.responseText);
				}
				,ErrorHandler.handle);
		_pollWorkerStatus(true);
		_pollRunStatus(true);
	}
	
	function _stopWorker(){
		var id=$(this).data('id');
		console.log('stopping worker: '+id);
		var params={
				'id' 		: id
		}
		AjaxUtil.get('material/workers/stop',params,'param',
				function(response){
					MessageBar.showSuccess(response.responseText);
				}
				,ErrorHandler.handle);
	}
	
	function _resetRun(){
		var params={
				'runId' 		: runId
		}
		AjaxUtil.get('material/run/reset',params,'param',_updateRun,ErrorHandler.handle);
	}
	
	function _startAllWorkers(){
		var params={
				'runId' 		: runId
		}
		AjaxUtil.get('material/workers/startAll',params,'param',_updateWorkerRows,ErrorHandler.handle);
		_pollWorkerStatus(true);
		_pollRunStatus(true);
	}
	
	function _stopAllWorkers(){
		var params={
				'runId' 		: runId
		}
		AjaxUtil.get('material/workers/stopAll',params,'param',_updateWorkerRows,ErrorHandler.handle);
	}
	
	function _refreshAllWorkers(){
		var params={
				'runId' 		: runId
		}
		AjaxUtil.get('material/workers/status',params,'param',_updateWorkerRows,ErrorHandler.handle);
	}
	
	function _pollWorkerStatus(poll){
		var endPoint='material/workers/status';
		if(poll && AjaxUtil.isPolling(endPoint)==false){
			let params={
					'runId'	: runId
			}
			AjaxUtil.startPolling(endPoint,params,'param',
			function(workers){
				_updateWorkerRows(workers);
				let stopPolling=true;
				workers.forEach(function(worker,index){
					if(worker.currentStatus=='RUNNING'){
						stopPolling=false;
					}
				});
				if(stopPolling){
					_pollWorkerStatus(false);
					_pollRunStatus(false);
				}
			},
			ErrorHandler.handle,5000);
		}
		else if(poll==false){
			AjaxUtil.stopPolling(endPoint);
		}
		else{
			//something else
		}
	}
	
	function _refreshRunStatus(){
		var params={
				'runId' 		: runId
		}
		AjaxUtil.get('material/run/status',params,'param',_updateRun,ErrorHandler.handle);
	}
	
	function _pollRunStatus(poll){
		var endPoint='material/run/status';
		if(poll && AjaxUtil.isPolling(endPoint)==false){
			let params={
					'runId'	: runId
			}
			AjaxUtil.startPolling(endPoint,params,'param',
			function(run){
				_updateRun(run);
			},
			ErrorHandler.handle,5000);
		}
		else if(poll==false){
			AjaxUtil.stopPolling(endPoint);
		}
		else{
			//something else
		}
	}
	
	function _updateRun(run){
		$('#material_count').val(run.materialCount);
		$('#success_count').val(run.successCount);
		$('#error_count').val(run.errorCount);
		$('#progress_count').val(run.progressCount);
		$('#norun_count').val(run.noRunCount);
		
		$progressBar=$('#run_progress');
		$progressBar.attr('aria-valuenow',run.progress);
		$progressBar.width(run.progress+'%');
		$progressBar.text(run.progressCount+' / '+run.materialCount);
		
		_updateStatusPie(run.successCount,run.errorCount,run.noRunCount);
	}
	
	return{
		init : init
	}
	
})();

var AjaxUtil=(function(){
	
	var pollMap=new Map();
	
	function post(endPoint,data,dataType,successHandler,failureHandler,uploadProgressHandler){
		var url=contextPath+endPoint;
		$.ajax({
			url : url,
			method : "POST",
			headers : {
				'Content-Type': 'application/json'
			},
			data : data,
			dataType : "json",
			xhr : function(){
				var xhr=$.ajaxSettings.xhr();
				if(uploadProgressHandler){
					xhr.upload.onprogress=uploadProgressHandler
				}
				return xhr;
			}
		}).done(function(response){
			successHandler(response);
		}).fail(function(e){
			if(failureHandler){
				failureHandler(e);
			}
			else{
				console.log('no failure handler');
			}
		}).always(function(){
			
		});
	}
	
	function get(endPoint,data,dataType,successHandler,failureHandler){
		var url=contextPath+endPoint;
		$.ajax({
			url : url,
			method : "GET",
			data : data,
			dataType : "json"
		}).done(function(response){
			successHandler(response);
		}).fail(function(e){
			if(failureHandler){
				failureHandler(e);
			}
			else{
				console.log('no failure handler');
			}
		}).always(function(){
			
		});
	}
	
	function _poll(endPoint,data,dataType,successHandler,failureHandler,intervalMs){
		var url=contextPath+endPoint;
		spinnerDisabled=true;
		var timeout;
		if(timeout){
			clearTimeout(timeout);
		}
		timeout=setTimeout(function(){
			$.ajax({
				url : url,
				method : "GET",
				data : data,
				dataType : "json",
				timeout : 2000
			}).done(function(response){
				console.log('polling end-point '+endPoint);
				successHandler(response);
			}).fail(function(e){
				if(failureHandler){
					failureHandler(e);
				}
				else{
					console.log('no failure handler');
				}
			}).always(function(){
				_poll(endPoint,data,dataType,successHandler,failureHandler,intervalMs);
			});
		},intervalMs);
		if(pollMap.get(endPoint)==false){
			console.log('stop polling end-point '+endPoint);
			clearTimeout(timeout);
			pollMap.delete(endPoint);
		}
	}
	
	function startPolling(endPoint,data,dataType,successHandler,failureHandler,intervalMs){
		if(pollMap.has(endPoint)==false){
			console.log('start polling end-point '+endPoint);
			pollMap.set(endPoint,true);
			_poll(endPoint,data,dataType,successHandler,failureHandler,intervalMs);
		}
		
	}
	
	function stopPolling(endPoint){
		console.log('trying to stop polling now');
		pollMap.set(endPoint,false);
	}
	
	function isPolling(endPoint){
		if(pollMap.has(endPoint)){
			return true;
		}
		else{
			return false;
		}
	}
	
	return{
		post 			: post,
		get				: get,
		startPolling	: startPolling,
		stopPolling		: stopPolling,
		isPolling		: isPolling
	}
	
})();

var Confirmations=(function(){
	
	var $fileImport;
	var $btnSend;
	var $btnFindPO;
	
	function init(){
		console.log('Confirmations.init');
		$btnSend=$('#btn_send');
		$btnFindPO=$('input#btn_find_po');
		$fileImport=$('input#file_import_pdf');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$fileImport.change(_displayFile);
		$btnSend.click(_uploadFile);
		$btnFindPO.click(_findPO);
	}
	
	function _displayFile(event){
		var file=event.target.files[0];
		var reader=new FileReader();
		reader.onload=function(e){
			$('#pdf').attr('src',e.target.result);
		}
		reader.readAsDataURL(file);
	}
	
	function _findPO(event){
		var poId=$('span#po').text();
		PODetails.findPO(poId,function(po){
			console.log(po);
		});
	}
	
	function _uploadFile(event){
		
		var fileInput=document.getElementById('file_import_pdf');
		var data=new FormData();
		
		data.append('file',fileInput.files[0]);
		data.append('type','pdf');
	
		_sendFile(data);
			
	}
	
	function _sendFile(data){
		var url=contextPath+'po/confirmations/uploadFile';
		
		console.log(data.get('file')+' '+data.get('type'));
			
		$.ajax({
			type 			: 'POST',
			url				: url,
			data			: data,
			processData		: false,
			contentType		: false
		}).done(function(c){
			_handleResponse(c);
		}).fail(function(e){
			ErrorHandler.handle(e);
		}).always(function(){
			
		});
	}
	
	function _handleResponse(c){
		$('span#po').text(c.customerOrderId);
		$tbody=$('table#supplier_conf_table>tbody');
		$tbody.empty();
		c.items.forEach(function(item){
			$tbody.append(_getSupplierItemRow(item));
		});
		console.log(c);
	}
	
	function _getSupplierItemRow(item){
		var $row=$('<tr>'
					+'<td>'+item.itemNumber+'</td>'
					+'<td>'+item.supplierId+'</td>'
					+'<td>'+item.customerId+'</td>'
					+'<td>'+item.orderedQuantity+'</td>'
					+'<td>'+Util.getHtmlDate2(item.confirmedDate)+'</td>'
					+'<td>'+item.unitNetPrice+'</td>'
					+'<td>'+item.totalNetPrice+'</td>'
				+'</tr>');
		return $row;
	}
	
	return{
		init : init
	}
	
})();

var SAPConnection=(function(){

	var $btnLogin;
	var $loginModal;
	var $cmbClient;
	var $fldUser;
	var $fldPassword;
	
	function init(){
		console.log('SAPConnection.init');
		$loginModal=$('div#sap_login_modal');
		$btnLogin=$('div#sap_login_modal input#sap_login_do');
		$cmbClient=$('div#sap_login_modal select#sap_client');
		$fldUser=$('div#sap_login_modal input#sap_username');
		$fldPassword=$('div#sap_login_modal input#sap_password');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$btnLogin.click(_loginSAP);
	}
	
	function _loginSAP(){
		console.log('Login...');
		var userJson={
				'client' 	:	$cmbClient.val(),
				'userName'	: 	$fldUser.val(),
				'password'	: 	$fldPassword.val()
		}
		
		var url=contextPath+'sap/login';
		var data=JSON.stringify(userJson);
		console.log(data);
		
		$.ajax({
			url : url,
			method : "POST",
			data : data,
			dataType : "json",
			contentType: 'application/json'
		}).done(function(user){
			console.log("Logged in");
			MessageBar.showSuccess("Logged in");
			$('span#sap_user').text(user.userName+" "+user.client);
		}).fail(function(e){
			ErrorHandler.handle(e);
		}).always(function(){
			$('div#sap_login_modal').modal('hide');
		});
	}
	
	function showLogin(){
		$loginModal.modal('show');
	}
	
	return{
		init : init,
		showLogin : showLogin
	}
	
})();

var ErrorHandler=(function(){
	
	function init(){
		
	}
	
	function handle(e){
		
		let err=e.responseJSON;
		if(err){
			console.log(err.code+' : '+err.message);
			MessageBar.showError(err.code+' : '+err.message);
			if(err.code==90){
				console.log('No sap!!!');
				SAPConnection.showLogin();
			}
		}
		else{
			console.log(e.responseText);
			console.log(e);
			MessageBar.showError('HttpStatus: '+e.status+' '+e.statusText+'\t'+e.responseText);
		}
		spinnerDisabled=false;
	}
	
	return{
		init 		: init,
		handle		: handle
	}
	
})();