package com.c306.core.base.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.annotations.JSON;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.Assert;

import com.c306.core.base.service.IGenericManager;
import com.c306.core.springsecurity.users.WebUserConstants;
import com.c306.core.springsecurity.users.entity.SecUser;
import com.c306.core.support.QueryParameters;
import com.c306.utils.GenericsUtils;

@SuppressWarnings("serial")
public abstract class BaseGenericActionSupport<T> extends BaseActionSupport {

	protected static final String LIST_RESULT = "list";// 列表result

	protected static final String EDIT_RESULT = "edit";// 编辑页面result

	protected static final String DELETED = "deleted";

	protected static final String AJAX = "ajax";
	
	protected static final String JSON = "json";
	
	protected static final String JSON_EDIT = "jsonEdit";

	/**
	 * 多条业务对象的主键值(一般用,分开)
	 */
	protected String keys;
	//作用同keys
	protected List<String> oids = new ArrayList<String>();

	protected int pageStyle;
	
	protected List items;

	private String oid;
	
	/**
	 * 待转为Json字符串的返回结果对象
	 * <result name="success" type="json">
	 *  <param name="ignoreHierarchy">fasle</param>
	 * 	<param name="excludeNullProperties">false</param>
	 * 	<param name="includeProperties">success,jsonMap</param>
	 * </result>
	 */
	protected Map jsonMap = new LinkedMap(1);
	protected static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 业务对象
	 */
	public T domain;
	/**
	 * 页面查询条件Dto
	 */
	public QueryParameters query = new QueryParameters();

	
	/**
	 * 获得Service类，必须在子类实现
	 */
	abstract protected IGenericManager<T> getService();

