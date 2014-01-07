<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:if test="domain.typeid == 1">
  <s:set value="%{getText('admin.navi.headline.title.aboutusmanagement')}" var="pagetitle"/>
</s:if>
<s:elseif test="domain.typeid == 2">
  <s:set value="%{getText('admin.navi.headline.title.contactusmanagement')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 3">
  <s:set value="%{getText('main.left.jsp.terms.and.conditions')}" var="pagetitle"/>
</s:elseif>

<s:elseif test="domain.typeid == 4">
  <s:set value="%{getText('main.bottom.jsp.what.anyshopstyle')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 6">
  <s:set value="%{getText('main.bottom.jsp.privacy.message')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 7">
  <s:set value="%{getText('main.bottom.jsp.legal.areas')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 9">
  <s:set value="%{getText('main.bottom.jsp.send.fee')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 10">
  <s:set value="%{getText('main.bottom.jsp.pay.safe')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 11">
  <s:set value="%{getText('main.bottom.jsp.track.order')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 12">
  <s:set value="%{getText('main.bottom.jsp.return.money')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 13">
  <s:set value="%{getText('main.bottom.jsp.size.list')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 15">
  <s:set value="%{getText('main.bottom.jsp.why.regist')}" var="pagetitle"/>
</s:elseif>
<s:elseif test="domain.typeid == 16">
  <s:set value="%{getText('main.bottom.jsp.fashion.adviser')}" var="pagetitle"/>
</s:elseif>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<script type="text/javascript" src="${ctx}/lookfor/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/lookfor/common/js/gallery.js"></script>

<s:form id="appfrm" action="/admin/general!saveOrUpdate.action" method="post" enctype="multipart/form-data">
<!-- 	隐藏域 -->
	<s:token />
    <s:hidden name="defaultResult" value="edit"/>
  
	<s:hidden name="domain.oid"/>
	<s:hidden name="domain.status"/>
	<s:hidden name="domain.createtime"/>
	<s:hidden name="domain.typeid"/>
    
<div id="tab2" class="tab-content">


      <fieldset>
        <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->

        <p>
          <label>
            <s:text name="admin.label.comm.english.title"/>:
          </label>
          <input type="text" name="domain.etitle" value="${domain.etitle}" class="text-input medium-input">
          <%--<span class="input-notification success png_bg">Successful message</span>--%>
          <s:if test="%{null != fieldErrors['domain.etitle']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.etitle'][0]}</span>
          </s:if>
          <%--<br><small><s:fielderror fieldName="domain.etitle" /></small>--%>
        </p>
        <p>
          <label>
            <s:text name="admin.label.comm.english.content"/>:
          </label>
          <textarea id="EContentsEditor" name="domain.econtent" style="display:none;">${domain.econtent}</textarea>
        </p>
        
        <p>
          <label>
            <s:text name="admin.label.comm.chinese.title"/>:
          </label>
          <input type="text" name="domain.title" value="${domain.title}" class="text-input medium-input">
        </p>
        <p>
          <label>
            <s:text name="admin.label.comm.chinese.content"/>:
          </label>
          <textarea id="ContentsEditor" name="domain.content" style="display:none;">${domain.content}</textarea>
        </p>
        
        <p/>
        <p/>
        
        <div class="managerCell002">
		<sec:authorize ifAnyGranted="ROLE_ADMIN">
		</sec:authorize>
          <span class="Button002" id="savebtn">
            <s:text name="global.button.label.save"/>
          </span>
        </div>

      </fieldset>

      <div class="clear"></div>
      <!-- End .clear -->

  </div>
  
</s:form>

<p></p>
<p></p>


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
			
			//CKFinder.setupCKEditor( newsEditor1, { basePath : '${ctx}/lookfor/common/ckfinder/'} ) ;
			//CKFinder.setupCKEditor( newsEditor2, { basePath : '${ctx}/lookfor/common/ckfinder/'} ) ;
		}
	} catch (e) {alert(e);}
	
	$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
	
	$('#savebtn').click(function(event){
		$("#tab2 .Button002").button("disable");
		$('#appfrm')[0].submit();
	});

});
</script>


<%@ include file="/jsp/admin/include/foot.inc"%>