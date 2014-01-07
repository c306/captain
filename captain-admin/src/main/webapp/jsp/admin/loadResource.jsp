<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.core.userdetails.UserDetails"%>
<%@ page contentType="text/html;charset=gb2312"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.beans.factory.FactoryBean"%>
<%@page import="org.springframework.security.web.access.intercept.FilterSecurityInterceptor"%>
<%@page import="org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource"%>
<%@page import="com.c306.core.springsecurity.utils.JdbcFilterInvocationDefinitionSourceFactoryBean"%>
<%//@page import="org.springframework.security.intercept.web.FilterInvocationDefinitionSource"%>
<%@page import="com.c306.core.springsecurity.utils.SecurityMetadataSourceHelper"%>

<html>
<head>
<title>Welcome <sec:authentication property="name" />!</title>
</head>
<body>

<%
//清除页面  
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);

%>

<%
	/* Deprecated code. use SecurityMetadataSourceHelper instead of.
    ApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(application);
    //FactoryBean factoryBean = (FactoryBean) ctx.getBean("&filterInvocationDefinitionSource");
    //FilterInvocationDefinitionSource fids = (FilterInvocationDefinitionSource) factoryBean.getObject();
    //The property 'objectDefinitionSource' is deprecated，已过时
    //filter.setObjectDefinitionSource(fids);
    //FilterInvocationSecurityMetadataSource fids = (FilterInvocationSecurityMetadataSource) ctx.getBean("filterInvocationDefinitionSource");
    JdbcFilterInvocationDefinitionSourceFactoryBean fac = new JdbcFilterInvocationDefinitionSourceFactoryBean();
    fac.setDataSource((javax.sql.DataSource) ctx.getBean("dataSource"));
    // 必需与SpringSecurity-context.xml配置相同
    fac.setResourceQuery("select re.res_string, r.name from sec_role r join sec_resc_role rr on r.id=rr.role_id join sec_resc re on re.id=rr.resc_id order by re.priority");
    
    FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("filterSecurityInterceptor");
    filter.setSecurityMetadataSource(fac.getObject());
    */
    SecurityMetadataSourceHelper.reloadMetadataSource(application);
%>

OK Successful
<br/>
<%
if(SecurityContextHolder.getContext().getAuthentication() != null){
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	//如果想获得当前登陆用户所拥有的所有权限。
	if(principal != null && ! (principal instanceof String)){
		out.println("当前登陆用户所拥有的权限: <br/>");
		UserDetails userDetails = (UserDetails)principal; 
		java.util.Collection authorities = userDetails.getAuthorities();
	    Iterator iterator = authorities.iterator();
	    for(int i=0; i<authorities.size(); i++){
	    	out.println(((GrantedAuthority)iterator.next()).getAuthority());
	    	out.println("<br/>");
	    }
	    out.println("<p>------------------haha--------------------<p/>");
	} else {
		out.println("principal = " + String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()) + "<br/>");
	}
} else {
	out.println("没有登录");
}
%>

<br/>
&lt;http pattern="/common/**" security="none" /&gt; security="none"为none的地址，不能使用标签：
&lt;sec:authentication property="name" /&gt;<br/>
<sec:authentication property="name" /><br/>
<%-- <sec:authentication property="principal.username" /><br/> 如果未登录，会抛异常--%>
<sec:authentication property="authorities"/><br/>
<sec:authentication property="authorities" var="authorities"/><br/>
<s:iterator value="#attr.authorities" var="rolename" status="stat">
  rolename[<s:property value="#stat.count" />]:<s:property/><br/>
</s:iterator>
<br/>
<s:if test="#attr.authorities.contains(new org.springframework.security.core.authority.GrantedAuthorityImpl.GrantedAuthorityImpl('ROLE_ADMIN'))">
  if qqqqq Is ROLE_ADMIN--------------------------------<br/>
</s:if><s:else>
  else aaaaa NO ROLE_ADMIN--------------------------------<br/>
</s:else>

</body>
</html>
