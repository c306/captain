package com.c306.core.fileupload.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.fileupload.entity.Attachment;
import com.c306.core.support.Page;


@Repository
public class AttachmentDao extends HibernateEntityDao<Attachment>{

	@SuppressWarnings("rawtypes")
	public List list(Attachment t) throws Exception {
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");

//		if (StringUtils.isNotBlank(t.getObjid())) {
			criteria.add(Restrictions.eq("objid", t.getObjid().trim()));
//		}
		if (StringUtils.isNotBlank(t.getModuleid())) {
			criteria.add(Restrictions.eq("moduleid", t.getModuleid().trim()));
		}
		
		criteria.addOrder(Order.asc("ordinal"));

		Page page = super.pagedQuery(criteria, 1, 10000);
		
		return (List) page.getDatas();
	}

	

}
