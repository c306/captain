package com.c306.code.masonry.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.code.article.dao.PictureDao;
import com.c306.code.masonry.dao.MasonryBrickDao;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.hbm.TMasonryBrick;


@Service
public class MasonryBrickService extends HibernateEntityManager<TMasonryBrick> {
	@Autowired
	private MasonryBrickDao dao;
	@Autowired
	private PictureDao pictureDao;


	public void removeCandidates(String... ids) {

		this.dao.removeCandidates(ids);
		
		// remove reference pics
		this.pictureDao.removeEntityByIdsWithoutCascade(ids);
		
	}


	@Override
	protected IEntityDao<TMasonryBrick> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}


	@Override
	public void saveOrUpdateAll(List<TMasonryBrick> list) {
		this.dao.saveOrUpdateAll(list);
	}

	

}
