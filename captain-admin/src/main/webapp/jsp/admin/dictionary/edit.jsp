<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.dicmanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<s:form id="appfrm" action="/admin/dict!saveOrUpdate.action" method="post" enctype="multipart/form-data">
<!-- 查询条件 -->
    <s:hidden name="pageNo"/>
    <s:hidden name="pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.key"/>
    <s:hidden name="query.ename"/>
    <s:hidden name="query.name"/>
<!-- 	隐藏域 -->
	<s:token />
	<s:hidden name="domain.oid"/>
	<s:hidden name="domain.status"/>
	<s:hidden name="domain.createtime"/>
    
<div id="tab2" class="tab-content">


      <fieldset>
        <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->

        <p>
          <label>
            <s:text name="admin.label.dic.key"/>:
          </label>
          <input type="text" name="domain.key" value="${domain.key}" class="text-input medium-input">
          <%--<span class="input-notification success png_bg">Successful message</span>--%>
          <s:if test="%{null != fieldErrors['domain.key']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.key'][0]}</span>
          </s:if>
          <%--<br><small><s:fielderror fieldName="domain.key" /></small>--%>
        </p>
        
        <p>
          <label>
            <s:text name="admin.label.dic.ename"/>:
          </label>
          <input type="text" name="domain.ename" value="${domain.ename}" class="text-input medium-input">
        </p>
        <p>
          <label>
            <s:text name="admin.label.dic.edesc"/>:
          </label>
          <textarea name="domain.edesc">${domain.edesc}</textarea>
        </p>
        
        <p>
          <label>
            <s:text name="admin.label.dic.name"/>:
          </label>
          <input type="text" name="domain.name" value="${domain.name}" class="text-input medium-input">
        </p>
        <p>
          <label>
            <s:text name="admin.label.dic.desc"/>:
          </label>
          <textarea name="domain.desc">${domain.desc}</textarea>
        </p>
        
        <p>
          <label>
            <s:text name="admin.label.dic.ordinal"/>:
          </label>
          <input type="text" name="domain.ordinal" value="${domain.ordinal}" class="text-input small-input">
        </p>
        
        <p/>
        <p/>
        
        <div class="managerCell002">
		<sec:authorize ifAnyGranted="ROLE_ADMIN">
		</sec:authorize>
          <span class="Button002" id="savebtn">
            <s:text name="global.button.label.save"/>
          </span>
          <span class="Button002" id="backbtn">
            <s:text name="admin.article.back"/>
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

	$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
	
	$('#savebtn').click(function(event){
		$("#tab2 .Button002").button("disable");
		$('#appfrm')[0].submit();
	});
	$('#backbtn').click(function(event){
		$("#tab2 .Button002").button("disable");
		$('#appfrm')[0].action = '${ctx}/admin/dict!list.action';
		$('#appfrm').submit();
	});

});
function doCancel(){
		location = '${ctx}/admin/dict!list.action?pageNo=${pageNo}&pageSize=${pageSize}';
}

</script>


<%@ include file="/jsp/admin/include/foot.inc"%>