package com.c306.code.page.service;

import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.code.page.dao.TNavigationDao;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.hbm.TNavigation;

@Service
public class TNavigationService extends HibernateEntityManager<TNavigation> {

	@Autowired TNavigationDao dao;
	/**
	 * bar info query
	 * 
	 * @param global
	 * @return
	 * @throws JSONException 
	 */
	public String queryBarInfo(String global) throws JSONException {
		return dao.queryBarInfo(global);
	}

	@Override
	protected IEntityDao<TNavigation> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