	/**
	 * 构造
	 */
	public BaseGenericActionSupport() {
		try {
			setDomain(getDomainClass().newInstance());
//			setQuery(getDomainClass().newInstance());
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 是否中国Local
	 * @return
	 */
	public boolean getIscn(){
		logger.debug("super.getLocale() " + super.getLocale().toString());
		return super.getLocale().toString().equals(Locale.CHINA.toString());
	}
	
	/**
	 * 当前登录用户
	 * @return
	 */
	public SecUser getLoginUser(){
		SecUser user = (SecUser) super.getSession().get(WebUserConstants.LOGINUSER );
		return user;
	}

	/**
	 * 得到业务对象 需要在业务controller中实现
	 * 
	 * @return
	 */
	@JSON
	public T getDomain() {
		return this.domain;
	}

	/**
	 * @param domain
	 */
	public void setDomain(T domain) {
		this.domain = domain;
	}

	public QueryParameters getQuery() {
		return query;
	}

	public void setQuery(QueryParameters query) {
		this.query = query;
	}

	/**
	 * 业务对象类型
	 */
	// protected Class<T> domainClass;
	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		return GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 得到一个指定ID的业务对象
	 */
	public T get() throws Exception {
		domain = (T) getService().get(oid);
		return domain;
	}

	/**
	 * 通用保存方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveOrUpdate() throws Exception {
		domain = getService().saveOrUpdate(domain);
		setSuccess(true);
		//this.addActionMessage(getText("global.hintinfo.savesuccess", "保存成功！"));
		this.addActionMessage(getText("global.hintinfo.operatesuccess", "操作成功！"));
		return getDefaultResult() == null ? list() : getDefaultResult();
	}
	
	public String ajaxSaveOrUpdate() throws Exception {
		try {
			saveOrUpdate();
		} catch (Exception e) {
			logger.error("保存错误", e);
		}
		return SUCCESS;
	}

	/**
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {

		return edit();
	}

	/**
	 * 通用的编辑方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String edit() throws Exception {
		if (!GenericValidator.isBlankOrNull(oid)) {
			get();
		} else {
			if(domain == null)
				domain = getDomainClass().newInstance();
		}
		return EDIT_RESULT;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String ajaxEdit() {
		try {
			if (oid != null) {
				get();
				if(this.domain != null)
					setSuccess(true);
			}
		} catch (Exception e) {
			logger.error("err.", e);
		}
		return JSON_EDIT;
	}

	/**
	 * 批量逻辑删除
	 * keys或oids存储主键值
	 * @return
	 */
	public String move2trash() throws Exception {
		if (!GenericValidator.isBlankOrNull(keys)) {
			getService().move2trash(StringUtils.split(keys, ","));
			this.addActionMessage(getText("global.hintinfo.deletesuccess", "删除成功！"));
			this.setSuccess(true);
		} else if (this.oids.size() > 0) {
			getService().move2trash(oids.toArray(new String[oids.size()]));
			this.addActionMessage(this.getText("global.hintinfo.deletesuccess", "删除成功！"));
			this.setSuccess(true);
		} else {
			this.addActionMessage(this.getText("global.hintinfo.noselectrecord", "您没有选择任何记录！"));
		}
		return list();
	}
	
	/**
	 * 批量删除
	 * keys或oids存储主键值
	 * @return
	 */
	public String delete() throws Exception {
		if (!GenericValidator.isBlankOrNull(keys)) {
			getService().removeByIds(StringUtils.split(keys, ","));
			this.addActionMessage(getText("global.hintinfo.deletesuccess", "删除成功！"));
			this.setSuccess(true);
		} else if (this.oids.size() > 0) {
			getService().removeByIds(oids);
			this.addActionMessage(this.getText("global.hintinfo.deletesuccess", "删除成功！"));
			this.setSuccess(true);
		} else {
			this.addActionMessage(this.getText("global.hintinfo.noselectrecord", "您没有选择任何记录！"));
		}
		return list();
	}
	
	public String ajaxDelete() throws Exception {
		try {
			delete();
		} catch (Exception e) {
			logger.error("err.", e);
		}
		return SUCCESS;
	}

	/**
	 * 列表使用的action
	 */
	@SkipValidation
	public String list() throws Exception {
		try {
//			this.page = this.getService().pagedQuery(getDomain(), getPageNo(), getPageSize(), getSort(), "ASC".equals(getDir()));
			this.page = this.getService().pagedQuery(getQuery());
		} catch (Exception e) {
			logger.error("err.", e);
			throw e;
		}
		return LIST_RESULT;
	}
	
	public String ajaxList() throws Exception {
		try {
			list();
			super.setSuccess(true);
		} catch (Exception e) {
			logger.error("err.", e);
			throw e;
		}
		return LIST_RESULT;
	}

	/**
	 * 根据异常信息KEY从资源文件中取得对应的业务异常说明
	 * 
	 * @param errorMessageKey
	 */
	protected void addErrorByKey(String[] errorMessageKey) {
		Assert.notNull(errorMessageKey);
		for (int i = 0; i < errorMessageKey.length; i++) {
			addActionError(getText(errorMessageKey[i]));
		}
	}

	// // 获得当前登录者信息
	// protected Loginer getLoginer() throws Exception {
	// if (getSession().get(Loginer.LOGININFO_SESSION) != null)
	// return (Loginer) getSession().get(Loginer.LOGININFO_SESSION);
	// else
	// throw new Exception(getText("RESOURCE.HINTINFO.NOLOGIN"));
	//
	// }

	// /**
	// * 无权限提示方法(若用户未登录，则提示请先登录)
	// *
	// * @throws Exception
	// * ;
	// */
	// protected String NoPermissionHint() throws Exception {
	// // 若用户为空，提示未登录
	// if (getLoginer() == null)
	// throw new Exception(getText("RESOURCE.HINTINFO.NOLOGIN"));
	// throw new Exception(getText("RESOURCE.HINTINFO.NOPERMISSION"));
	// }

	/*
	 * 默认接收所有参数
	 * 
	 * @see
	 * com.opensymphony.xwork.interceptor.ParameterNameAware#acceptableParameterName
	 * (java.lang.String)
	 */
	public boolean acceptableParameterName(String arg0) {
		return true;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public int getPageStyle() {
		return pageStyle;
	}

	public void setPageStyle(int pageStyle) {
		this.pageStyle = pageStyle;
	}

	public List<String> getOids() {
		return oids;
	}

	public void setOids(List<String> oids) {
		this.oids = oids;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

}
