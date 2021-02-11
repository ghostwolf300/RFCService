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

var PurchaseOrder=(function(){
	
	var $fileChooser;
	var $saveAll;
	var $add;
	var $login;
	var $testrun;
	var file;
	
	function init(){
		$fileChooser=$('#btn_choose');
		$export=$('#btn_export');
		$saveAll=$('#btn_save_all');
		$add=$('#btn_add');
		$login=$('#sap_login_do');
		$testrun=$('#chk_testrun_all');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$add.click(_addOrder);
		$saveAll.click(_saveAll);
		$fileChooser.change(_importFile);
		$export.click(_exportData);
		$testrun.change(_setTestRunAll);
		$('input.chk_testrun').change(_setTestRunSingle);
	}
	
	function _importFile(){
		file=$fileChooser[0].files[0]
		
		var reader=new FileReader();
		
		reader.onloadend=function(event){
			var arrayBuffer=reader.result;
			var options={type : 'array'};
			var workbook=XLSX.read(arrayBuffer,options);
			var sheetName=workbook.SheetNames;
			var sheet=workbook.Sheets[sheetName];
			var rowData=XLSX.utils.sheet_to_row_object_array(sheet);
			//var jsonData=JSON.stringify(rowData);
			_addOrders(rowData);
		}
		_clearOrders();
		reader.readAsArrayBuffer(file);
	}
	
	function _exportData(){
		var reader=new FileReader();
		
		reader.onloadend=function(event){
			var arrayBuffer=reader.result;
			var options={type : 'array'};
			var workbook=XLSX.read(arrayBuffer,options);
			var sheetName=workbook.SheetNames;
			var sheet=workbook.Sheets[sheetName];
			var rows=XLSX.utils.sheet_to_row_object_array(sheet);
			var idMap=_getPurchaseOrderIds();
			console.log(idMap);
			for(var i=0;i<rows.length;i++){
				let row=rows[i];
				let poId=idMap.get(row.orderNum);
				if(poId){
					console.log(row);
					sheet[XLSX.utils.encode_cell({r: i+1, c: 0})] = {t: 's', v: poId};
				}
			}
			XLSX.writeFile(workbook,'Create PO - results.xlsx');
		}
		
		if(file){
			reader.readAsArrayBuffer(file);
		}
	}
	
	function _getPurchaseOrderIds(){
		var idMap=new Map();
		
		$('div#orders>div#order').each(function(){
			let lineNumber=$(this).data('line_number');
			let poId=$(this).find('div#order_header input#document_number').val();
			//console.log(lineNumber+' '+poId);
			idMap.set(lineNumber,poId);
		});
		
		return idMap;
	}
	
	function _setTestRunAll(){
		var test=$testrun.is(':checked');
		$('div.order_controls').each(function(){
			$(this).find('input.chk_testrun').prop('checked',test);
		});
	}
	
	function _setTestRunSingle(){
		var test=true;
		$('div.order_controls').each(function(){
			if($(this).find('input.chk_testrun').is(':checked') == false){
				test=false;
				return false;
			}
		});
		$testrun.prop('checked',test);
		
	}
	
	function _clearOrders(){
		$('div#orders').empty();
	}
	
	function _addOrders(orderLines){
		var orderNum;
		orderLines.forEach(function(line){
			if(line.rowType=='H' && line.orderNum!=orderNum){
				console.log('Creating order: '+line.orderNum);
				orderNum=line.orderNum;
				_createOrderHeader(orderNum,line);
			}
			else if(line.rowType=='L' && line.orderNum==orderNum){
				console.log('Adding line for order '+orderNum);
				_createOrderItem(orderNum,line);
			}
			
		});
	}
	
	function _createOrderHeader(orderNum,header){
		var $orderDiv=$('div.order_template').clone(true);
		$orderDiv.removeClass('order_template');
		$orderDiv.addClass('order');
		$orderDiv.attr('data-line_number', orderNum);
		$orderDiv.find('input.btn_save_single').attr('onclick','PurchaseOrder.saveSingle('+orderNum+')');
		var $orderHeaderDiv=$orderDiv.find('div#order_header');
		
		$orderHeaderDiv.find('input#vendor').val(header.supplier);
		$orderHeaderDiv.find('input#order_type').val(header.orderType);
		$orderHeaderDiv.find('input#po_date').val(Util.getHtmlDate(header.poDate));
		$orderHeaderDiv.find('input#purch_org').val(header.purchOrg);
		$orderHeaderDiv.find('input#purch_group').val(header.purchGroup);
		$orderHeaderDiv.find('input#delivery_date').val(Util.getHtmlDate(header.deliveryDate));
		$orderHeaderDiv.find('input#plant').val(header.plant);
		$orderHeaderDiv.find('input#storage_loc').val(header.storageLoc);
		$orderHeaderDiv.find('input#your_reference').val(header.yourReference);
		$orderHeaderDiv.find('input#our_reference').val(header.ourReference);
		
		var $tbody=$orderDiv.find('table.order_items_table tbody');
		$tbody.empty();
		
		$orderDiv.appendTo('div#orders');
	}
	
	function _createOrderItem(orderNum,item){
		
		$orderDiv=$('div#orders').find("[data-line_number='"+orderNum+"']")
		$orderItemsTable=$orderDiv.find('table.order_items_table');
		$tbody=$orderItemsTable.find('tbody');
		var rowCount=$tbody.find('tr').length;
		var itemNo;
		if(rowCount==0){
			itemNo=10;
		}
		else{
			itemNo=rowCount*10+10;
		}
		$tbody.append(_getItemRow(item,orderNum,itemNo));
		
	}
	
	function _getItemRow(item,orderNum,itemNum){
		$tr=$('div.order_template').find('table.order_items_table tbody>tr').clone();
		$tr.find('input#item_no').val(itemNum);
		$tr.find('input#material').val(item.material);
		$tr.find('input#quantity').val(item.qty);
		$tr.find('input#net_price').val(item.netPrice);
		$tr.find('input#tax_code').val(item.taxCode);
		$tr.find('textarea#item_text').text(item.text);
		var $address=$tr.find('a#address');
		var addressJson={
				'title'			: item.title,
				'name1'			: item.name1,
				'name2'			: item.name2,
				'street'		: item.street,
				'houseNumber'	: item.houseNumber,
				'postCode'		: item.postCode,
				'city'			: item.city,
				'countryCode'	: item.countryCode
		}
		
		$address.data('json',addressJson);
		$address.text(addressJson.name1+' '
						+((addressJson.name2) ? addressJson.name2 + ' ' : '' )
						+addressJson.street+' '
						+addressJson.houseNumber+' '
						+addressJson.postCode+' '
						+addressJson.city);
		$address.on('click',{orderNum : orderNum,itemNum : itemNum},_changeAddress);
		
		//$address.attr('onclick','PurchaseOrder.changeAddress('+orderNum+')');
		return $tr;
	}
	
	function _addOrder(){
		var orderNum=_getLineNumber();
		$orderDiv=$('div.order_template').clone(true);
		$orderDiv.removeClass('order_template');
		$orderDiv.addClass('order');
		$orderDiv.attr('data-line_number', orderNum);
		$orderDiv.find('input.btn_save_single').attr('onclick','PurchaseOrder.saveSingle('+orderNum+')');
		$orderDiv.appendTo('div#orders');
	}
	
	function _getLineNumber(){
		var	a=0;
		var b=0;
		$('div#order').each(function(){
			a=Number($(this).data('line_number'));
			if(a>b){
				b=a;
			}
		});
		return b+1;
	}
	
	function _saveAll(){
		console.log('saving all...');
		var orderJson;
		$('div#order').each(function(){
			orderJson=_getOrderJSON($(this));
			_saveOrder(orderJson);
		});
			
	}
	
	function saveSingle(lineNum){
		
		console.log('Saving order '+lineNum);
		var $orderDiv=$('div#orders').find("[data-line_number='"+lineNum+"']")
		var orderJson=_getOrderJSON($orderDiv);
		_saveOrder(orderJson);
	}
	
	function _saveOrder(orderJson){
		var url=contextPath+'po/save';
		var data=JSON.stringify(orderJson);
		console.log(data);
		
		$.ajax({
			url : url,
			method : "POST",
			data : data,
			dataType : "json"
		}).done(function(response){
			_handleResponse(response);
		}).fail(function(e){
			ErrorHandler.handle(e);
		}).always(function(){
			
		});
	}
	
	function _fillSavedData(po){
		$orderDiv=$('div#orders').find("[data-line_number='"+po.metaData+"']")
		$orderDiv.find('div#order_header').find('input#document_number').val(po.id);
	}
	
	function _handleResponse(response){
		
		if(response.test){
			MessageBar.showSuccess("PO created in test mode");
		}
		else{
			MessageBar.showSuccess("PO created: "+response.poNumber);
		}
		
		var $orderDiv=$('div#orders').find("[data-line_number='"+response.metaData+"']")
		var $messageDiv=$orderDiv.find('div.bapi_messages');
		var $tbody=$messageDiv.find('tbody');
		
		$messageDiv.show();
		$tbody.empty();
		response.lines.forEach(function(line,index){
			$tbody.append(_createResponseMessageRow(line));
		});
		
		if(response.poNumber!=-1){
			$orderDiv.find('div#order_header').find('input#document_number').val(response.poNumber);
		}
	}
	
	function _createResponseMessageRow(line){
		var $row=$('<tr>'+
				"<td class='message_type'>"+line.type+'</td>'+
				'<td>'+line.number+'</td>'+
				'<td>'+line.id+'</td>'+
				'<td>'+line.row+'</td>'+
				'<td>'+line.message+'</td>'+
				'</tr>');
		if(line.type == 'W'){
			$row.addClass('warning');
		}
		else if(line.type == 'I'){
			$row.addClass('info');
		}
		else if(line.type == 'E'){
			$row.addClass('error');
		}
		else if(line.type == 'S'){
			$row.addClass('success');
		}
		return $row;
	}
	
	function _getOrderJSON($orderDiv){
		var $orderHeaderDiv=$orderDiv.find('div#order_header');
		var $orderItemsDiv=$orderDiv.find('div#order_items');
		
		var lineNumber=$orderDiv.attr('data-line_number');
		
		var orderJson={
				'id'				: 	Number($orderHeaderDiv.find('input#document_number').val()),
				'companyCode'		:	'07',
				'documentType'		:	$orderHeaderDiv.find('input#order_type').val(),
				'purchasingOrg'		:	$orderHeaderDiv.find('input#purch_org').val(),
				'purchasingGroup'	:	$orderHeaderDiv.find('input#purch_group').val(),
				'documentDate'		:	$orderHeaderDiv.find('input#po_date').val(),
				'vendor'			:	$orderHeaderDiv.find('input#vendor').val(),
				'supplierPlant'		:	$orderHeaderDiv.find('input#plant').val(),
				'yourReference'		:	$orderHeaderDiv.find('input#your_reference').val(),
				'ourReference'		:	$orderHeaderDiv.find('input#our_reference').val(),
				'lineItems'			:	_getOrderLineItems(
											$orderItemsDiv,
											Number($orderHeaderDiv.find('input#document_number').val()),
											$orderHeaderDiv.find('input#plant').val(),
											$orderHeaderDiv.find('input#storage_loc').val(),
											$orderHeaderDiv.find('input#delivery_date').val()
										),
				'metaData'			:	lineNumber,
				'test'				:	$orderDiv.find('input.chk_testrun').is(':checked')
		}
		
		return orderJson;

	}
	
	function _getOrderLineItems($orderItemsDiv, po, plant, storageLoc,deliveryDate){
		var itemsArr=[];
		var lineItemJson;
		
		$tbody=$orderItemsDiv.find('table#order_items_table').find('tbody');
		
		$tbody.find('tr').each(function(){
			var itemNo=Number($(this).find('input#item_no').val());
			lineItemJson={
					'poId'				:	po,
					'item'				:	itemNo,
					'material'			:	$(this).find('input#material').val(),
					'plant'				:	plant,
					'storageLocation'	:	storageLoc,
					'quantity'			:	Number($(this).find('input#quantity').val()),
					'netPrice'			:	Number($(this).find('input#net_price').val()),
					'valuationType'		:	'',
					'taxCode'			:	$(this).find('input#tax_code').val(),
					'textLines'			: _getItemTextLines(po,itemNo,$(this).find('textarea#item_text')),
					'address'			: $(this).find('a#address').data('json'),
					'deliveryDate'		: deliveryDate
			}
			itemsArr.push(lineItemJson);
		});
			
		return itemsArr;
		
	}
	
	function _getItemTextLines(po,itemNo,$itemText){
		var lines=$itemText.val().split('\n');
		var textArr=[];
		var textLineJson;
		
		for(var i=0;i<lines.length;i++){
			textLineJson={
					'poId'		: po,
					'item'		: itemNo,
					'textId'	: 'F02',
					'textForm'  : '*',
					'textLine'	: lines[i]
			}
			textArr.push(textLineJson);
		}
		
		return textArr;
	}
	
	function _changeAddress(event){
		var $address=$(event.target);
		var addressJson=$address.data('json');
	}
	
	return{
		init 			: init,
		saveSingle 		: saveSingle
	}
	
})();

