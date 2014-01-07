package ${modelPackage}.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;
import ${modelPackage}.entity.${entityName};


@Repository
public class ${entityName}Dao extends HibernateEntityDao<${entityName}> {


	/*
	@Override
	public void saveOrUpdateAll(List<${entityName}> c) {
		
		super.saveOrUpdateAll(c);
		
	}
	/*
	
	
	
	/*
	@Override
	public Page pagedQuery(QueryParameters params) {

		Criteria criteria = super.createCriteria(super.getEntityClass());

		// title
		if(StringUtils.isNotBlank(params.get("title"))) {
			criteria.add(Restrictions.like("title", params.get("title"), MatchMode.ANYWHERE));
		}

		// type
		if (StringUtils.isNotBlank(params.get("type"))) {
			criteria.add(Restrictions.eq("type", params.get("type")));
		}
		
		// Authorid
		if (StringUtils.isNotBlank(params.get("author"))) {
			criteria.add(Restrictions.eq("author", params.get("author")));
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
		
		criteria.addOrder(Order.asc("updatetime"));
		
		return super.pagedQuery(criteria, params);
	}
	*/


}
