package com.c306.code.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.code.article.dao.PictureDao;
import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.core.fileupload.entity.Attachment;
import com.c306.core.support.Page;


@Service
public class PictureService extends HibernateEntityManager<Attachment>{
	
	@Autowired
	private PictureDao dao;
	
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
		return dao.findCandidates(t, pageNo, pageSize);
	}

	public void removeAll(List<Attachment> list) {
		dao.removeAll(list);
	}

	@Override
	protected IEntityDao<Attachment> getDao() {
		return dao;
	}
	
	
}
