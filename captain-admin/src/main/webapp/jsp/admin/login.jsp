<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<%
Cookie cookies[]=request.getCookies(); 
Cookie sCookie=null; 
String svalue=""; 
String sname=""; 
if(cookies!=null)
for(int i=0;i<cookies.length;i++) 
{
	sCookie=cookies[i]; 
	if(sCookie.getName().equals("nanyoulogin")){
		svalue=sCookie.getValue(); 
		sname=sCookie.getName(); 
	}
	System.out.println("Cookie = " + sCookie.getName());
	System.out.println("Cookie value = " + sCookie.getValue());
}
//response.sendRedirect(request.getContextPath() + "/admin/index_simple.action");
%>

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>
<%--
<s:if test="#session['WW_TRANS_I18N_LOCALE'] == null || #session['WW_TRANS_I18N_LOCALE'].toString == ''">
	<c:set var="WW_TRANS_I18N_LOCALE" scope="session" value="${header['accept-language']}" />
	<s:set var="WW_TRANS_I18N_LOCALE" scope="session" value="%{#session['WW_TRANS_I18N_LOCALE'].substring(0, 5).replace('-', '_')}"/>
	<s:set var="WW_TRANS_I18N_LOCALE" scope="session" value="%{@com.opensymphony.xwork2.util.LocalizedTextUtil@localeFromString(#session['WW_TRANS_I18N_LOCALE'].toString(), null)}"/>
</s:if>
--%>

<LINK rel="shortcut icon" href="${ctx}/lookfor/images/logo.ico" type="image/x-icon" />

<s:if test="'zh_CN' == #session['WW_TRANS_I18N_LOCALE'].toString()">
	<script type="text/javascript" src="${ctx}/lookfor/common/My97DatePicker/cn_WdatePicker.js"></script>
</s:if><s:else>
	<script type="text/javascript" src="${ctx}/lookfor/common/My97DatePicker/en_WdatePicker.js"></script>
</s:else>

<script src="${ctx}/lookfor/common/jquery/jquery-1.4.2.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${ctx}/lookfor/js/common.js"></script>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title><s:text name="main.login.jsp.log.in"/></title>
    <script type="text/javascript" src="${ctx}/lookfor/common/js/cookies.js"></script>
    <style type="text/css">
div.error {
    width: 260px;
    border: 2px solid red;
    background-color: yellow;
    text-align: center;
}

div.hide {
    display: none;
}
    </style>
    <script type="text/javascript">
    function enterLogin(){
    	  var event = arguments[0]||window.event;
    	  var currentKey = event.charCode||event.keyCode;
    	  if(currentKey==13){
   	          if(f.j_password.value==''){
   	            //f.j_username.value='admin';
   	            //f.j_password.value='admin';
   	          }
   	          login();
    	   }
    	}
    	document.onkeypress=enterLogin;
    </script>
  </head>
<body onload='f.j_username.focus();'>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
	<tr>
		<td width="40%" align="center"><form id="f" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" onsubmit="login()">
			<table border="0" cellpadding="0" cellspacing="0" style="width: 645px;height: 439px;background-image: url('${ctx}/lookfor/images/bg.jpg');background-repeat: no-repeat;">
					<tr>
						<td width="30%">&nbsp;</td>
						<td width="40%" align="center" valign="middle" nowrap="nowrap"><fieldset>
        					<legend><s:text name="main.login.jsp.log.in"/></legend>
								<div class="error ${param.error == true ? '' : 'hide'}">
									<s:text name="main.login.jsp.login.fail"/><br>
									 ${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}
								</div>
								<input type="hidden" name="request_locale" value="<%=request.getHeader("accept-language").substring(0,5)%>"/>
								<table border="0" cellpadding="0" cellspacing="0">
									<tr height="30">
										<td align="right"><nobr><s:text name="main.login.jsp.username"/>：</nobr></td>
										<td align="left"><input type="text" name="j_username" maxlength="25"
									    		   style="width:180px;" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"/></td>
									</tr>
									<tr height="25">
										<td align="right"><s:text name="main.login.jsp.password"/>：</td>
										<td align="left"><input type="password" name="j_password" maxlength="25" style="width:180px;" /></td>
									</tr>
									<tr height="30">
										<td align="right"><input type="checkbox" name="_spring_security_remember_me" /></td>
										<td align="left" nowrap="nowrap"><s:text name="main.login.jsp.remember"/></td>
									</tr>
									<tr height="30">
										<td colspan="2" align="center">
											<input type="button" style="width: 50px;" onclick="login()" value="<s:text name="main.login.jsp.log.in"/>"/>
        									<input type="reset" style="width: 50px;" value="<s:text name="main.login.jsp.reset"/>"/>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
						<td width="30%">&nbsp;</td>
					</tr>	
				</table>
			</form>
		</td>
	</tr>
</table>
<script language="javascript">
  if(f.j_username.value=='' && Cookies.get('_spring_security_remember_me',false)){
    f.j_username.value=Cookies.get('j_username','');
    f.j_password.value=Cookies.get('j_password','');
    f._spring_security_remember_me.checked = Cookies.get('_spring_security_remember_me',false);
   }
   
   function login(){
   /*
	var myDate=new Date()
	myDate.setDate(myDate.getDate()+14)
  //记住密码
  if(f._spring_security_remember_me.checked){
   Cookies.set('j_username',f.j_username.value, myDate, null, null, false);
   Cookies.set('j_password',f.j_password.value, myDate, null, null, false);
   Cookies.set('_spring_security_remember_me',true);
  }else{//清除记住的密码
   Cookies.set('j_username','');
   Cookies.set('j_password','');
   Cookies.set('_spring_security_remember_me',false);
  }
  */
	f.submit();
  }
<!--
if (top.location != location) top.location.href = location.href;
//-->
</script>
</body>
</html>
