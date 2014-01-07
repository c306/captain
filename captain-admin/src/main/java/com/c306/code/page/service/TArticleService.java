package com.c306.code.page.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.code.page.dao.TArticleDao;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.hbm.TArticle;

@Service
public class TArticleService extends HibernateEntityManager<TArticle> {
	
	@Autowired
	TArticleDao dao;

	/**
	 * query article by type
	 * 
	 * @param type
	 * @return
	 */
	public String queryByType(String type, String global) {
		return dao.queryByType(type, global);
	}
	
	/**
	 * query article by oid
	 * 
	 * @param oid
	 * @param global
	 * @return
	 */
	public TArticle queryByOid(String oid, String global) {
		return dao.queryByOid(oid, global);
	}
	
	/**
	 * query products by type
	 * 
	 * @param type
	 * @param global
	 * @return
	 */
	public List<TArticle> queryProducts(String type, String global) {
		return dao.queryProducts(type, global);
	}
	
	public TArticle queryPractice(String type, String global) {
		return dao.queryPractice(type, global);
	}
	
	public List<TArticle> queryPeople(String type, String global) {
		return dao.queryPeople(type, global);
	}
	
	public TArticle queryPeopleDetail(String oid, String global) {
		return dao.queryPeopleDetail(oid, global);
	}

	@Override
	protected IEntityDao<TArticle> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
