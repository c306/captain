package com.c306.code.page.action;



import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.page.service.TNavigationService;
import com.c306.code.page.utils.ActionUtils;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.hbm.TNavigation;

public class BarAction extends BaseGenericActionSupport<TNavigation> {
	
	private static final long serialVersionUID = -3725011960238912503L;
	@Autowired
	private TNavigationService service;

	@Override
	protected TNavigationService getService() {
		return this.service;
	}
	private String dataItems;
	public String getDataItems() {
		return dataItems;
	}
	public void setDataItems(String dataItems) {
		this.dataItems = dataItems;
	}

	/**
	 * bar info query
	 * @return
	 * @throws Exception
	 */
	public String first() throws Exception {
		String global = ActionUtils.getCurrGlobal(super.getHttpServletRequest().getSession());
		dataItems = this.service.queryBarInfo(global);
		
		return "main.jsp";
	}
	
	
}
