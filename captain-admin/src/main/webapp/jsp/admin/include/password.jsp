<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>


	<form action="${ctx}/admin/secuser!modifyPwd.action" method="post">
<!-- 	隐藏域 -->
	<s:token />
  <s:hidden name="domain.id" value="%{#session.__SecUser.id}"/>
  
    	<div class="managerCell001">
        	<div class="managerText001"><s:text name="admin.user.password.oldpassword"/>：</div>
            <div class="managerText002"><input type="password" id="domain_password" name="domain.password" value="" class="managerInput001"/></div>
        </div>
    	<div class="managerCell001">
        	<div class="managerText001"><s:text name="admin.user.password.newpassword"/>：</div>
            <div class="managerText002"><input type="password" id="domain_newpassword" name="domain.newpassword" value="" class="managerInput001"/></div>
        </div>
        <div class="managerCell001">
        	<div class="managerText001"><s:text name="admin.user.password.confirmnewpassword"/>：</div>
            <div class="managerText002"><input type="password" id="confirmpassword" name="aaaaaa" value="" class="managerInput001"/></div>
        </div>
        <div class="managerButton"><button type="button" name="subbtn" onclick="doConfirm(this)"><s:text name="global.button.label.submit"/></button></div>

</form>

<script type="text/javascript">
function doCancel(){
    location = '${ctx}/admin/sec!list.action';
}
function doConfirm(obj){
  if($("#domain_password").val() == ''){
    alert('<s:text name="admin.user.password.oldpassword.validate"/>');
    return;
  }
  if($("#domain_newpassword").val() == ''){
    alert('<s:text name="admin.user.password.newpassword.validate"/>');
    return;
  }
  if($("#domain_newpassword").val() == $("#confirmpassword").val()){
    obj.form.submit();
  } else {
    alert('<s:text name="admin.user.password.notmatch"/>');
  }
}
jQuery(function($) {
  $("#domain_password").focus();
  
});
</script>