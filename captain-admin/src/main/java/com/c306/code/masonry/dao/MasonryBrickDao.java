package com.c306.code.masonry.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;
import com.c306.hbm.TMasonryBrick;


@Repository
public class MasonryBrickDao extends HibernateEntityDao<TMasonryBrick> {

	@Override
	public void saveOrUpdateAll(List<TMasonryBrick> c) {
		
		this.createQuery("delete from TMasonryBrick").executeUpdate();
		
		super.saveOrUpdateAll(c);
	}

	
	@Override
	public Page pagedQuery(QueryParameters params) {
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");

		if (StringUtils.isNotBlank(params.get("objid"))) {
			criteria.add(Restrictions.eq("objid", params.get("objid")));
		}
		
		criteria.addOrder(Order.asc("ordinal"));
		
		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}


	public void removeCandidates(String... ids) {

		// remove masonry bricks
		String sql_del = "delete from " + getTableName() + " where objid = ?";
		// log
		logger.info("sql_del=" + sql_del);
		
		for (String pkidVlaue : ids) {
			if (pkidVlaue != null && pkidVlaue.trim().length() > 0) {
				super.jdbcTemplate.update(sql_del, pkidVlaue.trim());
			}
		}
	}

	

}
