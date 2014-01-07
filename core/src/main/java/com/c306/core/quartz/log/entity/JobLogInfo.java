package com.c306.core.quartz.log.entity;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * JobLogInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "qrtz_log")
public class JobLogInfo implements java.io.Serializable {

	// Fields

	@GenericGenerator(name = "generator",
			strategy = "hilo",
			parameters = {
			/*@Parameter(name = "table", value = "hilo_sequence_qrtz_joblog"),*/
//			@Parameter(name = "max_lo", value = "20"), // 步长： TableHiLoGenerator使用
//			@Parameter( name = "increment_size", value = "20"), // 步长： MultipleHiLoPerTableGenerator使用
			@Parameter( name = "allocationSize", value = "20"), // 步长： TableGenerator使用，默认为50
			@Parameter( name = "initialValue", value = "20"),
//			@Parameter( name = "optimizer", value = "hilo"), // "hilo","pooled","pooled-lo",
			})
	@Id
	@GeneratedValue(generator = "generator", strategy = GenerationType.TABLE)
	@Column(name = "oid", unique = true, nullable = false, updatable = false)
	private long oid;
	@Column(name = "fire_ip", length = 100, updatable = false)
	private String fireIp;
	@Column(name = "fire_time", nullable = false, updatable = false)
	private long fireTime;
	@Column(name = "end_time")
	private long endTime;
	@Column(name = "job_name", length = 100, updatable = false)
	private String jobName;
	@Column(name = "job_group", length = 100, updatable = false)
	private String jobGroup;
	@Column(name = "job_data", length = 100, nullable = false)
	private String jobData;
	@Column(name = "trigger_info", length = 300, updatable = false)
	private String triggerInfo;
	@Column(name = "status", nullable = false)
	private int status;

	// trans

	public String getFireIp() {
		return fireIp;
	}

	public void setFireIp(String fireIp) {
		this.fireIp = fireIp;
	}

	@Transient
	transient public Date fromTime;
	@Transient
	transient public Date toTime;
	

	// Constructors

	/** default constructor */
	public JobLogInfo() {
	}

	/** minimal constructor */
	public JobLogInfo(long fireTime) {
		this.fireTime = fireTime;
	}

	/** full constructor */
	public JobLogInfo(String fireIp, long fireTime, long endTime, String jobName,
			String jobGroup, String triggerInfo, int status) {
		this.fireTime = fireTime;
		this.endTime = endTime;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.triggerInfo = triggerInfo;
		this.status = status;
		this.fireIp = fireIp;
	}

	// Property accessors
	public long getOid() {
		return this.oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getFireTime() {
		return this.fireTime;
	}

	public void setFireTime(long fireTime) {
		this.fireTime = fireTime;
	}

	public long getEndTime() {
		return this.endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getTriggerInfo() {
		return this.triggerInfo;
	}

	public void setTriggerInfo(String triggerInfo) {
		this.triggerInfo = triggerInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getJobData() {
		return jobData;
	}

	public void setJobData(String jobData) {
		this.jobData = jobData;
	}
	
	

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}
	
	@Transient
	public String getJobState() {
		return com.c306.core.quartz.log.plugin.LoggingJobHistoryPlugin.JobState.getName(this.status);
	}

}