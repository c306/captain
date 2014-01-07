package com.c306.core.quartz.log.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.quartz.log.entity.JobLogInfo;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

@Repository
public class JobLogDao extends HibernateEntityDao<JobLogInfo> {

	@Override
	public Page pagedQuery(QueryParameters params) {

		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");
		criteria.addOrder(Order.desc("fireTime"));
		
		

		if (StringUtils.isNotBlank(params.get("jobName"))) {
			criteria.add(Restrictions.like("jobName", params.get("jobName"), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(params.get("jobGroup"))) {
			criteria.add(Restrictions.like("jobGroup", params.get("jobGroup"), MatchMode.ANYWHERE));
		}
		if ( params.getIntProperty("status") != null ) {
			criteria.add(Restrictions.eq("status", params.getIntProperty("status")));
		}
		// fire time range [fromTime, toTime), not include toTime.
		if ( params.getTimestampProperty("fromTime") != null ) {
			criteria.add(Restrictions.ge("fireTime", params.getTimestampProperty("fromTime").getTime()));
		}
		if ( params.getTimestampProperty("toTime") != null ) {
			criteria.add(Restrictions.lt("fireTime", params.getTimestampProperty("toTime").getTime()));
		}

//		// publishtime
//		if ( params.getDateProperty("fromTime") != null ) {
//			criteria.add(Restrictions.ge( "publishtime", params.getDateProperty("fromTime") ));
//		}
//		if ( params.getDateProperty("toTime") != null ) {
//			criteria.add(Restrictions.le( "publishtime", params.getDateProperty("toTime") ));
//		}

		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}
	
}
