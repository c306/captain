package com.c306.core.springsecurity.resources.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.springsecurity.resources.entity.SecResc;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

@Repository
public class SecRescDao extends HibernateEntityDao<SecResc> {

	@Override
	public Page pagedQuery(QueryParameters params) {
		
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");
		

		if (StringUtils.isNotBlank(params.get("resString"))) {
			criteria.add(Restrictions.like("resString", params.get("resString"), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(params.get("resType"))) {
			criteria.add(Restrictions.eq("resType", params.get("resType")));
		}
		
		criteria.addOrder(Order.asc("resString"));
		
		return super.pagedQuery(criteria, params);
	}
	
	

	/**
	 * 
	 * @param params
	 * @return
	 */
	public Page pagedQueryByRoleId(QueryParameters params) {
		
		Assert.notNull(params.get("roleId"), "Parameter roleId must be not null.");
		
		String sqlcount = "select count(1) count from sec_resc s "
				+ " left JOIN sec_resc_role rr on rr.resc_id = s.id and rr.role_id = ?"
				+ " where 1=1";
		

		if(StringUtils.isNotBlank(params.get("name"))) {
			sqlcount += " and s.name like ?";
		}
		if(StringUtils.isNotBlank(params.get("resType"))) {
			sqlcount += " and s.res_Type = ?";
		}
		if(StringUtils.isNotBlank(params.get("resString"))) {
			sqlcount += " and s.res_String like ?";
		}
		
		int idx = 0;
		SQLQuery query = this.getSession().createSQLQuery(sqlcount);
		query.setLong(idx++, params.getLongProperty("roleId"));

		if(StringUtils.isNotBlank(params.get("name"))) {
			query.setString(idx++, "%" + params.get("name") + "%");
		}
		if(StringUtils.isNotBlank(params.get("resType"))) {
			query.setString(idx++, params.get("resType"));
		}
		if(StringUtils.isNotBlank(params.get("resString"))) {
			query.setString(idx++, "%" + params.get("resString") + "%");
		}
		
		query.addScalar("count", LongType.INSTANCE);
		int totalCount = ((Long)query.uniqueResult()).intValue();
		
		Page page = new Page(params.getPageSize(), params.getPageNo(), totalCount);
		
		
		// 返回分页对象
		if (totalCount < 1)
			return page;

		String sqlquery = "select rr.role_id as roleId, s.id, s.name, s.res_type resType, s.res_string resString, s.priority from sec_resc s "
				+ " left JOIN sec_resc_role rr on rr.resc_id = s.id and rr.role_id = ?"
				+ " where 1=1";

		if(StringUtils.isNotBlank(params.get("name"))) {
			sqlquery += " and s.name like ?";
		}
		if(StringUtils.isNotBlank(params.get("resType"))) {
			sqlquery += " and s.res_Type = ?";
		}
		if(StringUtils.isNotBlank(params.get("resString"))) {
			sqlquery += " and s.res_String like ?";
		}
		sqlquery += " order by s.res_String";
		
		idx = 0;
		query = this.getSession().createSQLQuery(sqlquery);
		query.setLong(idx++, params.getLongProperty("roleId"));

		if(StringUtils.isNotBlank(params.get("name"))) {
			query.setString(idx++, "%" + params.get("name") + "%");
		}
		if(StringUtils.isNotBlank(params.get("resType"))) {
			query.setString(idx++, params.get("resType"));
		}
		if(StringUtils.isNotBlank(params.get("resString"))) {
			query.setString(idx++, "%" + params.get("resString") + "%");
		}
		
		query.addScalar("id", LongType.INSTANCE);// must be set LongType
		query.addScalar("roleId", LongType.INSTANCE);// must be set LongType
		query.addScalar("name");
		query.addScalar("resType");
		query.addScalar("resString");
		query.addScalar("priority");
		
		query.setResultTransformer(Transformers.aliasToBean(SecResc.class));

		int startIndex = page.getFromIndex();
		List list = query.setFirstResult(startIndex).setMaxResults(page.getPageSize()).list();
		page.setDatas(list);
		return page;
	}


}
