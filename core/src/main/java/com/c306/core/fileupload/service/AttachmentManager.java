package com.c306.core.fileupload.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.core.fileupload.dao.AttachmentDao;
import com.c306.core.fileupload.entity.Attachment;


@Service
public class AttachmentManager extends HibernateEntityManager<Attachment>{
	
	@Autowired
	private AttachmentDao dao;

	@SuppressWarnings("rawtypes")
	public List list(Attachment t) throws Exception {
		return this.dao.list(t);
	}

	@Override
	protected IEntityDao<Attachment> getDao() {
		return this.dao;
	}

	

}
