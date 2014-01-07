<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/taglibs.inc"%>

<s:set var="galleryUploadButton" value="%{#parameters.galleryUploadButton == null ? 'ffu_upload' : #parameters.galleryUploadButton[0] }" />
<s:set var="galleryContainer" value="%{#parameters.galleryContainer == null ? 'UploadPhotos' : #parameters.galleryContainer[0] }" />
<s:set var="galleryFieldName" value="%{#parameters.galleryFieldName == null ? 'domain.atts' : #parameters.galleryFieldName[0] }" />

<s:if test="%{#parameters.uploadUrl != null}">
  <s:set value="#parameters.uploadUrl[0]" var="uploadUrl"/>
</s:if>
<s:else>
  <s:set value="'im4j'" var="uploadUrl"/>
</s:else>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

if(request.getAttribute("__gallery_flashfileupload_page") == null) {
	request.setAttribute("__gallery_flashfileupload_page", "1");
%>

<script type="text/javascript" src="${ctx}/lookfor/common/js/gallery.js"></script>
<link rel="stylesheet" href="${ctx}/lookfor/common/jquery/prettyphoto/css/prettyPhoto.css" type="text/css" media="screen" title="prettyPhoto main stylesheet"/>
<script type="text/javascript" src="${ctx}/lookfor/common/jquery/prettyphoto/jquery.prettyPhoto.js"></script>

<style type="text/css">
<!--/*
<s:if test="%{#parameters.zoomW != null}">
.UploadPhotos li img.preview {width:<s:property value="#parameters.zoomW"/>px;height:<s:property value="#parameters.zoomH"/>px;}
</s:if>
*/
-->
</style>
<style type="text/css">
.ui-progressbar-value { background-image: url(${ctx}/lookfor/images/common/pbar-ani.gif); }
	.UploadPhotos {padding: 4px 15px 4px 15px;  }
	.UploadPhotos .ui-widget-header {font-size: 9.13333px;vertical-align:baseline;opacity:1;height:7px;}
	.gallery li { min-width: 96px; min-height: 103px; float: left; padding: 5px; margin: 3px 3px 3px 0; text-align: center; cursor: move; }
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
		<label for="_upfile">Select a file : </label>
		<input type="file" id="_upfile" name="upload"/>
 		<input type="hidden" name="GUID" id="GUID" value="<s:property value="#parameters.GUID"/>"/>
 		<input type="hidden" name="width" value="<s:property value="#parameters.width"/>"/>
 		<input type="hidden" name="height" value="<s:property value="#parameters.height"/>"/>
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

function getFlashFileUploadUrl(ctnr){
	//alert($('#' + ctnr).galleryUpload().options('uploadUrl'));
	return $('#' + ctnr).galleryUpload().options('uploadUrl');
}

function getLiHtml(items){
	var lihtml = '';

	$.each(items, function(i,item){
		var headerhtml = '<span class="ui-icon ui-icon-home" style="display:' + ( (item.isvisible==1)?'':'none' ) + ';position:relative;top:-6px;width:16px;height:13px;margin-left:auto;margin-right:auto;"></span>';
    	lihtml += '<li class="ui-widget-content ' + ( (item.isvisible==1)?'ui-state-highlight':'' ) + ' ui-corner-tr" oid="' + item.tmpid + '">'+
    	'<h5 class="ui-widget-header">'+ headerhtml +'</h5>'+
    	'<img class="preview" title="' + item.title + '"'+
		' filename="' + item.title + '"' +
		' objid="' + item.objid + '"' +
		' isvisible="' + item.isvisible + '"' +
		' oid="' + (item.oid!==null?item.oid:'') + '"' +
		' moduleid="' + item.moduleid + '"' +
    	' src="${ctx}/' + item.previewImg + '"'+
    	' uri="' + item.uri + '"'+
    	' />'+
    	'<a href="${ctx}/' + item.uri + '" rel="prettyPhoto[ffu]" title="larger image" class="ui-icon ui-icon-zoomin"></a>'+
<s:if test="%{#parameters.readOnly[0] == 'true'}">
		'<a href="javascript:alert(\'<s:text name="global.hintinfo.nopermission"/>\');" title="Delete this image" class="ui-icon ui-icon-trash"></a>'+
</s:if><s:else>
		'<a href="javascript:void(0);" onclick="$(this).parents(\'.ffuctnr\').galleryUpload().removeUploadFile(\'' + item.tmpid + '\')" title="Delete this image" class="ui-icon ui-icon-trash"></a>'+
</s:else>
    	'</li>';
	});
	
	return (lihtml);
}

function uploadSuccess(data){
	var obj = $.parseJSON(data);
	$('#' + obj.uploadparams).galleryUpload().showGallery(getLiHtml([obj.uploadSuccessItem]));
}

function UploadComplete(galleryCntr){
	//alert(galleryCntr);
	$('#dialog-upload-ffu'+galleryCntr).dialog("close");
}

function prettyPhotoCallback(container){
	container = '#' + container;
	var callback = this;
	this.selectedHeadler = function(){
		var $me = $(this);
		$me.toggleClass("ui-state-highlight");
		var preimg = $me.find("img.preview");
		preimg.attr("isvisible", ($me.hasClass("ui-state-highlight")?"1":"0"));
		// change input isvisible value
		var idx = $(container).children('li').index($me);
		var fieldName = $(container).galleryUpload().options('fieldName') + '[' + idx + '].isvisible';
		$(container).children('div[formfields="_alluploadfields"]').find(':input[name="' + fieldName + '"]').val(preimg.attr("isvisible"));
		// add header icon
		$me.find(">h5>span").toggle();
	}
	/*$(container + ">li").bind("dblclick", function(){
		alert(1);$(this).toggleClass("ui-state-highlight");return false;
		});//bind会绑定多次事件，执行多次*/
	$(container + ">li").each(function(idx, el){
		el.ondblclick = callback.selectedHeadler;
	});
	//prettyPhoto deal with
	$(container + ":first a[rel^='prettyPhoto']").prettyPhoto(
			{animation_speed:'normal',theme:'light_rounded',slideshow:3000, autoplay_slideshow: true,
				custom_markup: '<div id="bsap_1259344" class="bsarocks bsap_d49a0984d0f377271ccbf01a33f2b6d6"></div><div id="bsap_1237859" class="bsarocks bsap_d49a0984d0f377271ccbf01a33f2b6d6" style="height:260px"></div><div id="bsap_1251710" class="bsarocks bsap_d49a0984d0f377271ccbf01a33f2b6d6"></div>',
				social_tools: ''
			});
}
</script>
<%
} // end if(request.getAttribute("__gallery_flashfileupload_page") == null)
%>
<div id="dialog-upload-ffu<s:property value="galleryContainer" />" style="display:none;" title="File upload">

  <object id="flashFileUpload<s:property value="galleryContainer" />"
    codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab#version=10,0,22,87"
    width="100%" height="100%" align="middle"
    classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">
    <param name="flashvars" value="fileTypeDescription=JPG/PNG格式图片&fileTypes=*.jpg;*.jpeg;*.png&uploadSuccessFunction=uploadSuccess&completeFunction=UploadComplete&fileSizeLimit=5120000&totalUploadSize=51200000&galleryContainer=<s:property value="galleryContainer" />" />
    <param name="movie" value="${ctx}/lookfor/common/FlashFileUpload.swf" />
    <param name="src" value="${ctx}/lookfor/common/FlashFileUpload.swf" />
    <param name="wmode" value="transparent" />
    <param name="play" value="true" />
    <param name="loop" value="-1" />
    <param name="quality" value="high" />
    <param name="bgcolor" value="#869ca7" />
    <param name="salign" value="lt" />
    <param name="menu" value="-1" />
    <param name="base" value="" />
    <param name="allowscriptaccess" value="sameDomain" />
    <param name="scale" value="noscale" />
    <param name="devicefont" value="0" />
    <param name="embedmovie" value="0" />
    <param name="bgcolor" value="" />
    <param name="swremote" value="" />
    <param name="moviedata" value="" />
    <param name="seamlesstabbing" value="1" />
    <param name="profile" value="0" />
    <param name="profileaddress" value="" />
    <param name="profileport" value="0" />
    <param name="allownetworking" value="all" />
    <param name="allowfullscreen" value="true" />

    <embed src="${ctx}/lookfor/common/FlashFileUpload.swf"
      flashvars="fileTypeDescription=JPG/PNG格式图片&fileTypes=*.jpg;*.jpeg;*.png&uploadSuccessFunction=uploadSuccess&completeFunction=UploadComplete&fileSizeLimit=5120000&totalUploadSize=51200000&galleryContainer=<s:property value="galleryContainer" />"
      quality="high" bgcolor="#869ca7"
      wmode="transparent"
        play="true"
        loop="false"
      width="100%" height="100%"
      name="fileUpload"
      align="middle"
      allowscriptaccess="samedomain"
      type="application/x-shockwave-flash"
      pluginspage="http://www.macromedia.com/go/getflashplayer">
    </embed>

  </object>

