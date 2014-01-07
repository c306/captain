<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('admin.navi.headline.title.quartz.jobdetial')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<script type="text/javascript" src="${ctx}/lookfor/common/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}/lookfor/common/ckfinder/ckfinder.js"></script>


<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">

<s:form cssClass="form-horizontal" id="appfrm" action="/quartz/scheduler!saveJob.action" method="post" >
<%-- enctype="multipart/form-data" --%>
<!-- 查询条件 -->
  <input type="hidden" name="treeid" value="${param.treeid}"/>
    <s:hidden name="pageNo"/>
    <s:hidden name="pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.status"/>
<!-- 	隐藏域 -->
	<s:token />
	<s:hidden name="domain.createtime"/>
	<input type="hidden" name="domain.author" value="${sessionScope.__SecUser.id}"/>
	<input type="hidden" name="domain.authorname" value="${sessionScope.__SecUser.username}"/>
  
    <s:hidden name="jobInfo.update" value="%{#request.isUpdateJobDetail}"/>
  
  <fieldset>
  <div class="control-group">
    <label class="control-label" for="jobInfo_name"><s:text name="admin.label.quartz.job.name"/>:</label>
    <div class="controls">
      <s:if test="#request.isUpdateJobDetail">
        <span class="input-xlarge uneditable-input">${jobDetail.name}</span>
        <input type="hidden" id="jobInfo_name" name="jobInfo.name" value="${jobDetail.name}" class="input-xlarge">
      </s:if>
      <s:else>
        <input type="text" id="jobInfo_name" name="jobInfo.name" value="${jobDetail.name}" class="input-xlarge">
      </s:else>
      <span class="help-inline asterisk_red">*</span>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="jobInfo_group"><s:text name="admin.label.quartz.job.group"/>:</label>
    <div class="controls">
      <s:if test="#request.isUpdateJobDetail">
        <span class="input-xlarge uneditable-input">${jobDetail.group}</span>
        <input type="hidden" id="jobInfo_group" name="jobInfo.group" value="${jobDetail.group}" class="input-xlarge">
      </s:if>
      <s:else>
        <input type="text" id="jobInfo_group" name="jobInfo.group" value="${jobDetail.group}" class="input-xlarge">
      </s:else>
      <span class="help-inline asterisk_red">*</span>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label" for="jobInfo_description"><s:text name="admin.label.quartz.job.description"/>:</label>
    <div class="controls">
      <textarea class="input-xlarge" id="jobInfo_description" name="jobInfo.description" rows="3">${jobDetail.description}</textarea>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label" for="jobInfo_jobClassName"><s:text name="admin.label.quartz.job.jobclassname"/>:</label>
    <div class="controls">
      <input type="text" id="jobInfo_jobClassName" name="jobInfo.jobClassName" value="${jobDetail.jobClass.name}" class="input-xlarge">
      <span class="help-inline asterisk_red">*</span>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label" for=""> </label>
    <div class="controls">
      <label class="checkbox inline">
        <input type="checkbox" id="jobInfo_requestsRecovery" name="jobInfo.requestsRecovery" value="true" class="input-small"
        <s:property value='%{ #request.jobDetail.requestsRecovery() ? "checked" : "" }' />
        ><s:text name="admin.label.quartz.job.recoverable"/>
      </label>
<%--      <p class="help-inline">Place input a natural number.</p>--%>
    </div>
  </div>

  <div class="control-group">
    <label class="control-label" for="jobInfo_paramDatas"><s:text name="admin.label.quartz.job.jobdatamap"/>:</label>
    <div class="controls">
      <textarea class="input-xlarge" id="jobInfo_paramDatas" name="jobInfo.paramDatas" rows="3">${request.paramDatas}</textarea>
    </div>
  </div>
  
  <div class="control-group">
    <div class="controls">
      <a id="submitbtn" class="btn" href="javascript:void(0);"><s:text name="global.button.label.save"/></a>
      <a id="backbtn" class="btn" href="javascript:void(0);"><s:text name="admin.article.back"/></a>
    </div>
  </div>

  </fieldset>
</s:form>

<p></p>
<p></p>



<script type="text/javascript">
$(document).ready(function(){
	
	$('#jobInfo_name, #jobInfo_group, #jobInfo_jobClassName').blur(function(event){
		
		if($(this).val().trim() == '') {
			$(this).parents('div.control-group').addClass('error');
		} else {
			$(this).parents('div.control-group').removeClass('error');
		}
		
	});
	
	function validateForm() {
		var pass = true;
		$('#jobInfo_name, #jobInfo_group, #jobInfo_jobClassName').each(function(idx, el){
			if($(this).val().trim() == '') {
				pass = false;
				$(this).parents('div.control-group').addClass('error');
			}
		});
		return pass;
	}
	
	$('#submitbtn').click(function(event){
		if( !confirm('<s:text name="global.hintinfo.submitconfirm"/>') )
			return;
		if($(this).attr("disabled"))
			return;
		if( !validateForm() )//validate form data
		{
			alert('Please check the Form!');
			return;
		}
		$(this).parent().children("a.btn").attr("disabled", true);
		$('#appfrm_domain_status').val(2);
		$('#appfrm')[0].submit();
	});
	$('#backbtn').click(function(event){
		if($(this).attr("disabled"))
			return;
		$(this).parent().children("a.btn").attr("disabled", true);
		$('#appfrm')[0].action = '${ctx}/quartz/scheduler!jobList.action';
		$('#appfrm').submit();
	});

});

</script>


  </div>
</div><!-- / end tab-content  -->
<%@ include file="/jsp/admin/include/foot.inc"%>