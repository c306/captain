<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" isErrorPage="true"%>
<%@ include file="/common/taglibs.inc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>您没有权限访问该资源!</title>
</head>
<link href="${ctx}/styles/kms/style.css" rel="stylesheet" type="text/css">
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
      <tr>
        <td height="1"></td>
      </tr>
      <tr>
        <td align="center" valign="top">
<div style="height:100%;overflow: auto;">
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
                 <td height="26">Sorry,您没有权限访问该资源!</td>
               </tr>
               <tr>
                 <td height="26"></td>
               </tr>
               <!--tr>
                 <td height="26"><a style="cursor:hand" onMouseDown=idc(error);idc2(error2) class="error">了解详情请点击</a></td>
               </tr-->
               <tr>
                 <td height="26"><div id="error" style="height:60; overflow:auto; display:none">&nbsp;</div></td>
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
<html>