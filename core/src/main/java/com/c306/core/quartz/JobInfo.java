package com.c306.core.quartz;

import java.util.Date;

import org.quartz.impl.JobDetailImpl;

public class JobInfo extends JobDetailImpl {
	private static final long serialVersionUID = -5300425204880706608L;

	private String jobClassName;
//	private JobDataInfo jobDataInfo;
	private boolean update;
	private Date scheduledFor;
	
	// job data map
	private String paramDatas;
	
	public boolean isDurability() {
		return this.isDurable();
	}
	
	// getter and setter

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

//	public JobDataInfo getJobDataInfo() {
//		return jobDataInfo;
//	}
//
//	public void setJobDataInfo(JobDataInfo jobDataInfo) {
//		this.jobDataInfo = jobDataInfo;
//	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public Date getScheduledFor() {
		return scheduledFor;
	}

	public void setScheduledFor(Date scheduledFor) {
		this.scheduledFor = scheduledFor;
	}

	public String getParamDatas() {
		return paramDatas;
	}

	public void setParamDatas(String paramDatas) {
		this.paramDatas = paramDatas;
	}

	// public String toString()
	// {
	// StringBuilder builder = new StringBuilder();
	//
	// builder.append("JobInfo [description=");
	// builder.append(this.description);
	// builder.append(", durable=");
	// builder.append(this.durable);
	// builder.append(", group=");
	// builder.append(this.group);
	// builder.append(", jobClassName=");
	// builder.append(this.jobClassName);
	// builder.append(", jobDataInfo=");
	// builder.append(this.jobDataInfo);
	// builder.append(", name=");
	// builder.append(this.name);
	// builder.append(", recoveryRequested=");
	// builder.append(this.recoveryRequested);
	// builder.append(", scheduledFor=");
	// builder.append(this.scheduledFor);
	// builder.append(", update=");
	// builder.append(this.update);
	// builder.append(", volatility=");
	// builder.append(this.volatility);
	// builder.append("]");
	//
	// return builder.toString();
	// }
}