</div>
<script type="text/javascript">
//uploadPage=<%=basePath%>admin/upload!${uploadUrl}.action?uploadparams=<s:property value="galleryContainer" />
$(document).ready(function(){
	if($('#<s:property value="galleryUploadButton"/>').size() > 0)
		$('#<s:property value="galleryUploadButton"/>')
					.galleryUpload({
						galleryContainer: '<s:property value="galleryContainer"/>',
						fieldName: '<s:property value="galleryFieldName"/>',
						objId: '<s:property value="domain.objid"/>',
						moduleId: '<s:property value="domain.moduleid"/>',
						uploadUrl: '<%=basePath%>admin/upload!${uploadUrl}.action?uploadparams=<s:property value="galleryContainer" />',
						// GUID: '__gallery_Fi_ffu',
						getLiHtml: getLiHtml, uploadWay: 'ffu', afterShow: prettyPhotoCallback,
						galleryItems: getGalleryItems<s:property value="galleryContainer"/>
					});
	<s:if test="%{#parameters.readOnly[0] == 'true'}">
	$('#<s:property value="galleryUploadButton"/>').attr('disabled',true);
	</s:if>
});
function getGalleryItems<s:property value="galleryContainer"/>(){
	var items = <s:property value="jsonItems" escapeHtml="false" />;
	return items;
}

</script>

