package com.c306.code.article.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.article.service.ArticleCategoryService;
import com.c306.code.article.service.ArticleService;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.hbm.TArticle;
import com.c306.hbm.TArticleCategory;


public class ArticleAction  extends BaseGenericActionSupport<TArticle> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ArticleService service;
	@Autowired
	private ArticleCategoryService categoryService;
	
	@Override
	protected ArticleService getService() {
		return this.service;
	}

	@Override
	public String saveOrUpdate() throws Exception {
		return super.saveOrUpdate();
	}

	@Override
	public String edit() throws Exception {
		return super.edit();
	}

	@Override
	public String list() throws Exception {
//		Collection<? extends GrantedAuthority> autths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//		logger.info(autths.toString());
//		//admin用户可查询全部
//		if(!(autths.contains(new GrantedAuthorityImpl("ROLE_ADMIN"))
//				|| autths.contains(new GrantedAuthorityImpl("ROLE_DESIGNER"))
//				|| autths.contains(new GrantedAuthorityImpl("ROLE_SEO"))
//				 )){
//			this.query.setAuthor(((SecUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
//		}
		
		//如果查询 NEW IN， 一周以内发布
//		if("1".equals(super.getParameterValue("query_newin"))){
//			if(this.query.getPublishtimefrom() == null){
//				this.query.setPublishtimefrom(DateUtils.addWeeks(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH), -1));
//			}
//			if(this.query.getPublishtimeto() == null){
//				this.query.setPublishtimeto(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
//			}
//		}
		
		return super.list();
	}
	
	public List<TArticleCategory> getCategories(){
		return categoryService.findAll();
	}
	
}
