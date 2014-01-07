package com.c306.core.quartz.log.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.core.quartz.log.entity.JobLogInfo;
import com.c306.core.quartz.log.service.JobLogManager;

public class JobLogAction extends BaseGenericActionSupport<JobLogInfo> {

	private static final long serialVersionUID = 1L;
	@Autowired 
	private JobLogManager service;
	
	@Override
	protected JobLogManager getService() {
		return service;
	}

}