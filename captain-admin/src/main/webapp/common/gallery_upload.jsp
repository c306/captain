<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/taglibs.inc"%>

<s:if test="%{#parameters.uploadUrl != null}">
	<s:set value="#parameters.uploadUrl[0]" var="uploadUrl"/>
</s:if>
<s:else>
	<s:set value="'im4j'" var="uploadUrl"/>
</s:else>

<style type="text/css">
<!--/*
<s:if test="%{#parameters.zoomW != null}">
.UploadPhotos li img.preview {width:<s:property value="#parameters.zoomW"/>px;height:<s:property value="#parameters.zoomH"/>px;}
</s:if>
*/
-->
</style>
<style>
.ui-progressbar-value { background-image: url(${ctx}/lookfor/images/common/pbar-ani.gif); }
	.UploadPhotos {padding: 1px 15px 1px 15px;  }
	.UploadPhotos .ui-widget-header {font-size: 9.13333px;vertical-align:baseline;opacity:1;height:7px;}
	.gallery li { width: 96px; height: 103px; float: left; padding: 0.4em; margin: 0 0.4em 0.4em 0; text-align: center; cursor: move; }
	.gallery li h5 { margin: 0 0 0.4em; cursor: move; }
	.gallery li a { float: right; }
	.gallery li a.ui-icon-zoomin { float: left; }
	.gallery li img { clear: both; }
	.gallery .custom-state-placeholder {height: 60px;}
	span.ui-icon-closethick {text-indent: 9999px; white-space:nowrap; line-height:0px;}
</style>

<div id="dialog-upload-form" style="display:none;" title="File upload">
	<form id="upload-form"
	 action="${ctx}/admin/upload!${uploadUrl}.action"
	 method="POST" enctype="multipart/form-data" target="upload_frame">
	<fieldset>
		<label for="_upfile">Select a picture : </label>
		<input type="file" id="_upfile" name="upload"/>
 		<input type="hidden" name="GUID" id="GUID" value=""/>
 		<input type="hidden" name="width" value=""/>
 		<input type="hidden" name="height" value=""/>
 		<input type="hidden" name="zoomW" value="<s:property value="#parameters.zoomW"/>"/>
 		<input type="hidden" name="zoomH" value="<s:property value="#parameters.zoomH"/>"/>
 		<input type="hidden" name="destDir" id="destDir" value="<s:property value="#parameters.destDir"/>"/>
 		<input type="hidden" name="destFileName" id="destFileName" value="<s:property value="#parameters.destFileName"/>"/>
<!-- 		<input type="text" name="moduleId"/> -->
<!-- 		<input type="text" name="caption"/> -->
	</fieldset>
	</form>
	<iframe style="display:none;" name="upload_frame"></iframe>
	<div id="_upload_progressbar" style="width:80%;margin-top:40px;margin-left:auto;margin-right:auto;"></div>
</div>
<div id="dialog-viewLargerImage" style="display:none;" title=""></div>

<script type="text/javascript">

$(document).ready(function(){
	if($('#create_upload').size() > 0)
	__gallery_Fi_ = $('#create_upload')
					.galleryUpload({fieldName: 'domain.atts', galleryContainer:"#UploadPhotos", getLiHtml: getLiHtml,
						GalleryValidator: validateGalleryType
					});
});
function validateGalleryType(file){
	var extArray = new Array(".jpg",".gif",".png");
	var allowSubmit = false;
	if (!file) return true;

	while (file.indexOf("\\") != -1)
		file = file.slice(file.indexOf("\\") + 1);

	var ext = file.slice(file.indexOf(".")).toLowerCase();

	for (var i = 0; i < extArray.length; i++) {
		if (extArray[i] == ext) { allowSubmit = true; break; }
	}
	if (!allowSubmit){
		alert("Please select jpg | gif | png .");
	}
	return allowSubmit;
}
function getLiHtml(){
	var lihtml = '';

	<s:iterator value="items" status="stat">
    	lihtml += '<li class="ui-widget-content ui-corner-tr" oid="<s:property value="oid" />">'+
    	'<h5 class="ui-widget-header">Gallery</h5>'+
    	'<img class="preview" title="<s:property value="title" />"'+
		' filename="<s:property value="title" />"' +
		' objid="<s:property value="objid" />"' +
		' oid="<s:property value="oid" />"' +
		' moduleid="<s:property value="moduleid" />"' +
<s:if test="%{zoomW > 0}">
    	' src="${ctx}/zoom/<s:property value="uri" />"'+
</s:if><s:else>
    	' src="${ctx}<s:property value="uri" />"'+
</s:else>
    	' uri="<s:property value="uri" />"'+
    	' />'+
    	'<a href="${ctx}/<s:property value="uri" />" target="_blank" title="View larger image" class="ui-icon ui-icon-zoomin"></a>'+
    	'<a href="javascript:__gallery_Fi_.removeUploadFile(\'<s:property value="oid" />\');" title="Delete this image" class="ui-icon ui-icon-trash"></a>'+
    	'</li>';
	</s:iterator>
	return (lihtml);
}

</script>
