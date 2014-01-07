package com.c306.comm.dictionary.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.c306.comm.dictionary.entity.Dictionary;
import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

@Repository
public class DictionaryDao extends HibernateEntityDao<Dictionary> {

	/**
	 * 根据key查询
	 * @param key
	 * @return
	 */
	public List<Dictionary> retrieveList(String key){
		if(GenericValidator.isBlankOrNull(key))
			return new ArrayList<Dictionary>(0);
		return super.find("key", key);
	}

	@Override
	public Page pagedQuery(QueryParameters params) {
		
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");


		//key
		if(StringUtils.isNotBlank(params.get("key"))) {
			criteria.add(Restrictions.like("key", params.get("key"), MatchMode.ANYWHERE));
		}
		/**
		 * 名称
		 */
		if(StringUtils.isNotBlank(params.get("name"))) {
			criteria.add(Restrictions.like("name", params.get("name"), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(params.get("ename"))) {
			criteria.add(Restrictions.like("ename", params.get("ename"), MatchMode.ANYWHERE));
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
		
		criteria.addOrder(Order.asc("name"));
		
		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}
	
	/**
	 * query list
	 * @param record
	 * @return
	 */
	public List<Dictionary> query(Dictionary record) {
		Criteria criteria = this.getSession().createCriteria(super.getEntityClass());
		//key
		if(StringUtils.isNotBlank(record.getKey())) {
			criteria.add(Restrictions.eq("key", record.getKey()));
		}
		//oid
		if(StringUtils.isNotBlank(record.getObjid())) {
			String objid[] = record.getObjid().split(", ");
			List<Integer> objList = new ArrayList<Integer>();
			for(String id:objid) {
				if(StringUtils.isNotBlank(id))
					objList.add(Integer.valueOf(id));
			}
			criteria.add(Restrictions.in("oid", objList));
		}
		if(record.getStatus() != null) {
			criteria.add(Restrictions.eq("status", record.getStatus()));
		}
		return criteria.list();
	}
}
