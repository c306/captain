package com.c306.code.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.code.article.dao.ArticleDao;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.hbm.TArticle;

@Service
public class ArticleService extends HibernateEntityManager<TArticle> {
	
	@Autowired
	private ArticleDao dao;


	
	@Override
	protected IEntityDao<TArticle> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
