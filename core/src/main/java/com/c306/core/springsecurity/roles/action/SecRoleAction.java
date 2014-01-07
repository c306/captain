package com.c306.core.springsecurity.roles.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.core.springsecurity.roles.entity.SecRole;
import com.c306.core.springsecurity.roles.service.SecRoleService;

public class SecRoleAction extends BaseGenericActionSupport<SecRole> {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SecRoleService service;
	
	@Override
	protected SecRoleService getService() {
		return service;
	}

	private Long userId;
	
	/**
	 * 
	 * @return
	 */
	public String retrieveRolesByUserId(){
		this.items = this.getService().retrieveRolesByUserId(userId);
		setSuccess(true);
		return "userroles";
	}
	

	public String saveOrUpdate() throws Exception{
		
		// validate
		if( this.service.exists(this.domain, "name") ) {
			
			this.addActionMessage(getText("hintinfo.savefailed.conflict"));
			return EDIT_RESULT;
		}
		
		this.domain.setName(this.domain.getName().toUpperCase());
		
		return super.saveOrUpdate();
	}
	
	public String assignUsers() throws Exception{
		
		try {
			// key: (add_userids;remove_userids)
			String[] uids = this.keys.split(";");
			this.service.saveAssignUsers(this.domain.getId(), uids[0].replace("(", ""), uids[1].replace(")", ""));
		} catch (Exception e) {
			this.logger.error("assignUsers error.", e);
			return SUCCESS;
		}
		
		
		this.setSuccess(true);
		return SUCCESS;
	}
	
	public String assignResources() throws Exception{
		
		try {
			// key: (add_rescids;remove_rescids)
			String[] rids = this.keys.split(";");
			this.service.saveAssignResources(this.domain.getId(), rids[0].replace("(", ""), rids[1].replace(")", ""));
		} catch (Exception e) {
			this.logger.error("assignResources error.", e);
			return SUCCESS;
		}
		
		
		this.setSuccess(true);
		return SUCCESS;
	}
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
