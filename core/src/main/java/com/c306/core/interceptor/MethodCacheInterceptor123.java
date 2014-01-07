package com.c306.core.interceptor;

import java.io.Serializable;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

@Deprecated
public class MethodCacheInterceptor123 implements MethodInterceptor,
		InitializingBean {

	private Ehcache cache;

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		//获取所拦截方法的返回值
		Object []arguments = invocation.getArguments();
		Object result;
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		
		//获取Cache中的Element
		Element element = cache.get(cacheKey);
		if(element == null) {
			System.out.println("++++++++++++++++++first+++++++++++++++++++");
			// call target/sub-interceptor
		    result = invocation.proceed();
		    // cache method result
		    element = new Element(cacheKey, (Serializable) result);
		    cache.put(element);
		}
		System.out.println("++++++++++++++++++cache+++++++++++++++++++");
	    
		return element.getValue();
	}

	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		Assert.notNull(cache, "Need a cache. Please use setCache(Cache) Create it.");
	}

}
