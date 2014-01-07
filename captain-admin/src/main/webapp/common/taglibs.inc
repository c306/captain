<%@ page language="java" isELIgnored="false"%>
<%//@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%//@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%//@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>

<%--<c:set var="ctx" value="${pageContext.request.contextPath}" />--%>

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<script type="text/javascript">
<!--
ctx = '${ctx}';
//-->
</script>
