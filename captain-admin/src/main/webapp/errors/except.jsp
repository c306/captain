<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" isErrorPage="true" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ page import="org.apache.commons.logging.LogFactory" %>

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<html>
<head>


<link rel="icon" type="image/ico" href="${ctx}/favicon.ico" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Struts2 Exception Page</title>

<script src="${ctx}/lookfor/common/jquery/jquery-1.9.1.min.js" type="text/javascript" language="javascript"></script>

<!-- bootstrap styles -->
<link href="${ctx}/lookfor/common/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/lookfor/common/bootstrap/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="${ctx}/lookfor/common/bootstrap/js/bootstrap.js"></script>


</head>

<body bgcolor="#f5f5f5" text="black">




<div class="span10">
  <div class="alert alert-error">
	<a class="close" data-dismiss="alert" href="#">×</a>
	<h4 class="alert-heading">Error: <s:property value="%{exception.message}"/></h4>
	<s:property value="%{exceptionStack}"/>
  </div>
</div>






</body>
</html>