var PODetails=(function(){
	
	var $btnFind;
	
	function init(){
		console.log('PODetails.init')
		$btnFind=$('input#btn_find_po');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$btnFind.click(_findPO);
	}
	
	function _findPO(){
		
		console.log('find PO');
		
		var url=contextPath+'po/find';
		var poId=$('input#fld_find_po').val();
		
		$.ajax({
			url : url,
			method : "GET",
			data : {
				poId : poId
			},
			contentType: 'application/json',
			dataType : "json"
		}).done(function(po){
			_handleResponse(po);
		}).fail(function(e){
			ErrorHandler.handle(e);
		}).always(function(){
			
		});
	}
	
	function findPO(poId,_callback){
		
		var url=contextPath+'po/find';
		$.ajax({
			url : url,
			method : "GET",
			data : {
				poId : poId
			},
			contentType: 'application/json'
		}).done(function(po){
			_callback(po);
		}).fail(function(e){
			ErrorHandler.handle(e);
		}).always(function(){
			
		});
	}
	
	function _handleResponse(po){
		MessageBar.showSuccess("PO document found");
		console.log(po);
		$header=$('div#header');
		$header.find('span#po').text(po.id);
		$header.find('input#created_date').val(Util.getHtmlDate2(po.createdDate));
		$header.find('input#created_by').val(po.createdBy);
		$header.find('input#vendor').val(po.vendor);
		$header.find('input#order_type').val(po.documentType);
		$header.find('input#po_date').val(Util.getHtmlDate2(po.documentDate));
		$header.find('input#purch_org').val(po.purchasingOrg);
		$header.find('input#purch_group').val(po.purchasingGroup);
		$header.find('input#your_reference').val(po.yourReference);
		$header.find('input#our_reference').val(po.ourReference);
		
		$items=$('div#items table#item_table>tbody');
		$items.empty();
		
		po.lineItems.forEach(function(item){
			$items.append(_getItemRow(item));
		});
		
	}
	
	function _getItemRow(item){
		var $row=$('<tr>'
					+'<td>'+item.item+'</td>'
					+'<td>'+item.material+'</td>'
					+'<td>'+item.quantity+'</td>'
					+'<td>'+item.plant+'</td>'
					+'<td>'+item.storageLocation+'</td>'
					+'<td>'+item.deliveryDate+'</td>'
					+'<td>'+item.taxCode+'</td>'
				+'</tr>');
		return $row;
	}
	
	return{
		init : init,
		findPO : findPO
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

var SalesPrice=(function(){
	
	var $btnTest;
	
	function init(){
		$btnTest=$('input#btn_test');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$btnTest.click(_test);
	}
	
	function _test(){
		console.log('Sales Price Test');
		
		var url=contextPath+'material/salesPrice/test';
		
		$.ajax({
			url : url,
			method : "GET",
			dataType : "json"
		}).done(function(response){
			console.log(response);
		}).fail(function(e){
			ErrorHandler.handle(e);
		}).always(function(){
			
		});
	}
	
	return{
		init : init
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
		console.log('entry div: '+id);
		var $rowDiv=$entryDiv.parent();
		var $fieldsDiv=$rowDiv.find('.babi-field-list:not(.bapi-add-table-entry)').first().clone(true);
		$fieldsDiv.insertBefore('#'+id);
	}
	
	function _delTableEntry(event){
		var $btn=$(event.target);
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
			var data=JSON.stringify(_csvToJsonArray(run.id,csv));
			//console.log(data);
			AjaxUtil.post('material/saveRunData',data,'json',_handleSuccess,ErrorHandler.handle,_uploadProgressHandler);
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
	                jsonArr.push(_createDataJson(runId,rowNumber,j,data[j]));
	            }
            }
            rowNumber++;
        }
        return jsonArr;
	}
	
	function _createDataJson(runId,rowNumber,fieldIndex,value){
		var dataJson={
				'runId'		: runId,
				'rowNumber'	: rowNumber,
				'fieldIndex': fieldIndex,
				'value'		: value
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
		_updateStatusPie(100,5,16);
	}
	
	function _bindEventHandlers(){
		$btnCreateWorkers.click(_createWorkers);
		$('button.worker-start').click(_startWorker);
		
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
			$row.find('button.worker-start').attr('data-id',worker.id);
			$row.attr('hidden',false);
			_updateWorkerRow($row,worker);
			$tbody.append($row);
		});
	}
	
	function _updateWorkerRow($row,worker){
		$row.find('.worker-id').text(worker.id);
		$row.find('.worker-materials').text(worker.materials);
		$row.find('.worker-success').text(worker.success);
		$row.find('.worker-errors').text(worker.errors);
		$progressBar=$row.find('div#worker_progress');
		$progressBar.attr('aria-valuenow',worker.progress);
		$progressBar.width(worker.progress+'%');
		let statusText;
		switch(worker.status){
			case 0 :
				statusText='CREATED';
				break;
			case 1 :
				statusText='RUNNING';
				break;
			case 2 :
				statusText='STOPPED';
				break;
			case 3 :
				statusText='ERROR';
				break;
			case 4 : 
				statusText='FINISHED';
				break;
		}
		$row.find('.worker-status').text(statusText);
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
	}
	
	function _stopWorker(){
		
	}
	
	return{
		init : init
	}
	
})();

var AjaxUtil=(function(){
	
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
	
	return{
		post 	: post,
		get		: get
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