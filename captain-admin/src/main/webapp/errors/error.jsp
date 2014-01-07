<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%@ include file="/common/taglibs.inc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ page import="org.apache.commons.logging.LogFactory" %>


<html>
<head>
    <title>E</title>
    <script src="<c:url value="/common/prototype/prototype.js"/>" type="text/javascript"></script>
</head>
<link href="${ctx}/styles/kms/style.css" rel="stylesheet" type="text/css">
<body>
    <%
        //exception=null;
        if (exception != null) //from JSP
        {
            //Exception from JSP didn't log yet ,should log it here.
            String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
            LogFactory.getLog(requestUri).error(exception.getMessage(), exception);
        } else if (request.getAttribute("exception") != null) //from Spring
        {
            exception = (Exception) request.getAttribute("exception");
                          
        }else if (request.getAttribute("javax.servlet.error.exception") != null) //from servlet
        {
            exception = (Exception) request.getAttribute("javax.servlet.error.exception");                        
        }
    %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
     <td bgcolor="#ffffff" height="6"></td>
  </tr>
  <tr>
    <td bgcolor="#ffffff">
    <div id="titel1">
  <div id="titel1_left">
     <div id="titel1_right">
	 <div id="titel1_txt">提示信息</div>
	 </div>
  </div>
</div></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td height="1"></td>
      </tr>
      <tr>
        <td align="center" valign="top">
<div style="height:426px;overflow: auto;">
 <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#dfedef;">

   <tr>
     <td align="center" >&nbsp;</td>
     </tr>
   <tr>
     <td height="386" align="center"><table width="70%" border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td style="background:url(${ctx}/lookfor/images/common/error_03.jpg) repeat-x; height:17px;">&nbsp;</td>
       </tr>
       <tr>
         <td style="background:url(${ctx}/lookfor/images/common/error_05.jpg) repeat"><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
           <tr>
             <td width="31%" height="180" align="left"><img src="${ctx}/lookfor/images/common/error.gif" width="120" height="122" /></td>
             <td width="69%"><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr>
                 <td height="26" style="color:#D80000; font-size:15px; font-weight:bold;">提示信息</td>
               </tr>
               <tr>
                 <td height="20" background="${ctx}/lookfor/images/common/errorline.gif">&nbsp;</td>
               </tr>
               <tr>
                 <td height="26"><%if (exception!=null){%><%=exception.getMessage()%><%}%></td>
               </tr>
               <tr>
                 <td height="26">    <div id="detail_error_msg" style="display:none">
        <pre><%if (exception!=null) {exception.printStackTrace(new java.io.PrintWriter(out));}%></pre>
    </div></td>
               </tr>
              
             </table></td>
           </tr>
           
         </table></td>
       </tr>
       <tr>
         <td style="background:url(${ctx}/lookfor/images/common/error_06.jpg) repeat-x; height:55px;">&nbsp;</td>
       </tr>
     </table></td>
   </tr>
   <tr>
     <td align="center">&nbsp;</td>
   </tr>
 </table>
</div></td>
</tr>
</table></td>
</tr>
</table>

</body>
</html>