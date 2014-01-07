package com.c306.comm.sysargs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.c306.comm.sysargs.SysArgsUtil;
import com.c306.comm.sysargs.entity.SystemArguments;
import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

@Repository
public class SystemArgumentsDao extends HibernateEntityDao<SystemArguments> {
	
	/**
	 * 加载系统参数
	 */
	@SuppressWarnings("unchecked")
	public void afterPropertySet() throws Exception {
		
		Criteria criteria = super.createCriteria();
		criteria.add(Restrictions.like("key", "application.", MatchMode.START));// load application args
		List<SystemArguments> list = criteria.list();

		Map<String, String> args = new HashMap<String, String>();
		for (SystemArguments arg : list) {
			args.put(arg.getKey(), arg.getVal());
		}
		// init args
		SysArgsUtil.init(args);
	}

	/**
	 * 根据key查询
	 * @param key
	 * @return
	 */
	public List<SystemArguments> retrieveList(String key){
		if(GenericValidator.isBlankOrNull(key))
			return new ArrayList<SystemArguments>(0);
		return super.find("key", key);
	}

	@Override
	public Page pagedQuery(QueryParameters params) {
		
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");


		//key
		if(StringUtils.isNotBlank(params.get("key"))) {
			criteria.add(Restrictions.like("key", params.get("key"), MatchMode.START));
		}

		// createtime range
		if( params.getDateProperty("fromTime") != null ) {
			criteria.add(Restrictions.ge( "createtime", params.getDateProperty("fromTime") ));
		}
		if( params.getDateProperty("toTime") != null ) {
			criteria.add(Restrictions.le( "createtime", params.getDateProperty("toTime") ));
		}

		// status
		if ( params.getIntProperty("status") != null ) {
			criteria.add(Restrictions.eq("status", params.getIntProperty("status")));
		}
		
		criteria.addOrder(Order.asc("username"));
		
		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}
	
	/**
	 * query list
	 * @param record
	 * @return
	 */
	public List<SystemArguments> query(SystemArguments record) {
		Criteria criteria = this.getSession().createCriteria(super.getEntityClass());
		//key
		if(StringUtils.isNotBlank(record.getKey())) {
			criteria.add(Restrictions.eq("key", record.getKey()));
		}
		if(record.getStatus() != null) {
			criteria.add(Restrictions.eq("status", record.getStatus()));
		}
		return criteria.list();
	}

	public void updateAppParam(SystemArguments domain) {
		SystemArguments entity;
		List<SystemArguments> list = this.retrieveList(domain.getKey());
		if(list.size() == 0)
			entity = new SystemArguments();
		else
			entity = list.get(0);
		entity.setKey(domain.getKey());
		entity.setVal(domain.getVal());
		this.saveOrUpdate(entity);
	}
	
	/**
	 * keys 中key键不能相同，否则hibernate可能会报错
	 * @param keys key1=v1;key2=v2;
	 */
	public Map<String, String> updateAppParams(String keys) {
		logger.info("updateAppParams: " + keys);
		Map<String, String> args = new HashMap<String, String>();
		String[] params = keys.trim().split(";");
		for (String param : params) {
			String[] kv = param.split("=");
			if(kv.length < 2) continue;
			
			SystemArguments entity;
			List<SystemArguments> list = this.retrieveList(kv[0]);
			if(list.size() == 0)
				entity = new SystemArguments();
			else
				entity = list.get(0);
			
			entity.setKey(kv[0]);
			entity.setVal(kv[1]);
			this.saveOrUpdate(entity);
			//
			args.put(kv[0], kv[1]);
		}
		return args;
	}
}
