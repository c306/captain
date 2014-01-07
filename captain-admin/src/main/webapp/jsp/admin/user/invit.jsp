<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.invitmanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>


<div id="tab2" class="tab-content" style="display: block;">
	<div style="padding-left: 20px;padding-top: 20px;">
		<textarea class="large-input" style="width: 599px; height: 91px;" 
				  id="email"></textarea>
		<div style="height: 10px;">&nbsp;</div>
		<div class="Button002" onclick="invited();">
        	<s:text name="admin.label.userinfo.send"/>
    	</div>&nbsp;<font style="color: #F00">(<s:text name="admin.label.userinfo.email.semicolon" />)</font>
   	</div>
</div>
<style>
<!--
.large-input{
	width: 95% !important;
	font-size: 16px !important;
	padding: 8px !important;
	padding: 6px;
    font-size: 13px;
    background: #fff url('${ctx}/lookfor/css/simple/images/bg-form-field.gif') top left repeat-x;
    border: 1px solid #d5d5d5;
	color: #333;
}
-->
</style>
<script type="text/javascript">
<!--
$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
var em_reg = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/;
function invited() {
	if($("#email").val() != null && $("#email").val() != "") {
		if(confirm("<s:text name='admin.label.userinfo.send.tip'/>")) {
			var email = $("#email").val().split(";");
			var vaild = true;
			for(var i = 0 ; i < email.length ; i++) {
				if(!em_reg.test(email[i])) {
					alert("<s:text name='main.main.jsp.valid.email'/>");
					vaild = false;
					break;
				}
			}
			if(vaild) {
				$.ajax({
					type: "POST",
					url: "${ctx}/query/users!invited.action",
					data: "errMsg=" + $("#email").val(),
					async: false,
					dataType: 'text',
					success: function(responsetxt) {
						if(responsetxt == "success") {
							alert("<s:text name='admin.label.userinfo.send.success'/>");
						} else if (responsetxt == "failed") {
							alert("<s:text name='admin.label.userinfo.send.failed'/>");
						}
					},
					error: function() {
						alert("Network anomaly.");
					}
				});
			}
		}
	}
}
//-->
</script>
<%@ include file="/jsp/admin/include/foot.inc"%>