package com.c306.core.springsecurity.roles.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.core.springsecurity.roles.dao.SecRoleDao;
import com.c306.core.springsecurity.roles.entity.SecRole;

@Service
public class SecRoleService extends HibernateEntityManager<SecRole> {
	
	@Autowired
	private SecRoleDao dao;

	public List<SecRole>  retrieveRolesByUserId(Long userid){
		return this.dao.retrieveRolesByUserId(userid);
	}

	@Override
	protected IEntityDao<SecRole> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	/**
	 * 
	 * @param roleId
	 * @param addUserIds separate ','
	 * @param removeUserIds separate ','
	 */
	public void saveAssignUsers(Long roleId, String addUserIds, String removeUserIds) {
		this.dao.saveAssignUsers(roleId, addUserIds, removeUserIds);
	}

	/**
	 * 
	 * @param roleId
	 * @param removeRescIds separate ','
	 * @param removeRescIds separate ','
	 */
	public void saveAssignResources(Long roleId, String addRescIds, String removeRescIds) {
		this.dao.saveAssignResources(roleId, addRescIds, removeRescIds);
	}
}
