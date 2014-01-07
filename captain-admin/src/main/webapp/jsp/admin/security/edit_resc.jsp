<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('resc.edit')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">
  
<%@ include file="/jsp/admin/include/alert.inc"%>

<s:form cssClass="form-horizontal" id="appfrm" action="/acegi/resc!saveOrUpdate.action" method="post" >
<%-- enctype="multipart/form-data" --%>
<!-- 查询条件 -->
  <input type="hidden" name="treeid" value="${param.treeid}"/>
    <s:hidden name="query.pageNo"/>
    <s:hidden name="query.pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.resType"/>
    <s:hidden name="query.resString"/>
<!-- 	隐藏域 -->
	<s:token />
	<s:hidden name="domain.createtime"/>
	<input type="hidden" name="domain.author" value="${sessionScope.__SecUser.id}"/>
	<input type="hidden" name="domain.authorname" value="${sessionScope.__SecUser.username}"/>
  
  <fieldset>
  <%-- <div class="control-group">
    <label class="control-label" for=""><s:text name="resc.id"/>:</label>
    <div class="controls">
      <span class="input-xlarge uneditable-input">${domain.id}</span>
      <input type="hidden" name="domain.id" value="${domain.id}">
    </div>
  </div> --%>
  <input type="hidden" name="domain.id" value="${domain.id}">

  <div class="control-group">
    <label class="control-label" for=""><s:text name="resc.name"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.name" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for=""><s:text name="resc.restype"/>:</label>
    <div class="controls">
      <s:select list="#{'URL':'URL'}" name="domain.resType"></s:select>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for=""><s:text name="resc.resstring"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.resString" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for=""><s:text name="resc.priority"/>:</label>
    <div class="controls">
      <s:textfield cssClass="input-large" name="domain.priority" />
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for=""><s:text name="resc.descn"/>:</label>
    <div class="controls">
      <s:textarea cssClass="input-xlarge" name="domain.descn" rows="3"/>
    </div>
  </div>

  <!-- button group -->
  <div class="control-group">
    <div class="controls">
      <a id="submitbtn" class="btn" href="javascript:void(0);"><s:text name="btn.save"/></a>
      <a id="backbtn" class="btn" href="javascript:void(0);"><s:text name="btn.back"/></a>
    </div>
  </div>

  </fieldset>
</s:form>

<p></p>
<p></p>



<script type="text/javascript">
$(document).ready(function(){
	
	$('#appfrm_domain_resString').blur(function(event){
		
		if($(this).val().trim() == '') {
			$(this).parents('div.control-group').addClass('error');
		} else {
			$(this).parents('div.control-group').removeClass('error');
		}
		
	});
	
	function validateForm() {
		var pass = true;
		$('#appfrm_domain_resString').each(function(idx, el){
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
		$('#appfrm')[0].action = '${ctx}/acegi/resc!list.action';
		$('#appfrm').submit();
	});

});

</script>


  </div>
</div><!-- / end tab-content  -->
<%@ include file="/jsp/admin/include/foot.inc"%>