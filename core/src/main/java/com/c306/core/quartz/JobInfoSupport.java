package com.c306.core.quartz;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

public class JobInfoSupport {
	
	
	public static JobDataMap newJobDataMap(String paramDatasStr) {
        JobDataMap jobDataMap = new JobDataMap();
        if(paramDatasStr != null && paramDatasStr.trim().length() > 0) {
            for (String param: paramDatasStr.split("&")) {
            	String[] kv = param.split("=");
                jobDataMap.put(kv[0].trim(), kv[1].trim());
            }
        }
        return jobDataMap;
	}
	
	public static String getJobDataForString(JobDataMap jobDataMap) {
		return getJobDataForString(jobDataMap, "&");
	}
	
	public static String getJobDataForString(JobDataMap jobDataMap, String separator) {
		if( jobDataMap == null )
			return null;
		String params = "";
		for ( String key: jobDataMap.getKeys() ) {
			params += separator + key + "=" + jobDataMap.getString(key);
		}
		return params.length()>0 ? params.substring(separator.length()) : "";
	}
	
	/**
	 * get trigger brief infomation
	 * @return
	 */
	public static String getTriggerInfo(Trigger trigger) {

    	StringBuilder triInfo = new StringBuilder(trigger.getKey().toString());
    	if(trigger instanceof CronTrigger) {
    		triInfo.append(", Cron: ").append(((CronTrigger)trigger).getCronExpression());
    	} else if(trigger instanceof SimpleTrigger) {
    		triInfo.append(", Interval: ").append(((SimpleTrigger)trigger).getRepeatInterval())
    				.append(", Count: ").append(((SimpleTrigger)trigger).getRepeatCount());
    	}
    	return triInfo.toString();
	}
	

}
