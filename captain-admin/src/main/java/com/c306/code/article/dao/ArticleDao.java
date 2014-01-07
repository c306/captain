package com.c306.code.article.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.c306.code.masonry.dao.MasonryBrickDao;
import com.c306.code.utils.ImageSizeUtils;
import com.c306.core.CoreConstants;
import com.c306.core.base.dao.HibernateEntityDao;
import com.c306.core.fileupload.entity.Attachment;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;
import com.c306.hbm.TArticle;

@Repository
public class ArticleDao extends HibernateEntityDao<TArticle> {
	
	@Autowired
	private MasonryBrickDao masonryBrickDao;
	@Autowired
	private ArticleCategoryDao articleCategoryDao;
	@Autowired
	private PictureDao pictureDao;

	@Override
	public Page pagedQuery(QueryParameters params) {

		Criteria criteria = this.getSession().createCriteria(this.getEntityClass(), "t");

		// title
		if(StringUtils.isNotBlank(params.get("title"))) {
			criteria.add(Restrictions.like("title", params.get("title"), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(params.get("etitle"))) {
			criteria.add(Restrictions.like("etitle", params.get("etitle"), MatchMode.ANYWHERE));
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
		
		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}

	@Override
	public TArticle saveOrUpdate(TArticle instance) {
		
		// find tree node path
		if(instance.getTypePath() == null) {
			String nodePath = articleCategoryDao.findNodePath(instance.getType());
			instance.setTypePath(nodePath);
		}
		
		List<Attachment> updateAtts = new ArrayList<Attachment>();
		List<Attachment> existAtts = null;
		
		if(StringUtils.isNotBlank(instance.getOid())) {
			existAtts = pictureDao.find("objid", instance.getOid());
		}
		
		// save article
		super.saveOrUpdate(instance);
		
		// 设置 tree node path and evit attr
		setupAtts(instance, instance.getAtts(), existAtts, updateAtts);
		setupAtts(instance, instance.getDarkPhotos(), existAtts, updateAtts);
		setupAtts(instance, instance.getColorPhotos(), existAtts, updateAtts);
		setupAtts(instance, instance.getLargePhotos(), existAtts, updateAtts);
		
		// delete pictures
		pictureDao.removeAll(existAtts);
		//
		removeRef(existAtts);
		// save new pictures
		pictureDao.saveOrUpdateAll(updateAtts);
		
		return instance;
	}
	
	private void setupAtts(TArticle instance, List<Attachment> formList, List<Attachment> existAtts, List<Attachment> updateAtts) {
		for(int i=0;i<formList.size();i++){
			Attachment att = formList.get(i);
			att.setObjid(instance.getOid());
			att.setNodepath(instance.getTypePath());
			
			if("1".equals(att.getModuleid())) { // 如果是首页展示图片
				// 计算首页展示图片大小
				int[] dimention = ImageSizeUtils.getUnitDimention(CoreConstants.UPLOAD_STORE_PATH + att.getUri());
				if(dimention != null) {
					att.setWidth(dimention[0]>4?4:dimention[0]);
					att.setHeigh(dimention[1]>4?4:dimention[1]);
				}
			}
			
			// check
			checkExists(att, existAtts);
			// add or upadate
			updateAtts.add(att);
		}
	}
	
	private boolean checkExists(Attachment att, List<Attachment> list) {
		if(list==null)
			return false;
		for (Attachment li : list) {
			if(li.getOid().equals(att.getOid())) {
				list.remove(li);
				this.evit(li);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void move2trash(String [] ids) {
		if( ids == null )
			return;
		// delete attachments pictures
//		String sql_trash = "update " + pictureDao.getTableName() + " set status=0 where objid = ?";
		String sql_trash = "delete from " + pictureDao.getTableName() + " where objid = ?";
		logger.info(sql_trash);
		for (String articleId : ids) {
			if (articleId != null && articleId.trim().length() > 0) {
				super.jdbcTemplate.update(sql_trash, articleId);
			}
		}
		//
		removeRef(ids);
		// delete articles
		super.move2trash(ids);
	}
	
	@Override
	public void removeByIds(String [] ids) {
		if( ids == null )
			return;
		// delete attachments pictures
		String sql_delete = "delete from " + pictureDao.getTableName() + " where objid = ?";
		logger.info(sql_delete);
		for (String articleId : ids) {
			if (articleId != null && articleId.trim().length() > 0) {
				super.jdbcTemplate.update(sql_delete, articleId);
			}
		}
		//
		removeRef(ids);
		// delete articles
		super.removeByIds(ids);
	}
	
	/**
	 * 删除  masonry brick 表相关图片
	 * @param ids
	 * @throws Exception
	 */
	private void removeRef(String [] ids) {
		if( ids == null )
			return;
		String sql_delete = "delete from " + masonryBrickDao.getTableName() + " where article_id = ?";
		for (String articleId : ids) {
			if (articleId != null && articleId.trim().length() > 0) {
				super.jdbcTemplate.update(sql_delete, articleId);
			}
		}
		logger.info(sql_delete);
	}
	/**
	 * 删除  masonry brick 表相关图片
	 * @param atts
	 */
	private void removeRef(List<Attachment> atts) {
		if( atts == null )
			return;
		String sql_delete = "delete from " + masonryBrickDao.getTableName() + " where objid = ?";
		logger.info(sql_delete);
		for(Attachment att:atts) {
			if( "1".equals(att.getModuleid()) ) { // 如果是首页展示图片
				super.jdbcTemplate.update(sql_delete, att.getOid());
			}
		}
	}

}
