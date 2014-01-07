package com.c306.core.springsecurity.roles.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.springsecurity.roles.entity.SecRole;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

@Repository
public class SecRoleDao extends HibernateEntityDao<SecRole> {
	
	final static String DELETE_USER_ROLE = "delete from sec_user_role where user_id = ? and role_id = ?";
	final static String INSERT_USER_ROLE = "insert sec_user_role(user_id, role_id) values(?,?)";
	
	final static String DELETE_RESC_ROLE = "delete from sec_resc_role where resc_id = ? and role_id = ?";
	final static String INSERT_RESC_ROLE = "insert sec_resc_role(resc_id, role_id) values(?,?)";

	public List<SecRole>  retrieveRolesByUserId(Long userid){
		String sql = "select r.ID, r.NAME, r.DESCN" +
				" from sec_user_role ur join sec_role r on ur.role_id = r.id" +
				" where ur.user_id = " + userid;
		return super.jdbcTemplate.query(sql, new RowMapper<SecRole>(){
			public SecRole mapRow(ResultSet rs, int idx) throws SQLException {
				SecRole role = new SecRole();
				role.setId(rs.getLong("ID"));
				role.setName(rs.getString("NAME"));
				role.setDescn(rs.getString("DESCN"));
				return role;
			}});
	}
	
	@Override
	public Page pagedQuery(QueryParameters params) {
		
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");

		if (StringUtils.isNotBlank(params.get("name"))) {
			criteria.add(Restrictions.like("name", params.get("name"), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(params.get("descn"))) {
			criteria.add(Restrictions.like("descn", params.get("descn"), MatchMode.ANYWHERE));
		}
		
		if ( params.getDateProperty("createtime") != null ) {
			criteria.add(Restrictions.ge( "createtime", params.getDateProperty("createtime") ));
		}
		if ( params.getIntProperty("status") != null ) {
			criteria.add(Restrictions.eq("status", params.getIntProperty("status")));
		}
		
		criteria.addOrder(Order.asc("name"));
		
		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}

	/**
	 * 只做删除和插入操作，不校验数据是否重复、是否合法。
	 * @param roleId
	 * @param addUserIds separate ','
	 * @param removeUserIds separate ','
	 */
	public void saveAssignUsers(final Long roleId, String addUserIds, String removeUserIds) {
		
		if( StringUtils.isNotBlank(removeUserIds) ) {
			// delete
			final String[] removeUids = removeUserIds.split(",");
			this.jdbcTemplate.batchUpdate(DELETE_USER_ROLE, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, Long.valueOf(removeUids[i]));
					ps.setLong(2, roleId);
					logger.info(DELETE_USER_ROLE);
				}
				
				@Override
				public int getBatchSize() {
					return removeUids.length;
				}
			});
		}

		if( StringUtils.isNotBlank(addUserIds) ) {
			// add
			final String[] addUids = addUserIds.split(",");
			this.jdbcTemplate.batchUpdate(INSERT_USER_ROLE, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, Long.valueOf(addUids[i]));
					ps.setLong(2, roleId);
					logger.info(INSERT_USER_ROLE);
				}
				
				@Override
				public int getBatchSize() {
					return addUids.length;
				}
			});
		}
		
	}
	
	/**
	 * 只做删除和插入操作，不校验数据是否重复、是否合法。
	 * @param roleId
	 * @param addRescIds separate ','
	 * @param removeRescIds separate ','
	 */
	public void saveAssignResources(final Long roleId, String addRescIds, String removeRescIds) {
		
		if( StringUtils.isNotBlank(removeRescIds) ) {
			// delete
			final String[] removeUids = removeRescIds.split(",");
			this.jdbcTemplate.batchUpdate(DELETE_RESC_ROLE, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, Long.valueOf(removeUids[i]));
					ps.setLong(2, roleId);
					logger.info(DELETE_RESC_ROLE);
				}
				
				@Override
				public int getBatchSize() {
					return removeUids.length;
				}
			});
		}
		
		if( StringUtils.isNotBlank(addRescIds) ) {
			// add
			final String[] addUids = addRescIds.split(",");
			this.jdbcTemplate.batchUpdate(INSERT_RESC_ROLE, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setLong(1, Long.valueOf(addUids[i]));
					ps.setLong(2, roleId);
					logger.info(INSERT_RESC_ROLE);
				}
				
				@Override
				public int getBatchSize() {
					return addUids.length;
				}
			});
		}
		
	}
	
}
