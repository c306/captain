<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.user.password.modify')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>


<s:form action="/mi/member!modifyPwd.action" id="appfrm" method="post">
<!-- 	隐藏域 -->
	<s:token />
  <s:hidden name="domain.id" value="%{#session.__SecUser.id}"/>


  <div id="tab2" class="tab-content" style="display: block;">
<%--<div class="managerContentTitle"><s:text name="admin.user.password.modify"/></div>--%>
      <fieldset>

        <p>
          <label>
            <s:text name="admin.user.password.oldpassword"/>:
          </label>
          <s:password name="domain.password" id="domain_password" cssClass="text-input medium-input"/>
        </p>
        <p>
          <label>
            <s:text name="admin.user.password.newpassword"/>:
          </label>
          <s:password name="domain.newpassword" id="domain_newpassword" cssClass="text-input medium-input"/>
        </p>
        <p>
          <label>
            <s:text name="admin.user.password.confirmnewpassword"/>:
          </label>
          <s:password id="confirmpassword" cssClass="text-input medium-input"/>
        </p>
        
        <p/>
        <p/>
        
        <div class="managerCell002">
          <div class="Button002" id="savetbtn">
            Save 
          </div>
        </div>

      </fieldset>

      <div class="clear"></div>
      <!-- End .clear -->

  </div>

</s:form>


<script type="text/javascript">
function doCancel(){
		location = '${ctx}/mi/member/sec!list.action';
}
function doConfirm(obj){
	if($("#domain_password").val() == ''){
		alert('<s:text name="admin.user.password.oldpassword.validate"/>');
    	$("#domain_password").show("highlight").show("highlight").show("highlight").focus();
		return;
	}
	if($("#domain_newpassword").val() == ''){
		alert('<s:text name="admin.user.password.newpassword.validate"/>');
    	$("#domain_newpassword").show("highlight").show("highlight").show("highlight").focus();
		return;
	}
	if($("#domain_newpassword").val() == $("#confirmpassword").val()){
		obj.submit();
	} else {
		alert('<s:text name="admin.user.password.notmatch"/>');
    	$("#domain_newpassword").show("highlight").show("highlight").show("highlight").focus();
	}
}
jQuery(function($) {
	$("#domain_first").focus();
	
	$("#tab2 .Button002").css({'color':'#000000', 'font-weight':'bold', 'font-size':'12px', 'cursor':'pointer'});
	//$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
  	$('#savetbtn').click(function(event){
		doConfirm(appfrm);
	});
	
});
</script>



<%@ include file="/jsp/admin/include/foot.inc"%>