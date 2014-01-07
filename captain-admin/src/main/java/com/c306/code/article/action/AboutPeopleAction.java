package com.c306.code.article.action;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.article.service.ArticleService;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.hbm.TArticle;

public class AboutPeopleAction  extends BaseGenericActionSupport<TArticle> {

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
		for(int i=0;i<domain.getAtts().size();i++){
			if(logger.isDebugEnabled())
				logger.debug("####" + ToStringBuilder.reflectionToString(domain.getAtts().get(i)));
			if(i==0){
				this.domain.setColorurl(domain.getAtts().get(i).getUri());
			}
		}
		return super.saveOrUpdate();
	}
	
	
	
}
