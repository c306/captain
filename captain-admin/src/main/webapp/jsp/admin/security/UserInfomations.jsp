<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.personalinformation')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<s:form action="/mi/member!saveUserinfo.action" enctype="multipart/form-data" method="post" id="appfrm">
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
          <s:textfield name="domain.username" cssClass="text-input small-input"/>
          <%--<span class="input-notification success png_bg">Successful message</span>--%>
          <s:if test="%{null != fieldErrors['domain.etitle']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.username'][0]}</span>
          </s:if>
          <%--<br><small><s:fielderror fieldName="domain.etitle" /></small>--%>
        </p>
        <p>
          <label>
            <s:text name="main.user.edit.jsp.nicename"/>:
          </label>
          <s:textfield name="domain.nicename" cssClass="text-input small-input"/>
          <s:if test="%{null != fieldErrors['domain.nicename']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.nicename'][0]}</span>
          </s:if>
        </p>
        <p>
          <label>
            <s:text name="admin.label.userinfo.city"/>:
          </label>
          <s:textfield name="domain.city" cssClass="text-input small-input"/>
          <s:if test="%{null != fieldErrors['domain.city']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.city'][0]}</span>
          </s:if>
        </p>
        <p>
          <label>
            <s:text name="admin.label.userinfo.email"/>:
          </label>
          <s:textfield name="domain.email" cssClass="text-input small-input"/>
          <s:if test="%{null != fieldErrors['domain.email']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.email'][0]}</span>
          </s:if>
        </p>
        <p>
          <label>
            <s:text name="admin.label.userinfo.mobiletel"/>:
          </label>
          <s:textfield name="domain.mobile" cssClass="text-input small-input"/>
          <s:if test="%{null != fieldErrors['domain.mobile']}">
          <span class="input-notification error png_bg">${fieldErrors['domain.mobile'][0]}</span>
          </s:if>
        </p>
        
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
        
        <p/>
        <p/>
        <p/>
        
        <div class="managerCell002">
          <a class="Button002" id="savetbtn">
            Save
          </a>
        </div>

      </fieldset>

      <div class="clear"></div>
      <!-- End .clear -->

  </div>

</s:form>

<script type="text/javascript">
function doCancel(){
    location = '${ctx}/admin/sec!list.action';
}
function doConfirm(obj){
  if($("#appfrm_domain_username").val() == ''){
    $("#appfrm_domain_username").show("highlight").show("highlight").show("highlight").focus();
    return;
  }
  
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
	
	$("#tab2 .Button002").css({'color':'#000000', 'font-weight':'bold', 'font-size':'12px', 'cursor':'pointer'});
  	$('#savetbtn').click(function(event){
		doConfirm(appfrm);
	});
});
</script>


<%@ include file="/jsp/admin/include/foot.inc"%>