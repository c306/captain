package com.c306.core.base.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.json.annotations.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.c306.core.support.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * 
 * @BaseGenericActionSupport
 * 
 * @author chens
 * 
 */
public class BaseActionSupport extends ActionSupport {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = 7789982561394923307L;

	protected static final String PRIMARY_KEY = "oid"; // 持久类主键对应的页面参数名称

	private ActionContext context; // Webwork的ActionContext

	private String defaultResult; // Action默认的result，即默认execute的返回值
	
	private boolean success = false; //返回成功标记
	
	/**
	 * 分页信息
	 */
	protected Page page = null;
	
	
	@Deprecated protected int pageNo;
	@Deprecated protected int pageSize = 0;
	private String sort=null; //排序用
	private String dir="ASC"; //排序用
	

	/**
	 * 获得Webwork的ActionContext
	 */
	public ActionContext getActionContext() {
		return context == null ? context = ActionContext.getContext() : context;
	}

	public String getDefaultResult() {
		return defaultResult;
	}

	public void setDefaultResult(String defaultResult) {
		this.defaultResult = defaultResult;
	}

	/**
	 * 获得当前Action的名称
	 */
	public String getActionName() {
		return getActionContext().getName();
	}

	/**
	 * 获得当前session
	 */
	public java.util.Map<String, Object> getSession() {
		return getActionContext().getSession();
	}
	
	/**
	 * 获得当前Http Servlet Request
	 */
	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getActionContext().get(StrutsStatics.HTTP_REQUEST);
	}
	
	/**
	 * 获得当前Http Servlet Response
	 * @return
	 */
	public HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) getActionContext().get(StrutsStatics.HTTP_RESPONSE);
	}

	/**
	 * 如果设定了defaultResult则返回defaultResult
	 */
	public String execute() throws Exception {
		return defaultResult == null ? super.execute() : defaultResult;
	}

	/**
	 * 根据指定的页面参数名称，获取页面传递来的参数值
	 * 
	 * @param parameter
	 * @return 单个对象
	 */
	protected Object getParameterValue(String parameter) {
		Object[] parameterArray = getParamenterArray(parameter);
		if (parameterArray != null && parameterArray.length == 1) {
			return parameterArray[0];
		} else {
			return null;
		}
	}
	
	/**
	 * 根据指定的页面参数名称，获取页面传递来的参数值
	 * 
	 * @param parameter
	 * @return 数组对象
	 */
	protected Object[] getParamenterArray(String parameter) {
		return (Object[]) (getActionContext().getParameters().get(parameter));
	}

	public boolean isSuccess() {
		return success;
	}
	@JSON
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@JSON
	public Page getPage() {
		return page;
	}

	protected void setPage(Page page) {
		this.page = page;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		if(this.pageSize == 0)
			return 20;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	
}