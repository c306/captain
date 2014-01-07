<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" isErrorPage="true"%>
<%@ include file="/common/taglibs.inc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><s:text name="jsp.title"/></title>
	<link href="<%=path%>/css/lanrentuku.css" rel=stylesheet type=text/css />
	<link href="<%=path%>/css/baode.css" rel="stylesheet" type="text/css" />
	<script src="<%=path%>/js/DD_belatedPNG.js"></script>
	<script type="text/javascript">
		if (navigator.appName == "Microsoft Internet Explorer") {
			if (navigator.appVersion.indexOf("MSIE 6.0") > 0) {
				DD_belatedPNG.fix('div, img');
			}
		}
		function onLogout() {
			document.getElementById("idinfo").innerHTML = "";
			document.getElementById("loginFormHidden").src = "<%=path%>/query/LoginAction!Logout.action";
		}
	</script>
</head>
<body>
<jsp:include page="../jsp/query/top.jsp" />
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
    	<td width="1000" height="37">
    		<div style="width:100%; height:37px; background-image:url(<%=path%>/image/bg002.png); background-repeat:repeat-y">
	    		<div><img src="<%=path%>/image/line001.png" /></div>
	      		<div style="padding-top:10px; padding-left:33px">
		        	<div class="menuBox">
		        		<a href="<%=path%>/query/ShowPageAction!showFirstPage.action" class="menuText"><s:text name="main.main.jsp.home"/></a>
		        	</div>
		        	<div class="menuBox">
		        		<a href="<%=path%>/query/PageInfoAction!showInfo.action?type=1" class="menuText"><s:text name="main.main.jsp.vip.card"/></a>
		        	</div>
		        	<div class="menuBox menuSelect" <s:if test="'zh_CN' == #session['WW_TRANS_I18N_LOCALE'].toString()">style="padding-top: 2px;"</s:if>
		        		 style="background-image:url(<%=path%>/image/olorder_bg.png); background-repeat:no-repeat; width:155px; height:25px">
		        		<a href="<%=path%>/query/OrderOnlineAction!showOnlineProduct.action" class="menuText">
		        			<s:text name="main.main.jsp.order.online"/>
		        		</a>
		        	</div>
		        	<div class="menuBox">
		        		<a href="<%=path%>/query/PageInfoAction!showInfo.action?type=2" class="menuText"><s:text name="main.main.jsp.recipes"/></a>
		        	</div>
			        <div class="menuBox">
			        	<a href="<%=path%>/query/PageInfoAction!showFaqs.action" class="menuText"><s:text name="main.main.jsp.faqs"/></a>
			        </div>
			        <div class="menuBox">
			        	<a href="<%=path%>/query/PageInfoAction!showInfo.action?type=4" class="menuText"><s:text name="main.main.jsp.promotioms"/></a>
			        </div>
			        <div class="menuBox">
			        	<a href="<%=path%>/query/PageInfoAction!showInfo.action?type=3" class="menuText"><s:text name="main.main.jsp.about.us"/></a>
			        </div>
	      		</div>
	        	<div style="padding-top:27px"><img src="<%=path%>/image/line001.png" /></div>
    		</div>
    	</td>
  	</tr>
</table>
<table width="800" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:20px; margin-bottom:10px">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="lv2Content">
      <tr>
        <td width="77%" class="lv2Title"><s:text name="main.signup.jsp.forgot.pwd"/></td>
        <td width="23%" class="lv2Title" style="font-size:12px; font-weight:bold; color:#fff799">&nbsp;</td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="lv2Content">
        <tr style="height: 100%;">
    				<td class="lv2Text" valign="top">
    					<table width="690" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#4a3e0f">
						  <tr>
							  <td align="left" valign="top" class="lv2Text"><table width="400" height="300" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#4a3e0f">
				            	<tr>
				              		<td align="center" valign="middle" style="padding:10px">
				              			<div style="float:left"><img src="<%=path%>/image/alert.png" width="48" height="48" /></div>
				                		<div style="font-family:Verdana, Geneva, sans-serif; color:#FFF; font-size:12px; line-height:25px; float:left">
				                			<span style="font-family:Verdana, Geneva, sans-serif; color:#FFF; font-size:12px; line-height:25px;">
				                				对不起，该页面不存在。</span></div>
				                  	</td>
				            	</tr>
				            	<tr>
				              		<td style="padding:10px"></td>
				            	</tr>
				          </table>
				          </td>
				        </tr>
						</table>
    				</td>
    			</tr>
    </table></td>
  </tr>
</table>
<jsp:include page="../jsp/query/bottom.jsp" />
</body>
</html>