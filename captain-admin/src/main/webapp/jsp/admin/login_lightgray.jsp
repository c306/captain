<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/struts-tags"%>

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<s:if test="#session['WW_TRANS_I18N_LOCALE'] == null || #session['WW_TRANS_I18N_LOCALE'].toString == ''">
    <%--<c:set var="WW_TRANS_I18N_LOCALE" scope="session" value="${header['accept-language']}" />--%>
	<s:set var="WW_TRANS_I18N_LOCALE" scope="session" value="%{#session['WW_TRANS_I18N_LOCALE'].substring(0, 5).replace('-', '_')}"/>
	<s:set var="WW_TRANS_I18N_LOCALE" scope="session" value="%{@com.opensymphony.xwork2.util.LocalizedTextUtil@localeFromString(#session['WW_TRANS_I18N_LOCALE'].toString(), null)}"/>
</s:if>

<!DOCTYPE s:set PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title><s:text name="main.login.jsp.log.in"/></title>
    <style type="text/css">
body {
  font-size:14px;
  font-family: "幼圆",Courier, "Courier New";
}
div.error {
    width: 500px;
    border: 0px solid red;
    background-color: #fff;
    text-align: left;
    color: red;
}

div.hide {
    display: none;
}
    </style>
    
    <script src="${ctx}/lookfor/common/jquery/jquery-1.4.2.min.js" type="text/javascript" language="javascript"></script>
    
    <script type="text/javascript">
    function enterLogin(){
    	  var event = arguments[0]||window.event;
    	  var currentKey = event.charCode||event.keyCode;
    	  if(currentKey==13){
   	          if(f.j_password.value==''){
   	            //f.j_username.value='admin';
   	            //f.j_password.value='admin';
   	          }
    	   }
    	}
    	document.onkeypress=enterLogin;
    </script>
  </head>
<body onload='f.j_username.focus();'>

<table width="100%" height="480" border="0" cellSpacing="0" cellPadding="0">
 
   
 <tbody>
  <tr>
   
       
   <td align="center" vAlign="middle">
    
        
    <table width="90%" border="0" cellSpacing="5" cellPadding="0">
     
           
     <tbody>
      <tr>
       
               
       <td align="center">
<%--        <img width="408" height="60" alt="" src="images/logo2.png" longDesc="http://" />--%>
       </td>
      </tr>
      
          
     </tbody>
    </table>
    
        <br />
        
    <table width="663" align="center" border="0" cellSpacing="0" cellPadding="0">
       
     <tbody>
      <tr>
       <td height="10" background="${pageContext.request.contextPath}/images/head.gif" colSpan="3"></td>
      </tr>
      
        
      <tr>
       <td width="1" bgColor="#c0bcb9"></td>
       
           
       <td align="center">
        
        <table width="100%" border="0" cellSpacing="0" cellPadding="0">
         
         <form id="f" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
          
          <tbody>
           <tr>
            <td height="45" vAlign="bottom" colSpan="4" align="center">
             <p style="margin-left: 35px;">
<%--              <span class="h_color"><b>管理员登录: </b>您还没有登录或登录超时，请重新登录</span>--%>
             </p>
             <br/>
            </td>
           </tr>
           <tr>
            <td align="center" colSpan="4">
             <hr width="90%" SIZE="5" color="#c0c0c0" />
            </td>
           </tr>
           
           <tr>
            <td width="19%" height="40" align="right">
             <span class="h_color"><s:text name="main.login.jsp.username"/>：</span>
            </td>
            <td width="35%" height="40" align="left">
            <input type="hidden" name="request_locale" value="en_US"/>
            <input tabindex="1" name="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="input3" style="width: 220px; height: 22px; font-size: 12px;" type="text"/>
            </td>
            <td width="8%" rowSpan="2" valign="bottom">
             <input tabindex="4" name="login" type="image" alt="登录" src="${pageContext.request.contextPath}/images/button_login.gif" />
            </td>
            <td width="" height="40">
             &nbsp;
            </td>
           </tr>
           
           <tr>
            <td height="40" align="right">
             <span class="h_color"><s:text name="main.login.jsp.password"/>：</span>
            </td>
            <td height="40" align="left">
             <input tabindex="3" name="j_password" class="input3" style="width: 220px; height: 22px; font-size: 12px;" type="password"/>
            </td>
            <td height="40">
             &nbsp;
            </td>
           </tr>
           <%--
           <tr>
            <td height="40" align="right">
             <input tabindex="3" type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me"/>
            </td>
            <td height="40" align="left" colspan="3">
				<label for="_spring_security_remember_me"><s:text name="main.login.jsp.remember"/></label>
            </td>
           </tr>--%>
           
           <s:if test="%{#session['SPRING_SECURITY_LAST_EXCEPTION'] != null}">
           <tr>
            <td height="55" align="center" colSpan="4">
              <div class="error">
                <s:text name="main.login.jsp.login.fail"/> ：
                <s:text name="main.login.jsp.username.password.error"/>
<%--                <s:property value="#session['SPRING_SECURITY_LAST_EXCEPTION'].getMessage()"/>--%>
              </div>
            </td>
           </tr>
           </s:if>
           
           <tr>
            <td height="45" align="center" colSpan="4">
<div class="footer">
  <div style="font-size:9px; text-align: right; padding-right: 50px;">Copyright &copy; 2012 all rights reserved </div>
</div>
            </td>
           </tr>
          </tbody>
         </form>
         <tbody>
          
         </tbody>
        </table>
            
       </td>
       <td width="1" bgColor="#c0bcb9"></td>
      </tr>
      <tr>
       <td height="10" background="${pageContext.request.contextPath}/images/food.gif" colSpan="3"></td>
      </tr>
      
     </tbody>
    </table>
    <br />
   </td>
   
     
  </tr>
  
 </tbody>
</table>

</body>
</html>

<script language="javascript">
<!--
if (top.location != location) top.location.href = location.href;
//-->
</script>