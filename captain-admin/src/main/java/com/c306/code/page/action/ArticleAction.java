package com.c306.code.page.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.page.service.TArticleService;
import com.c306.code.page.utils.ActionUtils;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.hbm.TArticle;

public class ArticleAction extends BaseGenericActionSupport<TArticle> {
	
	private static final long serialVersionUID = 9142948182030113144L;
	@Autowired
	private TArticleService service;

	@Override
	protected TArticleService getService() {
		return this.service;
	}
	
	private String dataItems;
	public String getDataItems() {
		return dataItems;
	}
	public void setDataItems(String dataItems) {
		this.dataItems = dataItems;
	}
	
	public String queryByType() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		dataItems = this.service.queryByType(this.keys, global);
		
		//set charset
		super.getHttpServletResponse().setCharacterEncoding("UTF-8");
		super.getHttpServletResponse().getWriter().write(dataItems);
		return null;
	}
	
	public String queryByOid() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		this.domain = this.service.queryByOid(this.keys, global);
		
		return "architecture.jsp";
	}
	
	public String queryProducts() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		this.items = this.service.queryProducts(this.keys, global);
		
		return "products.jsp";
	}
	
	public String queryPractice() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		this.domain = this.service.queryPractice(this.keys, global);
		
		return "practice.jsp";
	}
	
	public String queryPeople() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		//this.items = this.service.queryPeople(this.keys, global);
		this.domain = this.service.queryPeopleDetail(this.keys, global);
		
		return "people.jsp";
	}
	
	public String queryPdetail() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		this.domain = this.service.queryPeopleDetail(this.keys, global);
		
		return "people_detail.jsp";
	}
	
	public String queryColltion() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		this.domain = this.service.queryPeopleDetail(this.keys, global);
		
		return "practice.jsp";
	}
	
	/**
	 * query product detail
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryDetail() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		this.domain = this.service.queryByOid(this.keys, global);
		
		return "product_item.jsp";
	}
	
	/**
	 * first double click query
	 * @return
	 * @throws Exception
	 */
	public String research() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		this.domain = this.service.queryByOid(this.keys, global);
		
		this.items = this.service.queryProducts(this.domain.getType(), global);
		
		return "reseach_item.jsp";
	}
}
