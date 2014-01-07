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
  font-family: Courier, "Courier New";
}
div.error {
    /*width: 500px;*/
    border: 0px solid red;
    background-color: #fff;
    text-align: center;
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
   	       doLogin();
    	   }
    	}
    	document.onkeypress=enterLogin;
        function doLogin(){
        	var appfrm = document.getElementById('f');
        	if(appfrm.j_username.value == ''){
        		return;
        	}
        	if(appfrm.j_password.value == ''){
        		return;
        	}
 	        appfrm.submit();
        }
    </script>
  </head>
<body onload='f.j_username.focus();'>

<table width="100%" height="480" border="0" cellSpacing="0" cellPadding="0">
 
   
 <tbody>
  <tr>
   
       
   <td align="center" vAlign="middle">
    
        
    <table width="400" align="center" border="0" cellSpacing="0" cellPadding="0">
       
     <tbody>
      
        
      <tr>
       <td width="1" bgColor="#ffffff"></td>
       
           
       <td align="center">
        
        <table width="100%" border="0" cellSpacing="0" cellPadding="0">
         
         <form id="f" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
          
          <tbody>
           
           <tr>
            <td height="40" align="right">
             <span class="h_color"><s:text name="main.login.jsp.username"/>：</span>
            </td>
            <td height="40" align="left" style="width: 220px;">
            <input type="hidden" name="verbose" value="${param.verbose }"/>
            <input type="hidden" name="request_locale" value="en_US"/>
            <input tabindex="1" name="j_username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" class="input3" style="width: 220px; height: 22px; font-size: 12px;" type="text"/>
            </td>
            <td width="100">
              
            </td>
           </tr>
           
           <tr>
            <td height="40" align="right">
             <span class="h_color"><s:text name="main.login.jsp.password"/>：</span>
            </td>
            <td height="40" align="left">
             <input tabindex="3" name="j_password" class="input3" style="width: 220px; height: 22px; font-size: 12px;" type="password"/>
            </td>
            <td align="left" vAlign="bottom" >
             <span style="margin-left:20px; padding-bottom:5px;">
               <a href="javascript:void(0);" onclick="doLogin()" style="font-weight:bold;color:#000000;text-decoration:none;">Login</a>
             </span>
            </td>
           </tr>
           
           <s:if test="%{#session['SPRING_SECURITY_LAST_EXCEPTION'] != null}">
           <tr>
            <td height="55" align="center" colSpan="3">
              <div class="error">
                <s:text name="main.login.jsp.login.fail"/> ：
<%--                 <s:text name="main.login.jsp.username.password.error"/> --%>
                <s:property value="#session['SPRING_SECURITY_LAST_EXCEPTION'].getMessage()"/>
                <%
                if(request.getParameter("verbose")!=null) {
                    Exception ex = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                    ex.printStackTrace();
                }
                %>
              </div>
            </td>
           </tr>
           </s:if>
           
          </tbody>
         </form>
         <tbody>
          
         </tbody>
        </table>
            
       </td>
       <td width="1" bgColor="#ffffff"></td>
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