package com.c306.comm.sysargs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.comm.sysargs.dao.SystemArgumentsDao;
import com.c306.comm.sysargs.entity.SystemArguments;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;

@Service
public class SystemArgumentsManager extends HibernateEntityManager<SystemArguments> {

	@Autowired
	private SystemArgumentsDao dao;
	
	
	/**
	 * 加载系统参数
	 */
	public void afterPropertySet() throws Exception {
		this.dao.afterPropertiesSet();
	}

	/**
	 * 根据key查询
	 * @param key
	 * @return
	 */
	public List<SystemArguments> retrieveList(String key){
		return this.dao.retrieveList(key);
	}
	
	/**
	 * query list
	 * @param record
	 * @return
	 */
	public List<SystemArguments> query(SystemArguments record) {
		return this.dao.query(record);
	}

	public void updateAppParam(SystemArguments domain) {
		this.dao.updateAppParam(domain);
	}
	
	/**
	 * keys 中key键不能相同，否则hibernate可能会报错
	 * @param keys key1=v1;key2=v2;
	 */
	public Map<String, String> updateAppParams(String keys) {
		return this.dao.updateAppParams(keys);
	}

	@Override
	protected IEntityDao<SystemArguments> getDao() {
		return this.dao;
	}
}
