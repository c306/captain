<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<s:set value="%{getText('i18n.edit.title')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">

<%@ include file="/jsp/admin/include/alert.inc"%>

<s:form cssClass="form-horizontal" id="appfrm" action="/acegi/user!saveOrUpdate.action" method="post" >
<%-- enctype="multipart/form-data" --%>
<!-- 查询条件 -->
  <input type="hidden" name="treeid" value="${param.treeid}"/>
    <s:hidden name="query.pageNo"/>
    <s:hidden name="query.pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.username"/>
    <s:hidden name="query.email"/>
<!-- 	隐藏域 -->
	<s:token />
	<s:hidden name="domain.id"/>
  
  <fieldset>
  <!-- 
  <div class="control-group">
    <label class="control-label" for=""><s:text name="i18n.field.pk"/>:</label>
    <div class="controls">
      <span class="input-xlarge uneditable-input">${domain.id}</span>
      
    </div>
  </div>-->
  
  <div class="control-group span4" style="height:100px;">
    <label class="control-label"><s:text name="i18n.field.avatar"/>:</label>
    <div class="controls">
      <s:hidden name="domain.avatar"/>
      <div style="border:1px solid #EDEDED;width:100px;height:117px;"></div>
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.username"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.username" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.nicename"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.nicename" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.firstName"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.firstName" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.lastName"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.lastName" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.email"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.email" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.mobile"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.mobile" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.tel"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.tel" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.qq"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.qq" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.msn"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.msn" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.country"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.country" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.province"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.province" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.city"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.city" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.address"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.address" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.invitcode"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.invitcode" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.updatetime"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.updatetime" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.descn"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.descn" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.enabled"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.enabled" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.accountLocked"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.accountLocked" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.accountExpired"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.accountExpired" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.credentialsExpired"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.credentialsExpired" />
    </div>
  </div>
  <%-- 
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.password"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.password" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.confirmPassword"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.confirmPassword" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.passwordHint"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.passwordHint" />
    </div>
  </div>
  <div class="control-group span4">
    <label class="control-label"><s:text name="i18n.field.roles"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.roles" />
    </div>
  </div> --%>

  </fieldset>

  <!-- button group  -->
  <div class="form-actions" style="padding-left:20px;text-align:center;">
      <a id="submitbtn" class="btn" href="javascript:void(0);"><s:text name="global.button.label.save"/></a>
      <a id="backbtn" class="btn" href="javascript:void(0);"><s:text name="global.button.label.back"/></a>
  </div>
</s:form>

<p></p>
<p></p>



<script type="text/javascript">
$(document).ready(function(){
	var validFields = '#appfrm_domain_username, #appfrm_domain_email';
	$(validFields).blur(function(event){
		
		if($(this).val().trim() == '') {
			$(this).parents('div.control-group').addClass('error');
		} else {
			$(this).parents('div.control-group').removeClass('error');
		}
		
	});
	
	function validateForm() {
		var pass = true;
		$(validFields).each(function(idx, el){
			if($(this).val().trim() == '') {
				pass = false;
				$(this).parents('div.control-group').addClass('error');
			}
		});
		return pass;
	}
	
	$('#submitbtn').click(function(event){
		if( !validateForm() )//validate form data
		{
			alert('Please check the Form!');
			return;
		}
		if( !confirm('<s:text name="global.hintinfo.submitconfirm"/>') )
			return;
		if($(this).attr("disabled"))
			return;
		$(this).parent().children("a.btn").attr("disabled", true);
		$('#appfrm_domain_status').val(2);
		$('#appfrm')[0].submit();
	});
	$('#backbtn').click(function(event){
		if($(this).attr("disabled"))
			return;
		$(this).parent().children("a.btn").attr("disabled", true);
		$('#appfrm')[0].action = '${ctx}/acegi/user!list.action';
		$('#appfrm').submit();
	});

});

</script>


  </div>
</div><!-- / end tab-content  -->
<%@ include file="/jsp/admin/include/foot.inc"%>