package com.c306.core.springsecurity.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.c306.CaptainServiceException;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.core.springsecurity.roles.entity.SecRole;
import com.c306.core.springsecurity.roles.service.SecRoleService;
import com.c306.core.springsecurity.users.dao.SecUserDao;
import com.c306.core.springsecurity.users.entity.SecUser;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

//@Service("customUserDetailsService") declared in SpringSecurity-context.xml
public class SecUserService  extends HibernateEntityManager<SecUser> implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SecUserDao dao;
	
	@Autowired
	private SecRoleService secRoleService;
	
	@Override
	protected IEntityDao<SecUser> getDao() {
		return dao;
	}

	/*
    private static int count = 1;
	public SecUserService() {
		// 调用2次:使用aop时，cglib创建代理类时会再调用 一次
		System.out.println("Create Bean SecUserService------------------------------------ count="+count++);
	}
    private static int afterPropertiesSetCount = 1;
	@Override, InitializingBean
	public void afterPropertiesSet() throws Exception {
		// 只调用1次
		System.out.println("Create Bean SecUserService afterPropertiesSet------------------------------------ count="+afterPropertiesSetCount++);
	}
	//Create Bean SecUserService------------------------------------ count=1
	//Create Bean SecUserService afterPropertiesSet------------------------------------ count=1
	//Create Bean SecUserService------------------------------------ count=2
	 */



	@Override
	public SecUser saveOrUpdate(SecUser instance) {
		
		// if new user, set password 
		if( instance.getId() == null ) {
			instance.setPassword(passwordEncoder.encode(instance.getUsername()));
			instance.setEnabled(true);
		}
		
		List<SecRole> roles = secRoleService.retrieveRolesByUserId(instance.getId());
		instance.getRoles().addAll(roles);
		dao.saveOrUpdate(instance);
		return instance;
	}
	
	/**
	 * 设置用户角色
	 * @param userId 用户ID
	 * @param roleIds 多个角色ID，用","分隔：1,2,3,4,5
	 */
	public void saveRoles(final Long userId, String roleIds){
		this.dao.saveRoles(userId, roleIds);
	}



	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDetails user = this.dao.loadUserByUsername(username);
		
		
		return user;
	}

	
	public boolean isCorrectPassword(Long userId, String password) {
		return this.dao.isCorrectPassword(userId, password);
	}
	
	/**
	 * 修改密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 */
	public void changePassword(Long userId, String oldPassword, String newPassword) {
		
		int rs = this.dao.changePassword(userId, oldPassword, newPassword);
		if( rs == 0 ) {
			throw new CaptainServiceException("oldPassword.incorrect");
		}
		
	}

	/**
	 * 重置密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 */
	public void resetPassword(Long userId, String newPassword) {
		int rs = this.dao.resetPassword(userId, newPassword);
		if( rs == 0 ) {
			throw new CaptainServiceException();
		}
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public Page pagedQueryByRoleId(QueryParameters params) {
		return this.dao.pagedQueryByRoleId(params);
	}


	
	
}
