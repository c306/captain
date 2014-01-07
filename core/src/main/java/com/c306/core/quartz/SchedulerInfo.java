package com.c306.core.quartz;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SchedulerInfo implements Serializable {
	private static final long serialVersionUID = 0L;
	
	
	private Map<String, List<JobInfo>> jobs;
	private String jobStoreClassName;
	private String node;
	// private List<NotificationInfo> notifications;
	private String schedulerInstanceId;
	private String schedulerName;
	private boolean standbyMode;
	private boolean started;
	private String threadPoolClassName;
	private int threadPoolSize;
	private Map<String, List<TriggerInfo>> triggers;
	private String type;
	private String version;

	public Map<String, List<JobInfo>> getJobs() {
		return this.jobs;
	}

	public String getJobStoreClassName() {
		return this.jobStoreClassName;
	}

	public String getNode() {
		return this.node;
	}

	public String getSchedulerInstanceId() {
		return this.schedulerInstanceId;
	}

	public String getSchedulerName() {
		return this.schedulerName;
	}

	public String getThreadPoolClassName() {
		return this.threadPoolClassName;
	}

	public int getThreadPoolSize() {
		return this.threadPoolSize;
	}

	public Map<String, List<TriggerInfo>> getTriggers() {
		return this.triggers;
	}

	public String getType() {
		return this.type;
	}

	public String getVersion() {
		return this.version;
	}

	public boolean isStandbyMode() {
		return this.standbyMode;
	}

	public boolean isStarted() {
		return this.started;
	}

	public void setJobs(Map<String, List<JobInfo>> jobs) {
		this.jobs = jobs;
	}

	public void setJobStoreClassName(String jobStoreClassName) {
		this.jobStoreClassName = jobStoreClassName;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setSchedulerInstanceId(String schedulerInstanceId) {
		this.schedulerInstanceId = schedulerInstanceId;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	public void setStandbyMode(boolean standbyMode) {
		this.standbyMode = standbyMode;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void setThreadPoolClassName(String threadPoolClassName) {
		this.threadPoolClassName = threadPoolClassName;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setTriggers(Map<String, List<TriggerInfo>> triggers) {
		this.triggers = triggers;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("SchedulerInfo [");
		builder.append("jobs=");
		builder.append(this.jobs);
		builder.append(", jobStoreClassName=");
		builder.append(this.jobStoreClassName);
		builder.append(", node=");
		builder.append(this.node);
//		builder.append(", notifications=");
		// builder.append(this.notifications);
		builder.append(", schedulerInstanceId=");
		builder.append(this.schedulerInstanceId);
		builder.append(", schedulerName=");
		builder.append(this.schedulerName);
		builder.append(", standbyMode=");
		builder.append(this.standbyMode);
		builder.append(", started=");
		builder.append(this.started);
		builder.append(", threadPoolClassName=");
		builder.append(this.threadPoolClassName);
		builder.append(", threadPoolSize=");
		builder.append(this.threadPoolSize);
		builder.append(", triggers=");
		builder.append(this.triggers);
		builder.append(", type=");
		builder.append(this.type);
		builder.append(", version=");
		builder.append(this.version);
		builder.append("]");

		return builder.toString();
	}

}