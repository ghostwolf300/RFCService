/**
 * 
 */

var $body;
var contextPath;


$(document).ready(initPage);
$(document).on({
	ajaxStart : function(){
		$body.addClass('loading');
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
	        //'Content-Type': 'application/json' <==what if you want to send formdata?! this defaults every ajax call to application/json!
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
 		SAPConnection.init();
 		MaterialCreate.init();
 	}
 	else if(viewId==8){
 		MessageBar.init();
 		MaterialTemplates.init();
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
	
	function init(){
		console.log('MaterialTemplates.init');
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		$('select.function-field-input-type').on('change',_functionFieldInputType);
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
	
	return{
		init : init
	}
	
})();

var MaterialCreate=(function(){
	
	function init(){
		console.log('MaterialCreate.init')
		_bindEventHandlers();
	}
	
	function _bindEventHandlers(){
		
	}
	
	return{
		init : init
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
			MessageBar.showError(e.responseText);
		}
	}
	
	return{
		init 		: init,
		handle		: handle
	}
	
})();