<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('admin.navi.headline.title.management.about.collaboration')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<script type="text/javascript" src="${ctx}/lookfor/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/lookfor/common/ckfinder/ckfinder.js"></script>


<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Loading ...</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">

<s:form cssClass="form-inline" id="appfrm" action="/admin/aboutcollaboration!saveOrUpdate.action" method="post" enctype="multipart/form-data">
<!-- 查询条件 -->
  <input type="hidden" name="treeid" value="${param.treeid}"/>
    <s:hidden name="pageNo"/>
    <s:hidden name="pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.status"/>
    <s:hidden name="query.etitle"/>
    <s:hidden name="query.title"/>
    <s:hidden name="query.type" value="%{domain.type}"/>
    <s:hidden name="query.designer"/>
    <s:hidden name="query.publishtimefrom"/>
    <s:hidden name="query.publishtimeto"/>
  <input type="hidden" name="query_newin" value="${param.query_newin }"/>
  <input type="hidden" name="query_isdesigner" value="${param.query_isdesigner }"/>
<!-- 	隐藏域 -->
	<s:token />
	<s:hidden name="domain.type"/>
	<s:hidden name="domain.status"/>
	<s:hidden name="domain.createtime"/>
	<input type="hidden" name="domain.author" value="${sessionScope.__SecUser.id}"/>
	<input type="hidden" name="domain.authorname" value="${sessionScope.__SecUser.username}"/>
  
  
  <fieldset>
  
  <div class="control-group">
    <label class="control-label"><s:text name="admin.label.comm.code"/>:</label>
    <div class="controls">
      <span class="input-xlarge uneditable-input">${empty domain.oid ? 'Auto Generate' : domain.oid}</span>
      <input type="hidden" name="domain.oid" value="${domain.oid}">
      <p class="help-inline">Uneditable, Auto Generate.</p>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="domain.title"><s:text name="admin.label.comm.chinese.title"/>:</label>
    <div class="controls">
      <input type="text" id="domain.title" name="domain.title" value="${domain.title}" class="input-xlarge">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="domain.etitle"><s:text name="admin.label.comm.english.title"/>:</label>
    <div class="controls">
      <input type="text" id="domain.etitle" name="domain.etitle" value="${domain.etitle}" class="input-xlarge">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="ContentsEditor"><s:text name="admin.label.comm.chinese.content"/>:</label>
    <div class="controls">
      <textarea id="ContentsEditor" name="domain.content" style="display:none;">${domain.content}</textarea>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="EContentsEditor"><s:text name="admin.label.comm.english.content"/>:</label>
    <div class="controls">
      <textarea id="EContentsEditor" name="domain.econtent" style="display:none;">${domain.econtent}</textarea>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for=""></label>
    <div class="controls">
  	 <button id=ffu_upload><s:text name="admin.picture.upload"/></button>
     <span class="help-inline">Size(pix): <code>75X37</code> </span>
	 <br/>
	 <ul id="UploadPhotos" class="gallery ui-helper-reset ui-helper-clearfix" style="border: 0px solid #faa;"></ul>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label" for="domain.ordinal"><s:text name="admin.label.ordinal"/>:</label>
    <div class="controls">
      <input type="text" id="domain.ordinal" name="domain.ordinal" value="${domain.ordinal}" class="input-small">
      <p class="help-inline">Place input a natural number.</p>
    </div>
  </div>
  
  <div class="control-group">
    <div class="controls">
<%--      <a id="draftbtn" class="btn" href="javascript:void(0);"><s:text name="global.button.label.draft"/></a>--%>
<%--      <a id="submitbtn" class="btn" href="javascript:void(0);"><s:text name="global.button.label.submit"/></a>--%>
      <a id="publishbtn" class="btn" href="javascript:void(0);"><s:text name="admin.article.publish"/></a>
