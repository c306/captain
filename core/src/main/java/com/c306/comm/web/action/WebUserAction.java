package com.c306.comm.web.action;

import java.io.File;

import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.core.base.service.IGenericManager;
import com.c306.core.springsecurity.users.entity.SecUser;

/**
 * 
 * WEB user login action
 * 
 * @author chens
 *
 */
public class WebUserAction extends BaseGenericActionSupport<SecUser> {
	
	private File upload;
    private String uploadContentType;
    private String uploadFileName;


	@Override
	protected IGenericManager<SecUser> getService() {
		return null;
	}

	
	/**
	 * 注册
	 * @return
	 */
	public String register(){
//		SecUser loginUser = (SecUser) super.getHttpServletRequest().getSession().getAttribute(WebUserConstants.LOGINUSER);


		// 校验重复
		if( getService().exists(domain, "username") ){

			this.addActionMessage(this.getText("hintinfo.savefailed.conflict", "Username"));
			return EDIT_RESULT;
		}
		if( getService().exists(domain, "email") ){

			this.addActionMessage(this.getText("hintinfo.savefailed.conflict", "Email"));
			return EDIT_RESULT;
		}
		
		return "userinfo";
	}
	
	public String userinfo(){
//		SecUser loginUser = (SecUser) super.getHttpServletRequest().getSession().getAttribute(WebUserConstants.LOGINUSER);
		
		return "userinfo";
	}
	
	public String updateUserinfo() throws Exception{
//		SecUser loginUser = (SecUser) super.getHttpServletRequest().getSession().getAttribute(WebUserConstants.LOGINUSER);
//		
//		//上传头像
////		if(upload != null){
////			String PROJECTREALPATH= ServletActionContext.getServletContext().getRealPath("/");
////			int pos = this.uploadFileName.lastIndexOf('.');
////			String suffix = (pos < 0) ? "" : this.uploadFileName.substring(pos).toLowerCase();
////			String destNmae = String.format("%1$tm%1$td%1$tM%1$ts%1$tL", new java.util.Date()) + suffix;
////			FileUtils.copyFile(upload, new File(PROJECTREALPATH.concat("upload/headphoto/"+destNmae)), true);
////			//头像uri
////			this.domain.setAvatar("upload/headphoto/"+destNmae);
////		}
//		
////		super.logger.info("ddddddddddddddd "+ ToStringBuilder.reflectionToString(suser, ToStringStyle.MULTI_LINE_STYLE));
////		super.logger.info("ddddddddddddddd "+ ToStringBuilder.reflectionToString(domain, ToStringStyle.MULTI_LINE_STYLE));
//		
//		saveOrUpdate();
//		
//		this.domain = this.service.get(loginUser.getId());
//		
//		super.getHttpServletRequest().getSession().setAttribute(WebUserConstants.LOGINUSER, this.domain);
		
		return "userinfo";
	}
	
	

}
