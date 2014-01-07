package com.c306.core.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

@Deprecated
public class MethodCacheAfterAdvice123 implements AfterReturningAdvice,
		InitializingBean {

	private Ehcache cache;

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	@SuppressWarnings("unchecked")
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		// TODO Auto-generated method stub
		String className = arg3.getClass().getName();
		List<String> list = cache.getKeys();
		for (String cacheKey : list) {
			if (cacheKey.startsWith(className)) {
				cache.remove(cacheKey);
				System.out.println("remove cache" + cacheKey);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache,
				"Need a cache. Please use setCache(Cache) Create it.");
	}

}
