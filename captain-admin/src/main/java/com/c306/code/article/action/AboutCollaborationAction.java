package com.c306.code.article.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.article.service.ArticleService;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.hbm.TArticle;

public class AboutCollaborationAction  extends BaseGenericActionSupport<TArticle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ArticleService service;
	
	@Override
	protected ArticleService getService() {
		return this.service;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		return super.saveOrUpdate();
	}
	
	
	
}
