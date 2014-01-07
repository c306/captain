package com.c306.core.springsecurity.users.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.springsecurity.users.entity.SecUser;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

@Repository
public class SecUserDao  extends HibernateEntityDao<SecUser> {

	@Override
	public SecUser saveOrUpdate(SecUser instance) {
		super.saveOrUpdate(instance);
		return instance;
	}

	/**
	 * 设置用户角色
	 * @param userId 用户ID
	 * @param roleIds 多个角色ID，用","分隔：1,2,3,4,5
	 */
	public void saveRoles(final Long userId, String roleIds){
		
		Assert.notNull(userId);
		Assert.notNull(roleIds);
		
		String del = "delete from sec_user_role where user_id = " + userId;
//		String ins = "insert into sec_user_role(user_id,role_id) values(?, ?)";
		//delete
		super.jdbcTemplate.execute(del);
		//add
		for(final String roleId : roleIds.split(",")){
			if(roleId.length() > 0)
				super.jdbcTemplate.execute("insert into sec_user_role(user_id,role_id) values("+userId+", "+Long.valueOf(roleId)+")");
//			super.jdbcTemplate.execute(ins, new PreparedStatementCallback<Integer>(){
//				public Integer doInPreparedStatement(PreparedStatement pre)
//						throws SQLException, DataAccessException {
//					pre.setLong(1, userId);
//					pre.setLong(2, Long.valueOf(roleId));
//					return 1;
//				}});
		}
	}
	
//	public void modifyPwd(SecUser su){
//		this.createQuery("", "").executeUpdate();
//	}
	
	@Override
	public Page pagedQuery(QueryParameters params) {
		
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "u");

		if(StringUtils.isNotBlank(params.get("username"))) {
			criteria.add(Restrictions.like("username", params.get("username"), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(params.get("email"))) {
			criteria.add(Restrictions.like("email", params.get("email"), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(params.get("mobile"))) {
			criteria.add(Restrictions.like("mobile", params.get("mobile"), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(params.get("city"))) {
			criteria.add(Restrictions.like("city", params.get("city"), MatchMode.ANYWHERE));
		}

		// createtime range
		if( params.getDateProperty("fromTime") != null ) {
			criteria.add(Restrictions.ge( "createtime", params.getDateProperty("fromTime") ));
		}
		if( params.getDateProperty("toTime") != null ) {
			criteria.add(Restrictions.le( "createtime", params.getDateProperty("toTime") ));
		}

		// status
		if ( params.getIntProperty("status") != null ) {
			criteria.add(Restrictions.eq("status", params.getIntProperty("status")));
		}
		
		// not admin
		criteria.add(Restrictions.ne("username", "admin"));
		
		criteria.addOrder(Order.asc("username"));
		
		// query
		Page page = super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
		
		
		return page;
	}
	
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public Page pagedQueryByRoleId(QueryParameters params) {
		
		Assert.notNull(params.get("roleId"), "Parameter roleId must be not null.");
		
		String sqlcount = "select count(1) as count from sec_user u"
				+ " left join sec_user_role ur on ur.user_id = u.id and ur.role_id = ?"
				+ " left join sec_role r on r.id = ur.role_id"
				+ " where u.username <> 'admin' ";
		

		if(StringUtils.isNotBlank(params.get("username"))) {
			sqlcount += " and u.username like ?";
		}
		if(StringUtils.isNotBlank(params.get("email"))) {
			sqlcount += " and u.email like ?";
		}
		
		int idx = 0;
		SQLQuery query = this.getSession().createSQLQuery(sqlcount);
		query.setLong(idx++, params.getLongProperty("roleId"));

		if(StringUtils.isNotBlank(params.get("username"))) {
			query.setString(idx++, "%" + params.get("username") + "%");
		}
		if(StringUtils.isNotBlank(params.get("email"))) {
			query.setString(idx++, "%" + params.get("email") + "%");
		}
		
		query.addScalar("count", LongType.INSTANCE);
		int totalCount = ((Long)query.uniqueResult()).intValue();
		
		Page page = new Page(params.getPageSize(), params.getPageNo(), totalCount);
		
		
		// 返回分页对象
		if (totalCount < 1)
			return page;

		String sqlquery = "select u.*, r.name as roleName from sec_user u"
				+ " left join sec_user_role ur on ur.user_id = u.id and ur.role_id = ?"
				+ " left join sec_role r on r.id = ur.role_id"
				+ " where u.username <> 'admin' ";

		if(StringUtils.isNotBlank(params.get("username"))) {
			sqlquery += " and u.username like ?";
		}
		if(StringUtils.isNotBlank(params.get("email"))) {
			sqlquery += " and u.email like ?";
		}
		sqlquery += " order by u.username";
		
		idx = 0;
		query = this.getSession().createSQLQuery(sqlquery);
		query.setLong(idx++, params.getLongProperty("roleId"));

		if(StringUtils.isNotBlank(params.get("username"))) {
			query.setString(idx++, "%" + params.get("username") + "%");
		}
		if(StringUtils.isNotBlank(params.get("email"))) {
			query.setString(idx++, "%" + params.get("email") + "%");
		}
		
//		query.setEntity(0, new SecUser());// will exception
//		query.addRoot("u", SecUser.class);
		query.addScalar("id", LongType.INSTANCE);
		query.addScalar("username");
		query.addScalar("firstName");
		query.addScalar("lastName");
		query.addScalar("email");
		query.addScalar("roleName");
		
		query.setResultTransformer(Transformers.aliasToBean(SecUser.class));

		int startIndex = page.getFromIndex();
		List list = query.setFirstResult(startIndex).setMaxResults(page.getPageSize()).list();
		page.setDatas(list);
		return page;
	}
	


	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		try {
			List userList = this.findByHql("from com.c306.core.springsecurity.users.entity.SecUser u left join u.roles where u.username = ? ", new Object[]{username});
			
			if( userList != null && userList.size() > 0 ) {

				UserDetails user = (UserDetails) ((Object[]) userList.get(0))[0];
				this.logger.info("user detial: {}", user.toString());
				
				return user;
			}
		} catch (Exception e) {
			this.logger.error("log in loadUserByUsername error. ", e);
		}

        throw new UsernameNotFoundException("User " + username + " has no GrantedAuthority");
	}


	public boolean isCorrectPassword(Long userId, String password) {
		String hql = "select count(1) com.c306.core.springsecurity.users.entity.SecUser u where u.id = :userId and  u.password = :password";
		Long rs = (Long) this.getSession().createQuery(hql)
				.setString("password", password)
				.setLong("userId", userId).uniqueResult();
		return rs != 0;
	}
	
	public int changePassword(Long userId, String oldPassword, String newPassword) {
		String hql = "update com.c306.core.springsecurity.users.entity.SecUser u set u.password = :newPassword where u.id = :userId and u.password = :oldPassword";
		return this.getSession().createQuery(hql)
				.setString("newPassword", newPassword)
				.setString("oldPassword", oldPassword)
				.setLong("userId", userId)
				.executeUpdate();
	}
	
	public int resetPassword(Long userId, String newPassword) {
		String hql = "update com.c306.core.springsecurity.users.entity.SecUser u set u.password = :newPassword where u.id = :userId ";
		return this.getSession().createQuery(hql)
				.setString("newPassword", newPassword)
				.setLong("userId", userId)
				.executeUpdate();
	}
	
}
