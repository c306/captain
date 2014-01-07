<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.usermanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<s:form action="/admin/user!saveOrUpdate.action" enctype="multipart/form-data" method="post" id="appfrm">

    <s:hidden name="pageNo"/>
    <s:hidden name="pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.username"/>
    <s:hidden name="query.email"/>
    
<!-- 	隐藏域 -->
  <s:token />
  <s:hidden name="domain.id"/>
  <s:hidden name="domain.status"/>
  
  <div id="tab2" class="tab-content" style="display: block;">

      <fieldset>
        <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->

        <p>
          <label>
            <s:text name="admin.label.userinfo.fullname"/>:
          </label>
          <s:textfield name="domain.username" cssClass="text-input medium-input"/>
          <%--<span class="input-notification success png_bg">Successful message</span>--%>
          <s:if test="%{null != fieldErrors['domain.etitle']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.username'][0]}</span>
          </s:if>
          <%--<br><small><s:fielderror fieldName="domain.etitle" /></small>--%>
        </p>
        <p>
          <label>
            <s:text name="admin.label.userinfo.city"/>:
          </label>
          <s:textfield name="domain.city" cssClass="text-input medium-input"/>
          <s:if test="%{null != fieldErrors['domain.city']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.city'][0]}</span>
          </s:if>
        </p>
        <p>
          <label>
            <s:text name="admin.label.userinfo.email"/>:
          </label>
          <s:textfield name="domain.email" cssClass="text-input medium-input"/>
          <s:if test="%{null != fieldErrors['domain.email']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.email'][0]}</span>
          </s:if>
        </p>
        <p>
          <label>
            <s:text name="admin.label.userinfo.mobiletel"/>:
          </label>
          <s:textfield name="domain.mobile" cssClass="text-input medium-input"/>
          <s:if test="%{null != fieldErrors['domain.mobile']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.mobile'][0]}</span>
          </s:if>
        </p>
        <p>
          <label>
            <s:text name="admin.label.userinfo.designer"/>:
          </label>
          <s:action var="designerList" name="designers!retrieveList" namespace="/admin">
          </s:action>
          <s:if test="iscn">
            <s:select name="domain.designerid" emptyOption="true" listKey="oid" listValue="name" list="#designerList.items" cssClass="text-input"></s:select>
          </s:if><s:else>
            <s:select name="domain.designerid" emptyOption="true" listKey="oid" listValue="ename" list="#designerList.items" cssClass="text-input"></s:select>
          </s:else>
        </p>
        
        <!--
        <p>
          <label>
            <s:text name="admin.label.userinfo.headphoto"/>:
            <br><small style="color:red;"> size ( 62 * 62 px)</small>
          </label>
			<s:hidden name="domain.avatar"/>
			<div class="headImg"></div>
			<div style="width: 64px; height: 64px;left: 50px; top: 30px; padding: 10px;" class="ui-widget ui-widget-content ui-corner-all">
				<div class="ui-dialog-content ui-widget-content" style="background: none; border: 0;">
					<img src="${ctx}/${empty domain.avatar ? 'images/common/emptypic.jpg' : domain.avatar}" width="62" height="62" border=0/>
				</div>
			</div>
    		<input type="file" id="headphoto" name="upload"/>
        </p>
        -->
        
        <p/>
        <p/>
        
        <div class="managerCell002">
          <sec:authorize ifAnyGranted="ROLE_ADMIN">
          <span class="Button002" id="savebtn">
            <s:text name="global.button.label.save"/>
          </span>
          </sec:authorize>
          <span class="Button002" id="backbtn">
            <s:text name="admin.article.back"/>
          </span>
        </div>

      </fieldset>

      <div class="clear"></div>
      <!-- End .clear -->

  </div>

</s:form>

<script type="text/javascript">
function doConfirm(obj){
  if($("#appfrm_domain_username").val() == ''){
    $("#appfrm_domain_username").show("highlight").show("highlight").show("highlight").focus();
    return;
  }
  $("#tab2 .Button002").button("disable");
  $('#appfrm').submit();
}


function LimitAttach(file) {
	var extArray = new Array(".gif", ".jpg");
	var allowSubmit = false;
	if (!file) return true;

	while (file.indexOf("\\") != -1)
		file = file.slice(file.indexOf("\\") + 1);

	var ext = file.slice(file.indexOf(".")).toLowerCase();

	for (var i = 0; i < extArray.length; i++) {
		if (extArray[i] == ext) { allowSubmit = true; break; }
	}
	if (!allowSubmit){
		alert("对不起，只能上传以下格式的文件:  " 
				+ (extArray.join("  ")) + "\n请重新选择符合条件的文件再上传.");
	}
	return allowSubmit;
}

jQuery(function($) {
	$("#domain_first").focus();
	
	$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
  	$('#savebtn').click(function(event){
		doConfirm(appfrm);
	});
	$('#backbtn').click(function(event){
		$("#tab2 .Button002").button("disable");
		$('#appfrm')[0].action = '${ctx}/admin/user!list.action';
		$('#appfrm').submit();
	});
});
</script>


<%@ include file="/jsp/admin/include/foot.inc"%>