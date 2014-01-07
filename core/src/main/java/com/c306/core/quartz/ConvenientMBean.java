package com.c306.core.quartz;

import javax.management.NotCompliantMBeanException;

import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerMBeanImpl;

public class ConvenientMBean extends QuartzSchedulerMBeanImpl {

	public ConvenientMBean(QuartzScheduler scheduler)
			throws NotCompliantMBeanException {
		super(scheduler);
	}

}
