package com.c306.core.springsecurity.resources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.core.springsecurity.resources.dao.SecRescDao;
import com.c306.core.springsecurity.resources.entity.SecResc;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

@Service
public class SecRescService extends HibernateEntityManager<SecResc> {
	
	@Autowired
	private SecRescDao dao;

	@Override
	protected IEntityDao<SecResc> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public Page pagedQueryByRoleId(QueryParameters params) {
		return this.dao.pagedQueryByRoleId(params);
	}


}
