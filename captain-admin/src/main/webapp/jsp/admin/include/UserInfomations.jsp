<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

	<s:form action="/admin/secuser!modifyPwd.action">
<!-- 	隐藏域 -->
	<s:token />
  <s:hidden name="domain.id"/>
  
    	<div class="managerCell001">
        	<div class="managerText001"><s:text name="admin.user.password.oldpassword"/>：</div>
            <div class="managerText002"><input id="domain_password" name="domain.password" value="" class="managerInput001"/></div>
        </div>
    	<div class="managerCell001">
        	<div class="managerText001"><s:text name="admin.user.password.newpassword"/>：</div>
            <div class="managerText002"><input id="domain_newpassword" name="domain.newpassword" value="" class="managerInput001"/></div>
        </div>
        <div class="managerCell001">
        	<div class="managerText001"><s:text name="admin.user.password.confirmnewpassword"/>：</div>
            <div class="managerText002"><input id="confirmpassword" name="aaaaaa" value="" class="managerInput001"/></div>
        </div>
        <div class="managerButton" onclick="doConfirm(this)"><s:property value="%{getText('global.button.label.submit')}"/></div>

      <div class="managerCell001">
          <div class="managerText001">Full Name：</div>
            <div class="managerText002"><input class="managerInput001"/></div>
        </div>
        <div class="managerCell001">
          <div class="managerText001">City：</div>
            <div class="managerText002"><input class="managerInput001"/></div>
        </div>
        <div class="managerCell001">
          <div class="managerText001">E-mail：</div>
            <div class="managerText002"><input class="managerInput001"/></div>
        </div>
        <div class="managerCell001">
          <div class="managerText001">Mobile Tel：</div>
            <div class="managerText002"><input class="managerInput001"/></div>
        </div>
        <div class="managerCell001">
          <div class="managerText001">Upload Head Photo:</div>
        </div>
        <div class="headImg"></div>
        <div class="Choosefile">Choose file</div>
        <div class="managerButton" style="margin-top:43px" onclick="doConfirm(this)"><s:property value="%{getText('global.button.label.submit')}"/></div>
        
</s:form>

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
  $("#domain_first").focus();
  
});
</script>