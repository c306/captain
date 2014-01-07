package com.c306.code.article.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.fileupload.entity.Attachment;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;


@Repository("pictureDao")
public class PictureDao extends HibernateEntityDao<Attachment>{
	
	@Override
	public Page pagedQuery(QueryParameters params) {
		
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");
		
		if (StringUtils.isNotBlank(params.get("objid"))) {
			criteria.add(Restrictions.eq("objid", params.get("objid")));
		}
		if (StringUtils.isNotBlank(params.get("moduleid"))) {
			criteria.add(Restrictions.eq("moduleid", params.get("moduleid")));
		}
		if (StringUtils.isNotBlank(params.get("nodepath"))) {
			criteria.add(Restrictions.eq("nodepath", params.get("nodepath")));
		}
		
		criteria.addOrder(Order.desc("ordinal"));
		
		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}
	
	/**
	 *  查询 work 节点及下级所有节点的略图片
	 * @param t
	 * @param pageNo
	 * @param pageSize
	 * @param criterions
	 * @return
	 * @throws Exception
	 */
	public Page findCandidates(Attachment t, int pageNo, int pageSize) throws Exception {
		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");
		
		criteria.add(Restrictions.like("nodepath", "|002|%"));// work 节点及下级所有节点
		criteria.add(Restrictions.eq("moduleid", "1"));// 1：首页展示图片；2：黑白图片；3：彩色图片4：明细大图片
//		criteria.add(Restrictions.eq("status", 1));// 
		
//		//
		criteria.add(Restrictions.sqlRestriction("not exists ( select b.oid from t_masonrybrick b where b.objid = {alias}.oid )"));
		
		if (StringUtils.isNotBlank(t.getObjid())) {
			criteria.add(Restrictions.eq("objid", t.getObjid().trim()));
		}
		
		criteria.addOrder(Order.asc("ordinal"));
		
		return super.pagedQuery(criteria, pageNo, pageSize);
	}

	public void removeAll(List<Attachment> list) {
		try {
			if( list != null && list.size() > 0 )
				for (Attachment entity : list) {
					super.remove(entity);
				}
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
	
	
}
