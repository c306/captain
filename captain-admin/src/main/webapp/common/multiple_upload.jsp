<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/taglibs.inc"%>

<link rel="stylesheet" type="text/css" href="${ctx}/lookfor/common/jquery/jquery-ui/css/ui-lightness/jquery-ui-1.10.2.custom.min.css" />
<style type="text/css">
<!--
#BoxUpload {width:auto;height:auto;}
#BoxUpload ul {margin:0px;padding:0px;}
#BoxUpload li {margin:2px;padding:5px;float:left;}
#BoxUpload li img.preview {clear:right;border:1px solid lightgray;}
<s:if test="%{#parameters.zoomW != null}">
#BoxUpload li img.preview {width:<s:property value="#parameters.zoomW"/>px;height:<s:property value="#parameters.zoomH"/>px;}
</s:if>
#BoxUpload li div {text-align:center;font-family: "Times New Roman"; font-weight: bold;}
#BoxUpload li A {text-decoration:none;font-size:14px;color:red;font-weight:normal;height:24px;margin-top:6px;}
#BoxUpload li A:hover {text-decoration:underline;font-weight: bold;}
.ui-progressbar-value { background-image: url(${ctx}/lookfor/images/pbar-ani.gif); }
-->
</style>
<script src="${ctx}/lookfor/common/jquery/jquery-ui/jquery-ui-1.10.2.custom.min.js" type="text/javascript" language="javascript"></script>

<div id="dialog-upload-form" style="display:none;" title="File upload">
	<form id="upload-form"
	 action="${ctx}/admin/upload!img.action"
	 method="POST" enctype="multipart/form-data" target="upload_frame">
	<fieldset>
		<label for="_upfile">Select a picture : </label>
		<input type="file" id="_upfile" name="upload"/>
 		<input type="hidden" name="zoomW" value="<s:property value="#parameters.zoomW"/>"/>
 		<input type="hidden" name="zoomH" value="<s:property value="#parameters.zoomH"/>"/>
 		<input type="hidden" name="destDir" value="<s:property value="#parameters.destDir"/>"/>
 		<input type="hidden" name="destFileName" value="<s:property value="#parameters.destFileName"/>"/>
<!-- 		<input type="text" name="moduleId"/> -->
<!-- 		<input type="text" name="caption"/> -->
		<div id="progressbar" style="width:80%"></div>
	</fieldset>
	</form>
	<iframe style="display:none;" name="upload_frame"></iframe>
</div>

<script type="text/javascript">
$(document).ready(function(){
	
	$("#dialog-upload-form").dialog({
		autoOpen: false,
		height: 250,
		width: 420,
		modal: true,
		resizable: false,
		buttons: {
			'Upload To Server': function() {
				if($("#_upfile").val() ==''){
					alert('Please pick a image file.');return;
				}
				$(this).dialog('close');
				$("#upload-form").submit();
			},
			Cancel: function() {
				$(this).dialog('close');
			}
		},
		close: function() {
			
		}
	});
	
	$('#create-upload')
		.button()
		.click(function() {
			$('#dialog-upload-form').dialog('open');
            return false;//如果不返回false，FF会提交 表单
		});
	
	$("#BoxUpload").append('<div id="_alluploadfields" style="display:none;"></div>');

	showFile();
});

function showFile(){
	var lihtml = '';
	<s:iterator value="items" status="stat">
    	lihtml += '<li>'+
    	'<img class="preview" title="<s:property value="title" />"'+
		' filename="<s:property value="title" />"' +
		' objid="<s:property value="objid" />"' +
		' oid="<s:property value="oid" />"' +
		' moduleid="<s:property value="moduleid" />"' +
<s:if test="%{zoomW > 0}">
    	' src="${ctx}/zoom<s:property value="uri" />"'+
</s:if>
<s:else>
    	' src="${ctx}<s:property value="uri" />"'+
</s:else>
    	' uri="<s:property value="uri" />"'+
    	' />'+
    	"<div><a href='javascript:void(0);' onclick='removeUploadFile(this);'>删除(Remove)</a></div>"+
    	'</li>';
	</s:iterator>
	uploadCompeled(lihtml);
}
function uploadCompeled(data){
	if(data == '') return;
	try{
		$("#BoxUpload").append(data);
		setupFields();
		//alert($('#BoxUpload').attr('innerHTML'));
		$('#_upfile').val();
	}catch(e){alert("01"+e.message);}
}
function removeUploadFile(a){
	//alert($(a).parent().attr('tagName'));
	$(a).parent().parent().remove();
	setupFields();
}
function setupFields(){
	var allFields = $('#_alluploadfields');
	allFields.empty();
	$("#BoxUpload").find('img').each(function(i){
		var furi = '<input type="hidden" name="domain.atts[' + i + '].uri" value="' + $(this).attr('uri') + '"/>';
		var ftitle = '<input type="hidden" name="domain.atts[' + i + '].title" value="' + $(this).attr('filename') + '"/>';
		var fobjid = '<input type="hidden" name="domain.atts[' + i + '].objid" value="' + $(this).attr('objid') + '"/>';
		var fmoduleid = '<input type="hidden" name="domain.atts[' + i + '].moduleid" value="' + $(this).attr('moduleid') + '"/>';
		var foid = '<input type="hidden" name="domain.atts[' + i + '].oid" value="' + $(this).attr('oid') + '"/>';
		allFields.append(furi).append(ftitle).append(fobjid).append(fmoduleid).append(foid);
	});
}
</script>