<%--      <a id="rejectbtn" class="btn" href="javascript:void(0);"><s:text name="admin.article.reject"/></a>--%>
      <a id="backbtn" class="btn" href="javascript:void(0);"><s:text name="admin.article.back"/></a>
    </div>
  </div>

  </fieldset>
</s:form>

<p></p>
<p></p>


<s:action namespace="/admin" name="upload!list" executeResult="true">
<s:if test="true">
	<s:param name="width" value="0" />
    <s:param name="height" value="0" />
    <s:param name="zoomW" value="0" />
    <s:param name="zoomH" value="0" />
</s:if>
<s:else>
	<s:param name="width" value="720" />
    <s:param name="height" value="440" />
    <s:param name="zoomW" value="140" />
    <s:param name="zoomH" value="86" />
</s:else>
	<s:param name="domain.objid" value="domain.oid" />
<%--	<s:param name="subType" value="domain.sex" />--%>
	<s:param name="uploadUrl">img</s:param>
	<s:param name="DefaultResult">flashFileUpload</s:param>
	<s:param name="readOnly">false</s:param>
</s:action>
<script type="text/javascript">
</script>

<script type="text/javascript">
$(document).ready(function(){
	try
	{
		if ( typeof CKEDITOR == 'undefined' )
		{
			alert('ckeditor load error.');
		} else {
			newsEditor2 = CKEDITOR.replace( 'EContentsEditor', {enterMode : CKEDITOR.ENTER_DIV,
					resize_maxWidth : 660,
					toolbarStartupExpanded : false,
					//extraPlugins : 'autogrow',
					language : "en",
					readOnly : false,
					autoGrow_maxHeight : 800} );
			
			var newsEditor1 = CKEDITOR.replace( 'ContentsEditor', {enterMode : CKEDITOR.ENTER_DIV,
					resize_maxWidth : 660,
					toolbarStartupExpanded : false,
					//extraPlugins : 'autogrow',
					language : "zh",
					readOnly : false,
					autoGrow_maxHeight : 800} );
			
			CKFinder.setupCKEditor( newsEditor1, { basePath : '${ctx}/lookfor/common/ckfinder/'} ) ;
			CKFinder.setupCKEditor( newsEditor2, { basePath : '${ctx}/lookfor/common/ckfinder/'} ) ;
		}
	} catch (e) {alert(e);}
	
	
	$('#draftbtn').click(function(event){
		$(this).siblings("a.btn").attr("disabled", true);
		$('#appfrm_domain_status').val(1);
		$('#appfrm')[0].submit();
	});
	$('#submitbtn').click(function(event){
		$(this).siblings("a.btn").attr("disabled", true);
		$('#appfrm_domain_status').val(2);
		$('#appfrm')[0].submit();
	});
	$('#backbtn').click(function(event){
		$(this).siblings("a.btn").attr("disabled", true);
		$('#appfrm')[0].action = '${ctx}/admin/aboutcollaboration!list.action';
		$('#appfrm').submit();
	});
	$('#publishbtn').click(function(event){
		if($('#appfrm_domain_publishtime').val() == ''){
			$('#appfrm_domain_publishtime').animate({ height: 'toggle', opacity: 'toggle', borderColor: '#faa', borderWidth: 2 }, "normal")
											.animate({ height: 'toggle', opacity: 'toggle' }, "normal")
											;
			return false;
		}
		$('#appfrm_domain_status').val(3);
		$(this).siblings("a.btn").attr("disabled", true);
		$('#appfrm').submit();
	});
	$('#rejectbtn').click(function(event){
		$('#appfrm_domain_status').val(4);
		$(this).siblings("a.btn").attr("disabled", true);
		$('#appfrm').submit();
	});
	$('#backbtn_audit').click(function(event){
		$(this).siblings("a.btn").attr("disabled", true);
		$('#appfrm')[0].action = '${ctx}/admin/aboutcollaboration!tasklist.action';
		$('#appfrm').submit();
	});

});

</script>


  </div>
</div><!-- / end tab-content  -->
<%@ include file="/jsp/admin/include/foot.inc"%>