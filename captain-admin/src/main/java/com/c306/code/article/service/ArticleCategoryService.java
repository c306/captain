package com.c306.code.article.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.c306.code.article.dao.ArticleCategoryDao;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.hbm.TArticleCategory;

@Service
public class ArticleCategoryService extends HibernateEntityManager<TArticleCategory> {
	
	ArticleCategoryDao dao;

	public List<TArticleCategory> findParents(String oid) {
		return dao.findParents(oid);
	}

	
	public String findNodePath(String oid) {
		return this.dao.findNodePath(oid);
	}


	@Override
	protected IEntityDao<TArticleCategory> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
