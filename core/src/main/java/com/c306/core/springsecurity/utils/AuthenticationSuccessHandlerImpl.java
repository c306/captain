package com.c306.core.springsecurity.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.c306.core.springsecurity.users.WebUserConstants;
import com.c306.core.springsecurity.users.entity.SecUser;
import com.c306.core.springsecurity.users.service.SecUserService;

//@Component
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

	@Autowired
	private SecUserService service;
	
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		
//		setUserInfo(request, response, authentication);
		
		Collection<? extends GrantedAuthority> autths = authentication.getAuthorities();
//		if(autths.contains(new GrantedAuthorityImpl("ROLE_MEMBER"))){//普通用户角色
//			
//			getRedirectStrategy().sendRedirect(request, response, "/admin/secuser!userinfo.action");
//			
//		} else if (autths.contains(new GrantedAuthorityImpl("ROLE_SUPERADMIN"))) {
//			
//			getRedirectStrategy().sendRedirect(request, response, "/admin/secuser!toModifyAdminPwd.action");
//			
//		} else {
//
//			super.onAuthenticationSuccess(request, response, authentication);
//			
//		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	public void setUserInfo(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {

		UserDetails ud = (UserDetails)authentication.getPrincipal();
		
		List<SecUser> users = service.find("username", ud.getUsername());
		
		SecUser user = users.get(0);
		
		request.getSession().setAttribute(WebUserConstants.LOGINUSER, user);
		logger.info("登录成功。");
	}
}
