package com.c306.core.quartz;

import java.io.Serializable;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class TriggerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /*
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *
    * AbstractTrigger Data members.
    *
    * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    */

    private String name;

    private String group = Scheduler.DEFAULT_GROUP;

    private String jobName;

    private String jobGroup = Scheduler.DEFAULT_GROUP;

    private String description;

    private JobDataMap jobDataMap;
	// job data map
	private String paramDatas;

    private boolean volatility = false; // still here for serialization backward compatibility

    private String calendarName = null;

    private String fireInstanceId = null;

    private int misfireInstruction = Trigger.MISFIRE_INSTRUCTION_SMART_POLICY;

    private int priority = Trigger.DEFAULT_PRIORITY;

    private transient TriggerKey key = null;
    
    
	// Cron Trigger
    private String cronExpression = null;
    private String startTime = null;
    private String endTime = null;
    private String nextFireTime = null;
    private String previousFireTime = null;
    private transient String timeZone = null;
    
    
    // Simple Trigger
//    private Date startTime = null;
//    private Date endTime = null;
//    private Date nextFireTime = null;
//    private Date previousFireTime = null;
    private int repeatCount = 0;
    private long repeatInterval = 0;
    private int timesTriggered = 0;
    private boolean complete = false;
    
    // .Constants.TTYPE_SIMPLE .Constants.TTYPE_CRON
    private String triggerType;
    private boolean update;
    
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public JobDataMap getJobDataMap() {
		return jobDataMap;
	}
	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}
	public boolean isVolatility() {
		return volatility;
	}
	public void setVolatility(boolean volatility) {
		this.volatility = volatility;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getFireInstanceId() {
		return fireInstanceId;
	}
	public void setFireInstanceId(String fireInstanceId) {
		this.fireInstanceId = fireInstanceId;
	}
	public int getMisfireInstruction() {
		return misfireInstruction;
	}
	public void setMisfireInstruction(int misfireInstruction) {
		this.misfireInstruction = misfireInstruction;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public TriggerKey getKey() {
		return key;
	}
	public void setKey(TriggerKey key) {
		this.key = key;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public String getPreviousFireTime() {
		return previousFireTime;
	}
	public void setPreviousFireTime(String previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public int getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}
	public long getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public int getTimesTriggered() {
		return timesTriggered;
	}
	public void setTimesTriggered(int timesTriggered) {
		this.timesTriggered = timesTriggered;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public String getParamDatas() {
		return paramDatas;
	}
	public void setParamDatas(String paramDatas) {
		this.paramDatas = paramDatas;
	}

    

}
