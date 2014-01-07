<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{'Any Shop Style Logo Upload'}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<style>
	#logogallery li { width: 230px; }
</style>

<script type="text/javascript" src="${ctx}/lookfor/common/js/gallery.js"></script>

<div id="tab2" class="tab-content" style="display: block;">

	<br/>

      <fieldset>
        <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->

        <p>
			<ul id="logogallery" class="gallery ui-helper-reset ui-helper-clearfix" style="border: 0px solid #faa;"></ul>
    		<br/>
    		<button id=logo_upload>Logo Upload</button>
    		<small style="color:red;">Size ( 311 × 97 px) </small>
        </p>
        

        
      </fieldset>

      <div class="clear"></div>
      <!-- End .clear -->

  </div>


<s:action namespace="/admin" name="upload!list" executeResult="true">
       <s:param name="uploadUrl">img</s:param>
       <s:param name="destDir">/</s:param>
       <s:param name="destFileName" value=""/>
</s:action>

<script type="text/javascript">

$(document).ready(function(){
	showAllLogos();
});
function showAllLogos(){
	gallery1 = $('#logo_upload').galleryUpload({GUID: 'logouid', galleryContainer:"#logogallery", destFileName: 'image/logo.png',
						getLiHtml: getLogoPhotoHtml1, maximum:1, imgW: 230, imgH: 72,
						GalleryValidator: validatorLogo
					});

}
/////upload video
function uploadCompeled(html, GUID) {
	//$(document).data(GUID).showGallery(html);
	location.reload();
}
function validatorLogo(file){
	var extArray = new Array(".png");
	var allowSubmit = false;
	if (!file) return true;

	while (file.indexOf("\\") != -1)
		file = file.slice(file.indexOf("\\") + 1);

	var ext = file.slice(file.indexOf(".")).toLowerCase();

	for (var i = 0; i < extArray.length; i++) {
		if (extArray[i] == ext) { allowSubmit = true; break; }
	}
	if (!allowSubmit){
		alert("Please select a .png picture !");
	}
	return allowSubmit;
}
function getLogoPhotoHtml(phtotsrc, uuid){
	var lihtml = '';
    	lihtml += '<li class="ui-widget-content ui-corner-tr" oid="vvideo">'+
    	'<h5 class="ui-widget-header">High Tatras</h5>'+
    	'<img class="preview" height="110" title=""'+
		' filename=""' +
		' objid=""' +
		' oid=""' +
		' moduleid=""' +
		' src="${ctx}/' + phtotsrc + '"' +
    	' uri=""'+
    	' />'+
    	'<a href="${ctx}/' + phtotsrc + '" target="_blank" title="View larger image" class="ui-icon ui-icon-zoomin"></a>'+
    	'<a href="javascript:$(document).data(\'' + uuid + '\').removeUploadFile(\'vvideo\');" title="Delete this image" class="ui-icon ui-icon-trash"></a>'+
    	'</li>';
	return (lihtml);
}

function getLogoPhotoHtml1(){
	return getLogoPhotoHtml('image/logo.png','logouid');
}


///////~~end upload

</script>


<%@ include file="/jsp/admin/include/foot.inc"%>