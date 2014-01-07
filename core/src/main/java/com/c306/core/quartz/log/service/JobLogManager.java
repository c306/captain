package com.c306.core.quartz.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c306.core.base.dao.IEntityDao;
import com.c306.core.base.service.HibernateEntityManager;
import com.c306.core.quartz.log.dao.JobLogDao;
import com.c306.core.quartz.log.entity.JobLogInfo;

@Service
public class JobLogManager extends HibernateEntityManager<JobLogInfo> {
	
	@Autowired
	private JobLogDao dao;

	@Override
	protected IEntityDao<JobLogInfo> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	
}
