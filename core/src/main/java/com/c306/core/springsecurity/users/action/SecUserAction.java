package com.c306.core.springsecurity.users.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.core.springsecurity.users.entity.SecUser;
import com.c306.core.springsecurity.users.service.SecUserService;
import com.c306.utils.ExcelUtils;

/**
 * 
 * user management 用户管理
 * 
 * @author chens
 *
 */
public class SecUserAction  extends BaseGenericActionSupport<SecUser>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SecUserService service;

	@Override
	protected SecUserService getService() {
		return service;
	}
	
	
	/**
	 * 用户新增，修改
	 */
	@Override
	public String saveOrUpdate() throws Exception {
//		SecUser suser = (SecUser) super.getHttpServletRequest().getSession().getAttribute(WebUserConstants.LOGINUSER);
//		if(!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new GrantedAuthorityImpl("ROLE_ADMIN")) && suser.getId() != this.domain.getId()){
//			throw new Exception(getText("global.hintinfo.nopermission", "没有权限进行此操作！"));
//		}
		

		// 校验重复
		if( getService().exists(domain, "username") ){

			this.addActionMessage(this.getText("hintinfo.savefailed.username.conflict"));
			return EDIT_RESULT;
		}
		if( getService().exists(domain, "email") ){

			this.addActionMessage(this.getText("hintinfo.savefailed.email.conflict"));
			return EDIT_RESULT;
		}
		
		// save or update userInfo
		domain = getService().saveOrUpdate(domain);
		setSuccess(true);
		//this.addActionMessage(getText("global.hintinfo.savesuccess", "保存成功！"));
		this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
		
		// reload userInfo and Roles
//		this.domain = this.service.get(suser.getId());
//		super.getHttpServletRequest().getSession().setAttribute(WebUserConstants.LOGINUSER, this.domain);
		
		return list();
	}
	
	
	@Override
	public String list() throws Exception {
		String rs = super.list();
		return getDefaultResult() == null ? rs : getDefaultResult();
	}
	
	
	public String listByRole() throws Exception {
		
		try {
			this.page = this.service.pagedQueryByRoleId(this.query);
		} catch (Exception e) {
			this.logger.error("listByRole error.", e);
		}
		
		return getDefaultResult() == null ? "role_user_list" : getDefaultResult();
	}
	
	
	@Override
	public String delete() throws Exception {
		
		// can't delete admin
//		if("".equals("admin")) {
//			
//		}
		
		return super.delete();
	}
	
	/**
	 * 分配角色
	 * @return
	 * @throws Exception
	 */
	public String saveRoles() throws Exception {
		this.getService().saveRoles(this.domain.getId(), getKeys());
		setSuccess(true);
		return "saveRoles";
	}
	
//	public String tomodifyPwd(){
//		this.domain = (SecUser) super.getHttpServletRequest().getSession().getAttribute(WebUserConstants.LOGINUSER);
//		return "modifyPwd";
//	}
//	
//	public String modifyPwd(){
//		try {
//			domain = getService().get(domain.getId());
//			if(domain.getPassword().equals(this.query.get("oldPassword"))) {
//				this.service.changePassword(userId, this.query.get("oldPassword"), this.query.get("newPassword"));
//				logger.info("密码成功。");
//				addActionMessage(getText("admin.user.password.changepasswordsucc", "密码修改成功。"));
//			} else {
//				logger.info("原密码输入错误。");
//				addActionError(getText("admin.user.password.oldpwdwrong","原密码输入错误。"));
//			}
//		} catch (Exception e) {
//			logger.error("修改密码异常.", e);
//			addActionError("修改密码异常: " + e.getMessage());
//		}
//		return "modifyPwd";
//	}
	
	/**
	 * 前台转后台
	 * @return
	 */
//	public String front2manage(){
//		// Spring Security
//		try {
//			
//			SecUser record = (SecUser)super.getHttpServletRequest().getSession().getAttribute(ActionUtils.USER_INFO);
//			
//			WebApplicationContext wctx = WebApplicationContextUtils.getWebApplicationContext(super.getHttpServletRequest().getSession().getServletContext());
//			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(record.getUsername(), record.getPassword());
//			authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(super.getHttpServletRequest()));
//			Authentication auth = ((FilterSecurityInterceptor)wctx.getBean("filterSecurityInterceptor")).getAuthenticationManager().authenticate(authRequest);
//			SecurityContextHolder.getContext().setAuthentication(auth);
//			
//			AuthenticationSuccessHandlerImpl ushi = (AuthenticationSuccessHandlerImpl)wctx.getBean("authenticationSuccessHandlerImpl");
//			ushi.setUserInfo(super.getHttpServletRequest(), super.getHttpServletResponse(), auth);
//			
////			HttpSessionSecurityContextRepository hscr = (HttpSessionSecurityContextRepository)wctx.getBean(HttpSessionSecurityContextRepository.class);
////			hscr.saveContext(SecurityContextHolder.getContext(), super.getHttpServletRequest(), super.getHttpServletResponse());
//			
//			super.getHttpServletRequest().getSession().setAttribute("SPRING_SECURITY_CONTEXT", auth);
//			
//		} catch (AuthenticationException e) {
//			logger.error("未通过Spring Security认证。Authentication failed: ", e);
//		} catch (Exception e) {
//			logger.error("", e);
//		}
//		
//		return "front2manage";
//	}

	public String exportData() {
		return "export";
	}
	
	/**
	 * 导出
	 * @return
	 * @throws Exception
	 */
	public InputStream getInputStream() throws Exception{
		this.setPageSize(Short.MAX_VALUE);
		this.setPageNo(1);
		list();
		byte[] buf = new byte[0];
		try {
			Map<String, String> headMap = new LinkedHashMap<String, String>();
//			headMap.put("sn", getText("admin.label.comm.serial"));
//			headMap.put("status", getText("admin.thead.comm.status"));
			headMap.put("username", getText("admin.label.userinfo.fullname"));
			headMap.put("city", getText("admin.label.userinfo.city"));
			headMap.put("email", getText("admin.label.userinfo.email"));
			headMap.put("createtime", getText("admin.thead.comm.createtime"));
//			headMap.put("etitle", getText("etitle"));
			buf = ExcelUtils.exportToExcel(super.getPage().getDatas(), headMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(buf);
	}
	
}
