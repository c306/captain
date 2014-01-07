package com.c306.core.springsecurity.utils;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SecurityMetadataSourceHelper {

	public static void reloadMetadataSource(ServletContext  sc) {
		
	    ApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(sc);
	    //FactoryBean factoryBean = (FactoryBean) ctx.getBean("&filterInvocationDefinitionSource");
	    //FilterInvocationDefinitionSource fids = (FilterInvocationDefinitionSource) factoryBean.getObject();
	    //The property 'objectDefinitionSource' is deprecated，已过时
	    //filter.setObjectDefinitionSource(fids);
	    //FilterInvocationSecurityMetadataSource fids = (FilterInvocationSecurityMetadataSource) ctx.getBean("filterInvocationDefinitionSource");
	    
	    // recreate factory bean
//	    JdbcFilterInvocationDefinitionSourceFactoryBean fac = new JdbcFilterInvocationDefinitionSourceFactoryBean();
//	    fac.setDataSource((javax.sql.DataSource) ctx.getBean("dataSource"));
//	    // 必需与SpringSecurity-context.xml配置相同
//	    fac.setResourceQuery("select re.res_string, r.name from sec_role r join sec_resc_role rr on r.id=rr.role_id join sec_resc re on re.id=rr.resc_id order by re.priority");
	    
	    // directly get factory bean from ctx
	    JdbcFilterInvocationDefinitionSourceFactoryBean fac = ctx.getBean("&securityMetadataSource", JdbcFilterInvocationDefinitionSourceFactoryBean.class);
	    
	    // create SecurityMetadataSource
	    FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("filterSecurityInterceptor");
	    filter.setSecurityMetadataSource(fac.getObject());

	}

}
