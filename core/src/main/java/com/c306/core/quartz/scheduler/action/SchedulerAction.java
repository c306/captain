package com.c306.core.quartz.scheduler.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.json.JSONUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerMBeanImpl;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.core.jmx.TriggerSupport;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.Constants;
import org.quartz.impl.matchers.GroupMatcher;

import com.c306.core.CoreConstants;
import com.c306.core.quartz.ConvenientMBean;
import com.c306.core.quartz.JobInfo;
import com.c306.core.quartz.JobInfoSupport;
import com.c306.core.quartz.JobStoreTX;
import com.c306.core.quartz.SchedulerInfo;
import com.c306.core.quartz.TriggerInfo;
import com.c306.utils.BeanPropertyAccessUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class SchedulerAction extends ActionSupport {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static Logger logger = Logger.getLogger(SchedulerAction.class);
	
	private static final String[] DATETIME_PATTERNS = new String[]{"yyyy-MM-dd'T'HH:mm:ss", CoreConstants.DATETIME_PATTERN_YMDHMS};
	
	private boolean success = false; //返回成功标记
	/**
	 * 待转为Json字符串的返回结果对象
	 * <result name="success" type="json">
	 *  <param name="ignoreHierarchy">fasle</param>
	 * 	<param name="excludeNullProperties">false</param>
	 * 	<param name="includeProperties">success,jsonMap</param>
	 * </result>
	 */
	private Map jsonMap = new LinkedMap(1);
	protected static ObjectMapper objectMapper = new ObjectMapper();
	
	
	
	/////////////// domain fields //////////////////////////////////
	private JobInfo jobInfo;
	private TriggerInfo triggerInfo;
	protected List<TriggerInfo> triggerInfos = new ArrayList<TriggerInfo>();
	
    private String keys;
    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    

	private SchedulerFactory getSchedulerFactory() {
		ServletContext ctx = (ServletContext) ActionContext.getContext().get(StrutsStatics.SERVLET_CONTEXT);
		return (StdSchedulerFactory) ctx.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
	}
	
	private StdScheduler getScheduler() throws SchedulerException {
		return (StdScheduler) getSchedulerFactory().getScheduler();
	}
	
	private QuartzSchedulerResources getQuartzSchedulerResources(Scheduler scheduler) throws SchedulerException, IllegalAccessException, NoSuchFieldException {
		QuartzScheduler qs = (QuartzScheduler) BeanPropertyAccessUtils.getDeclaredProperty(scheduler, "sched");
		QuartzSchedulerResources resources = (QuartzSchedulerResources) BeanPropertyAccessUtils.getDeclaredProperty(qs, "resources");
		return resources;
	}
	
	private JobStoreTX getJobStore(QuartzSchedulerResources resources) throws SchedulerException, IllegalAccessException, NoSuchFieldException {
//		com.c306.core.quartz.JobStoreTX
//		JobStoreSupport jobStore = (JobStoreSupport) resources.getJobStore();
		JobStoreTX jobStore = (JobStoreTX) resources.getJobStore();
		
		return jobStore;
	}
	
	private QuartzSchedulerMBeanImpl getQuartzSchedulerMBean(StdScheduler scheduler) throws Exception {
		
		try {
			QuartzScheduler qs = (QuartzScheduler) BeanPropertyAccessUtils.getDeclaredProperty(scheduler, "sched");
//			QuartzSchedulerResources resources = (QuartzSchedulerResources) BeanUtils.getDeclaredProperty(qs, "resources");
//			JobStoreSupport jobStore = (JobStoreSupport) resources.getJobStore();
			
			QuartzSchedulerMBeanImpl mbean = new ConvenientMBean(qs);
			
			return mbean;
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}
	
	
	/****************** scheduler instance *************************************/
	public String instanceList() {
		
		try {
			ArrayList<SchedulerInfo> schedulerList = new ArrayList<SchedulerInfo>();
			
			Collection<Scheduler> insList = getSchedulerFactory().getAllSchedulers();
			for(Scheduler scheduler: insList) {
				QuartzSchedulerResources qsres = this.getQuartzSchedulerResources(scheduler);
				SchedulerInfo schedulerInfo = new SchedulerInfo();
				schedulerInfo.setSchedulerName(qsres.getName());
				schedulerInfo.setSchedulerInstanceId(qsres.getInstanceId());
				schedulerInfo.setJobStoreClassName(qsres.getJobStore().getClass().getName());
//				if( qsres.getJobStore() instanceof org.quartz.simpl.SimpleInstanceIdGenerator )
				schedulerInfo.setNode(qsres.getInstanceId());
				schedulerInfo.setStandbyMode(scheduler.isInStandbyMode());
				schedulerInfo.setStarted(scheduler.isStarted());
				schedulerInfo.setThreadPoolClassName(qsres.getThreadPool().getClass().getName());
				schedulerInfo.setThreadPoolSize(qsres.getThreadPool().getPoolSize());
//				schedulerInfo.setType(type);
				String version = String.format("%s.%s.%s", QuartzScheduler.getVersionMajor(), QuartzScheduler.getVersionMinor(),
                        QuartzScheduler.getVersionIteration());
				schedulerInfo.setVersion(version);

				logger.info(schedulerInfo);
				schedulerList.add(schedulerInfo);
			}
			
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
            request.setAttribute("schedulerList", schedulerList);
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return "instanceList";
	}
	
	public String pauseInstance() {
		
		try {

			logger.warn("pauseInstance keys: " + keys);
			for( String key : keys.split(",") ) {
				Scheduler scheduler = this.getSchedulerFactory().getScheduler(key);
				scheduler.standby();
				logger.warn("standby scheduler: " + key);
			}

			logger.warn("pauseInstance keys complete. ");
			this.success = true;
			
		} catch (Exception e) {
			logger.error("", e);
//			this.addActionMessage("System Error: "+e.getMessage());
			this.jsonMap.put("msg", e.getMessage());
		}
		
//		return this.instanceList();
		return SUCCESS;
	}
	
	public String resumeInstance() {
		
		try {

			logger.warn("resumeInstance keys: " + keys);
			for( String key : keys.split(",") ) {
				Scheduler scheduler = this.getSchedulerFactory().getScheduler(key);
				scheduler.start();
				logger.warn("restart scheduler: " + key);
			}

			logger.warn("resumeInstance keys complete. ");
			this.success = true;
			
		} catch (Exception e) {
			logger.error("", e);
//			this.addActionMessage("System Error: "+e.getMessage());
			this.jsonMap.put("msg", e.getMessage());
		}
		
//		return "redirect:/quartz/scheduler!pauseInstance.action";
//		return this.instanceList();
		return SUCCESS;
	}
	
	
	/****************** job *************************************/
	
	public String jobList() {
		
		try {
			
			StdScheduler scheduler = this.getScheduler();

			List<String> jobGroupNames = scheduler.getJobGroupNames();
            List<JobDetail> jobDetailList = new ArrayList<JobDetail>();
            Map<JobKey, Object> triggersMap = new HashMap<JobKey, Object>();
            Map<TriggerKey, String> triggersJsonMap = new HashMap<TriggerKey, String>();
            for (String jobGroupName : jobGroupNames) {
            	//filter by jobGroup
            	if( StringUtils.isNotBlank(this.jobGroup) && !StringUtils.containsIgnoreCase(jobGroupName, this.jobGroup.trim()) )
            		continue;
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName))) {
                	//filter by jobName
                	if( StringUtils.isNotBlank(this.jobName) && !StringUtils.containsIgnoreCase(jobKey.getName(), this.jobName.trim()) )
                		continue;
                	
                	jobDetailList.add(scheduler.getJobDetail(jobKey));
        			List<? extends Trigger> triggerList = scheduler.getTriggersOfJob(jobKey);
        			triggersMap.put(jobKey, triggerList);
        			converTrigger2Json(triggersJsonMap, triggerList);
                }
            }
            
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
            request.setAttribute("jobGroupNames", jobGroupNames);
            request.setAttribute("jobDetailList", jobDetailList);
            request.setAttribute("triggersMap", triggersMap);
            request.setAttribute("triggersJsonMap", triggersJsonMap);
            
            Set<TriggerKey> pausedTrigSet = this.getJobStore(getQuartzSchedulerResources(scheduler)).selectPausedTriggers();
            request.setAttribute("pausedTrigSet", pausedTrigSet);// paused triggers
            
//			this.page = new Page(detailList.size(), 1, detailList.size());
//			this.page.setDatas(detailList);
			
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return "jobList";
	}
	
	private void converTrigger2Json(Map<TriggerKey, String> triggersJsonMap, List<? extends Trigger> triggerList) {
		for( Trigger triggerInfo: triggerList) {
			Map<String, Object> attrMap = new HashMap<String, Object>();
			
			if( triggerInfo instanceof SimpleTrigger ) {
				attrMap.put("repeatCount", ((SimpleTrigger)triggerInfo).getRepeatCount());
				attrMap.put("repeatInterval", ((SimpleTrigger)triggerInfo).getRepeatInterval() / 1000);
			} else if( triggerInfo instanceof CronTrigger ) {

				attrMap.put("cronExpression", ((CronTrigger)triggerInfo).getCronExpression().trim());
				attrMap.put("timeZone", ((CronTrigger)triggerInfo).getTimeZone().getID());
			}
			
			attrMap.put("description", triggerInfo.getDescription());
//			attrMap.put("calendarName", triggerInfo.getCalendarName());
			attrMap.put("priority", triggerInfo.getPriority());
			attrMap.put("startTime", triggerInfo.getStartTime());
			attrMap.put("endTime", triggerInfo.getEndTime());
			attrMap.put("misfireInstruction", triggerInfo.getMisfireInstruction());
			
			attrMap.put("name", triggerInfo.getKey().getName());
			attrMap.put("group", triggerInfo.getKey().getGroup());
			attrMap.put("jobName", triggerInfo.getJobKey().getName());
			attrMap.put("jobGroup", triggerInfo.getJobKey().getGroup());
//			attrMap.put("jobDataMap", triggerInfo.getJobDataMap());
			attrMap.put("paramDatas", JobInfoSupport.getJobDataForString(triggerInfo.getJobDataMap()));
			
			logger.info("newTrigger: " + attrMap);
			
			try {
//				triggersJsonMap.put(trig.getKey(), objectMapper.writeValueAsString(trig));
				//org.apache.struts2.json.JSONResult
				ArrayList<Pattern> excludeProperties = null;
//		        List<String> excludePatterns = JSONUtil.asList("");//triggerBuilder,scheduleBuilder,jobKey,key,timeZone
		        Set<String> excludePatterns = JSONUtil.asSet("");//triggerBuilder,scheduleBuilder,jobKey,key,timeZone
		        if (excludePatterns != null) {
		        	excludeProperties = new ArrayList<Pattern>(excludePatterns.size());
		            for (String pattern : excludePatterns) {
		                excludeProperties.add(Pattern.compile(pattern));
		            }
		        }
				String json = JSONUtil.serialize(attrMap, excludeProperties, null, false, false, true);
				triggersJsonMap.put(triggerInfo.getKey(), json);
				
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
	
	public String jobEdit() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		
		try {
			JobDetail jobDetail;
			if( StringUtils.isNotBlank(this.jobName) && StringUtils.isNotBlank(this.jobGroup) ) {
				jobDetail = this.getScheduler().getJobDetail(new JobKey(this.jobName, this.jobGroup));
				if( jobDetail == null )
					throw new Exception("Job not defound!");
	            request.setAttribute("isUpdateJobDetail", true);
	            request.setAttribute("paramDatas", JobInfoSupport.getJobDataForString(jobDetail.getJobDataMap()));
			} else {
				jobDetail = new JobDetailImpl();
			}
			
            request.setAttribute("jobDetail", jobDetail);
			
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return "jobEdit";
	}
	
	/**
	 * save or update
	 * @return
	 */
	public String saveJob() {
		
		try {

			// set job class
	        Class<?> jobClass = Class.forName(this.jobInfo.getJobClassName());
	        Class<? extends Job> jobClassTyped = (Class<? extends Job>)jobClass;
	        this.jobInfo.setJobClass(jobClassTyped);
	        
	        // set job data map
//	        jobDetail.setJobDataMap(JobDataMapSupport.newJobDataMap((TabularData) cData.get(ITEM_NAMES[i++])));
	        this.jobInfo.setJobDataMap(JobInfoSupport.newJobDataMap(this.jobInfo.getParamDatas()));
	        
	        // 必需是durability
	        this.jobInfo.setDurability(true);
	        
	        // save
			getScheduler().addJob(this.jobInfo, this.jobInfo.isUpdate());
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return jobList();
	}
	
	public String pauseJobs() {
		
		try {
			logger.warn("pauseJobs key: " + this.keys);

			List<JobKey> jobKeyList = generateJobKeys(this.keys);
			for( JobKey jobKey: jobKeyList ) {
				this.getScheduler().pauseJob(jobKey);
				logger.warn("pauseJob successfully. jobKey: " + jobKey);
			}
			logger.warn("pauseJobs successfully. jobKeyList.size(): " + jobKeyList.size());
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return this.jobList();
	}
	
	public String resumeJobs() {
		
		try {
			logger.warn("resumeJobs key: " + this.keys);

			List<JobKey> jobKeyList = generateJobKeys(this.keys);
			for( JobKey jobKey: jobKeyList ) {
				this.getScheduler().resumeJob(jobKey);
				logger.warn("resumeJob successfully. jobKey: " + jobKey);
			}
			logger.warn("resumeJobs successfully. jobKeyList.size(): " + jobKeyList.size());
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return this.jobList();
	}
	
	public String deleteJobs() {
		
		try {
			logger.warn("deleteJobs key: " + this.keys);
			
			List<JobKey> jobKeyList = generateJobKeys(this.keys);
			if( jobKeyList.size() > 0 ) {
				// Jobs can be removed from the scheduler by using the 'Delete' operation.
				// This causes the scheduler to remove the job and all of its triggers. Currently running jobs are not affected.
//				boolean b = this.getScheduler().deleteJob(new JobKey(this.jobName, this.jobGroup));
				boolean b = this.getScheduler().deleteJobs(jobKeyList);
				if( !b ) {
					throw new Exception("Delete jobs failed.");
				}
				logger.warn("deleteJobs successfully. jobKeyList: " + jobKeyList);
			}
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.deletesuccess", "删除成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return this.jobList();
	}
	
	/**
	 * 立即触发一次执行任务。
	 * After submitting this request, the remote Quartz Scheduler will
	 * create a one-time system trigger to execute the job immediately.
	 * The new job will run as soon as possible. This system trigger will 
	 * appear in the Triggers panel for the selected job. However, this 
	 * Trigger will not persist beyond its first and only invocation. Within a 
	 * short time, that trigger will disappear from the list.
	 * @return
	 */
	public String triggerJobs() {
		
		try {
			logger.warn("triggerJobs key: " + this.keys);
			
			JobDataMap jobdata = JobInfoSupport.newJobDataMap("ManuallyTriggered=" + new Date());
			
			List<JobKey> jobKeyList = generateJobKeys(this.keys);
			for( JobKey jobKey: jobKeyList ) {
				// 立即执行
				this.getScheduler().triggerJob(jobKey, jobdata);
				logger.warn("triggerJob successfully. jobKey: " + jobKey);
			}
			logger.warn("triggerJobs successfully. jobKeyList: " + jobKeyList);
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.addActionMessage("System Error: "+e.getMessage());
		}
		
		return this.jobList();
	}
	

	
	/****************** trigger *************************************/
	
	public String triggerList() {
		
		try {
			
			StdScheduler scheduler = this.getScheduler();

            List<Trigger> triggerList = new ArrayList<Trigger>();
            List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
            for (String triggerGroupName : triggerGroupNames) {
            	//filter by triggerGroup
            	if( StringUtils.isNotBlank(this.triggerGroup) && !StringUtils.containsIgnoreCase(triggerGroupName, this.triggerGroup.trim()) )
            		continue;
                for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupName))) {
                	//filter by triggerName
                	if( StringUtils.isNotBlank(this.triggerName) && !StringUtils.containsIgnoreCase(triggerKey.getName(), this.triggerName.trim()) )
                		continue;
                    triggerList.add(scheduler.getTrigger(triggerKey));
                }
            }
            
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
            request.setAttribute("triggerGroupNames", triggerGroupNames);
            request.setAttribute("triggerList", triggerList);
            
            // get triggers state
            Map<String, String> trigStateSet = this.getJobStore(this.getQuartzSchedulerResources(scheduler)).selectTriggersState(triggerList);
            request.setAttribute("trigStateSet", trigStateSet);
			
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return "triggerList";
	}
	
	/**
	 * fetch triggers by jobKey
	 * @return
	 */
	public String fetchTriggers() {
		
		try {
			
			List<? extends Trigger> triggerList = this.getScheduler().getTriggersOfJob(new JobKey(this.jobName, this.jobGroup));

            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
            request.setAttribute("triggerList", triggerList);
			
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return "fetchTriggers";
	}
	
	/**
	 * ajax request.
	 * save or update.
	 * needs params: jobName, jobGroup, triggerInfos
	 * @return
	 */
	public String schedulerTrigger() {
		
		try {
			
			if( StringUtils.isBlank(this.triggerInfo.getJobName()) || StringUtils.isBlank(this.triggerInfo.getJobGroup()) ) {
				// error
				throw new Exception("jobName or jobGroup must be not empty.");
			}
			StdScheduler scheduler = this.getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail(new JobKey(this.triggerInfo.getJobName(), this.triggerInfo.getJobGroup()));
			// convert triggers
	        HashSet<Trigger> triggersForJob = convertTriggers(this.triggerInfo);
//	        HashSet<Trigger> triggersForJob = convertTriggers(this.triggerInfos); // if trigger list
			// schedule job for some triggers
			scheduler.scheduleJob(jobDetail, triggersForJob, true);

			this.success = true;
			this.jsonMap.put("triggerInfo", this.triggerInfo);
			//this.jsonMap.put("msg", getText("global.hintinfo.operatesuccess", "操作成功！"));
		} catch (Exception e) {
			logger.error("", e);
			this.jsonMap.put("msg", "System Error: "+e.getMessage());
		}
		
		return "success";
	}
	
	private static HashSet<Trigger> convertTriggers(TriggerInfo... triggerInfos) throws Exception {
		HashSet<Trigger> triggersForJob = new HashSet<Trigger>();
		
		for( TriggerInfo triggerInfo : triggerInfos ) {

			Map<String, Object> attrMap = new HashMap<String, Object>();
			
			if( Constants.TTYPE_CRON.equals(triggerInfo.getTriggerType()) )
				attrMap.put("cronExpression", triggerInfo.getCronExpression().trim());
			attrMap.put("timeZone", triggerInfo.getTimeZone().trim());

			if( triggerInfo.getRepeatCount() == 0 )
				triggerInfo.setRepeatCount(-1);
			attrMap.put("repeatCount", triggerInfo.getRepeatCount());
			attrMap.put("repeatInterval", triggerInfo.getRepeatInterval() * 1000);
			
			attrMap.put("description", triggerInfo.getDescription());
//			attrMap.put("calendarName", triggerInfo.getCalendarName());
			attrMap.put("priority", triggerInfo.getPriority());
			if( StringUtils.isNotBlank(triggerInfo.getStartTime()))
				attrMap.put("startTime", DateUtils.parseDate(triggerInfo.getStartTime(), DATETIME_PATTERNS));
			if( StringUtils.isNotBlank(triggerInfo.getEndTime()))
				attrMap.put("endTime", DateUtils.parseDate(triggerInfo.getEndTime(), DATETIME_PATTERNS));
			attrMap.put("misfireInstruction", triggerInfo.getMisfireInstruction());
			
			attrMap.put("name", triggerInfo.getName().trim());
			attrMap.put("group", triggerInfo.getGroup().trim());
			attrMap.put("jobName", triggerInfo.getJobName());
			attrMap.put("jobGroup", triggerInfo.getJobGroup());
			attrMap.put("jobDataMap", JobInfoSupport.newJobDataMap(triggerInfo.getParamDatas()));
			
			logger.info("newTrigger: " + attrMap);
			
			triggersForJob.add(TriggerSupport.newTrigger(attrMap));
		}
		
		return triggersForJob;
	}
	
	public String deleteTriggers() {
		
		try {
//			QuartzSchedulerMBeanImpl mbean = getQuartzSchedulerMBean();
//			mbean.getAllJobDetails();
			StdScheduler scheduler = this.getScheduler();
			logger.warn("deleteTriggers key: " + this.keys);
			
			List<TriggerKey> triggerKeyList = generateTriggerKeys(this.keys);
			if( triggerKeyList.size() == 0 && StringUtils.isNotBlank(this.triggerName) && StringUtils.isNotBlank(this.triggerGroup)) {
				triggerKeyList.add(new TriggerKey(this.triggerName, this.triggerGroup));
			}

			if( triggerKeyList.size() > 0 ) {
				//
				boolean b = scheduler.unscheduleJobs(triggerKeyList);
				if( !b ) {
					throw new Exception("Delete triggers failed.");
				}
			}
			logger.warn("deleteTriggers successfully. triggerKeyList: " + triggerKeyList);
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.deletesuccess", "删除成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.jsonMap.put("msg", "System Error: "+e.getMessage());
		}
		
		return "success";
	}
	
	public String pauseTriggers() {
		
		try {
			StdScheduler scheduler = this.getScheduler();
			logger.warn("pauseTriggers key: " + this.keys);
			
			List<TriggerKey> triggerKeyList = generateTriggerKeys(this.keys);
			for( TriggerKey triggerKey: triggerKeyList) {
				//
				scheduler.pauseTrigger(triggerKey);
				logger.warn("pauseTrigger successfully. triggerKey: " + triggerKey);
			}
			logger.warn("pauseTriggers successfully. triggerKeyList: " + triggerKeyList);
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.jsonMap.put("msg", "System Error: "+e.getMessage());
		}
		
		return "success";
	}
	
	public String resumeTriggers() {
		
		try {
			StdScheduler scheduler = this.getScheduler();
			logger.warn("resumeTriggers key: " + this.keys);
			
			List<TriggerKey> triggerKeyList = generateTriggerKeys(this.keys);
			for( TriggerKey triggerKey: triggerKeyList) {
				//
				scheduler.resumeTrigger(triggerKey);
				logger.warn("resumeTrigger successfully. triggerKey: " + triggerKey);
			}
			logger.warn("resumeTriggers successfully. triggerKeyList: " + triggerKeyList);
			
			this.success = true;
			this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
			
		} catch (Exception e) {
			logger.error("", e);
			this.jsonMap.put("msg", "System Error: "+e.getMessage());
		}
		
		return "success";
	}
	
	private static List<JobKey> generateJobKeys(String keys) {

		ArrayList<JobKey> jobKeyList = new ArrayList<JobKey>();
		if( StringUtils.isNotBlank(keys) ) {
			for( String jobk : keys.split(",") ) {
				String[] gn = jobk.split("/");
				if( gn.length == 2 )
					jobKeyList.add(new JobKey(gn[1], gn[0]));
			}
		}
		return jobKeyList;
	}
	
	private static List<TriggerKey> generateTriggerKeys(String keys) {
		
		ArrayList<TriggerKey> triggerKeyList = new ArrayList<TriggerKey>();
		if( StringUtils.isNotBlank(keys) ) {
			for( String jobk : keys.split(",") ) {
				String[] gn = jobk.split("/");
				if( gn.length == 2 )
					triggerKeyList.add(new TriggerKey(gn[1], gn[0]));
			}
		}
		return triggerKeyList;
	}

	public JobInfo getJobInfo() {
		return jobInfo;
	}

	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	public TriggerInfo getTriggerInfo() {
		return triggerInfo;
	}

	public void setTriggerInfo(TriggerInfo triggerInfo) {
		this.triggerInfo = triggerInfo;
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

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public List<TriggerInfo> getTriggerInfos() {
		return triggerInfos;
	}

	public void setTriggerInfos(List<TriggerInfo> triggerInfos) {
		this.triggerInfos = triggerInfos;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

}
