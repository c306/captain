<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<head>
<title>404 NOT FOUND</title>
</head>

<body bgcolor="#f5f5f5" text="black">
<div style="font-size: 400px; text-align: center;">404</div>
<div style="font-size: 128px; text-align: center; font-variant: small-caps;">Page Not Found</div>
<div style="text-align: center;">The document you requested could not be found. Please check the URL and try again. This is a recording.</div>



<div style="display: none;">
<%@ include file="/common/messages.inc"%>
<s:iterator var="ferr" value="fieldErrors">
      <div style="padding: 0pt 0.7em;" class="ui-state-error ui-corner-all"> 
        <p><span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span> 
        <strong>Alert:</strong> <s:property escapeHtml="false"/></p>
      </div>
</s:iterator>
</div>




</body>
</html>