package com.c306.core.springsecurity.resources.action;

import javax.servlet.ServletContext;

import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.core.springsecurity.resources.entity.SecResc;
import com.c306.core.springsecurity.resources.service.SecRescService;
import com.c306.core.springsecurity.utils.SecurityMetadataSourceHelper;
import com.opensymphony.xwork2.ActionContext;

public class SecRescAction extends BaseGenericActionSupport<SecResc> {

	private static final long serialVersionUID = 1L;
	@Autowired 
	private SecRescService service;
	
	@Override
	protected SecRescService getService() {
		return service;
	}

	
	@Override
	public String saveOrUpdate() throws Exception{
		
		// validate
		if( this.service.exists(this.domain, "resString") ) {
			
			this.addActionError(getText("hintinfo.savefailed.conflict"));
			return EDIT_RESULT;
		}
		
		String rs = super.saveOrUpdate();
		
		// new or update 成功后，要reload sources
		SecurityMetadataSourceHelper.reloadMetadataSource((ServletContext) ActionContext.getContext().get(StrutsStatics.SERVLET_CONTEXT));
		
		return rs;
	}

	public String listByRole() throws Exception {
		
		try {
			this.page = this.service.pagedQueryByRoleId(this.query);
		} catch (Exception e) {
			this.logger.error("listByRole error.", e);
		}
		
		return getDefaultResult() == null ? "role_resc_list" : getDefaultResult();
	}
	
	
	
}
