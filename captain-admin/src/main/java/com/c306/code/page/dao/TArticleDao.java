package com.c306.code.page.dao;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.c306.code.page.entity.TBar;
import com.c306.code.page.utils.ActionUtils;
import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.fileupload.entity.Attachment;
import com.c306.hbm.TArticle;

@Repository
public class TArticleDao extends HibernateEntityDao<TArticle> {

	/**
	 * query article by type
	 * 
	 * @param type
	 * @return
	 */
	public String queryByType(String type, String global) {
		DetachedCriteria dc = DetachedCriteria.forClass(TArticle.class);
		dc.add(Restrictions.eq("type", type));
		dc.addOrder(Order.asc("ordinal")).addOrder(Order.desc("updatetime"));

		List<TArticle> artList = dc.getExecutableCriteria(this.getSession()).list();
		List<TBar> barList = new ArrayList<TBar>();
		for(TArticle record:artList) {
			TBar bar = new TBar();
			bar.setId(record.getOid());
			bar.setObjid(record.getType());
			bar.setName(ActionUtils.getLanStr(record.getTitle(), record.getEtitle(), global));
			
			barList.add(bar);
		}
		JSONArray itms = (JSONArray) JSONSerializer.toJSON(barList);
		return itms.toString();
	}
	
	/**
	 * query article by oid
	 * 
	 * @param oid
	 * @param global
	 * @return
	 */
	public TArticle queryByOid(String oid, String global) {
		TArticle article = new TArticle();
		DetachedCriteria dc = DetachedCriteria.forClass(TArticle.class);
		dc.add(Restrictions.eq("oid", oid));
		
		List<TArticle> list = dc.getExecutableCriteria(this.getSession()).list();
		if (list != null && list.size() > 0) {
			article = list.get(0);
			article.setTitle(ActionUtils.getLanStr(article.getTitle(), article.getEtitle(), global));
			article.setContent(ActionUtils.getLanStr(article.getContent(), article.getEcontent(), global));
			
			DetachedCriteria atdc = DetachedCriteria.forClass(Attachment.class);
			atdc.add(Restrictions.eq("objid", oid)).addOrder(Order.asc("ordinal"));
			
			article.getAtts().addAll(atdc.getExecutableCriteria(this.getSession()).list());
			for(Attachment attch:article.getAtts()) {
				if("2".equals(attch.getModuleid())) {//moduleid =  2黑白图片, 3彩色图片, 4明细大图片
					article.setBlackurl(attch.getUri());
				}
			}
		}
		return article;
	}
	
	/**
	 * query products by type
	 * 
	 * @param type
	 * @param global
	 * @return
	 */
	public List<TArticle> queryProducts(String type, String global) {
		DetachedCriteria dc = DetachedCriteria.forClass(TArticle.class);
		dc.add(Restrictions.eq("type", type));
		dc.addOrder(Order.asc("ordinal")).addOrder(Order.desc("updatetime"));

		List<TArticle> list = dc.getExecutableCriteria(this.getSession()).list();
		if (list != null && list.size() > 0) {
			List<TArticle> aList = new ArrayList<TArticle>();
			String[] atrid = new String[list.size()];
			int i = 0;
			for (TArticle record : list) {
				atrid[i++] = record.getOid();
			}
			DetachedCriteria atdc = DetachedCriteria.forClass(Attachment.class);
			atdc.add(Restrictions.in("objid", atrid)).addOrder(
					Order.asc("ordinal"));
			List<Attachment> attList = atdc.getExecutableCriteria(this.getSession()).list();
			for (TArticle record : list) {
				record.setTitle(ActionUtils.getLanStr(record.getTitle(),
						record.getEtitle(), global));
				record.setContent(ActionUtils.getLanStr(record.getContent(),
						record.getEcontent(), global));
				for (Attachment attch : attList) {
					if (attch.getObjid().equals(record.getOid())) {
						if("2".equals(attch.getModuleid())) {//moduleid =  2黑白图片, 3彩色图片, 4明细大图片
							record.setBlackurl(attch.getUri());
						}
						record.getAtts().add(attch);
					}
				}
				aList.add(record);
			}
			return aList;
		}
		return list;
	}
	
	public TArticle queryPractice(String type, String global) {
		DetachedCriteria dc = DetachedCriteria.forClass(TArticle.class);
		dc.add(Restrictions.eq("type", type));

		TArticle article = new TArticle();
		List<TArticle> list = dc.getExecutableCriteria(this.getSession()).list();
		if (list != null && list.size() > 0) {
			article = list.get(0);
			article.setTitle(ActionUtils.getLanStr(article.getTitle(), article.getEtitle(), global));
			article.setContent(ActionUtils.getLanStr(article.getContent(), article.getEcontent(), global));
		}
		return article;
	}
	
	public List<TArticle> queryPeople(String type, String global) {
		DetachedCriteria dc = DetachedCriteria.forClass(TArticle.class);
		dc.add(Restrictions.eq("type", type));
		dc.addOrder(Order.asc("ordinal")).addOrder(Order.desc("updatetime"));
		
		List<TArticle> artList = new ArrayList<TArticle>();
		List<TArticle> list = dc.getExecutableCriteria(this.getSession()).list();
		if (list != null && list.size() > 0) {
			for(TArticle article:list) {
				article.setTitle(ActionUtils.getLanStr(article.getTitle(), article.getEtitle(), global));
				article.setContent(ActionUtils.getLanStr(article.getContent(), article.getEcontent(), global));
				
				artList.add(article);
			}
		}
		return artList;
	}
	
	public TArticle queryPeopleDetail(String oid, String global) {
		TArticle article = new TArticle();
		DetachedCriteria dc = DetachedCriteria.forClass(TArticle.class);
		dc.add(Restrictions.eq("oid", oid));
		
		List<TArticle> list = dc.getExecutableCriteria(this.getSession()).list();
		if (list != null && list.size() > 0) {
			article = list.get(0);
			article.setTitle(ActionUtils.getLanStr(article.getTitle(), article.getEtitle(), global));
			article.setContent(ActionUtils.getLanStr(article.getContent(), article.getEcontent(), global));
			article.setAddress(ActionUtils.getLanStr(article.getAddress(), article.getEaddress(), global));
			article.setBlackurl(ActionUtils.getLanStr(article.getBlackurl(), article.getEmap(), global));
		}
		return article;
	}
}
