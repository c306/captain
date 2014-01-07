package com.c306.comm.sysargs.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.comm.sysargs.SysArgsUtil;
import com.c306.comm.sysargs.entity.SystemArguments;
import com.c306.comm.sysargs.service.SystemArgumentsManager;
import com.c306.core.base.action.BaseGenericActionSupport;

public class SystemArgumentsAction extends BaseGenericActionSupport<SystemArguments> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private SystemArgumentsManager service;
	
	@Override
	protected SystemArgumentsManager getService() {
		return service;
	}
	
	/**
	 * 取key下所有值  param domain.key required
	 * @throws Exception
	 */
	public void retrieveList() throws Exception {
		this.items = this.service.retrieveList(this.domain.getKey());
	}

	public String updateAppParam() throws Exception {
		// update db
		this.service.updateAppParam(this.domain);
		// 更新缓存
		SysArgsUtil.update(this.domain.getKey(), this.domain.getVal());
		this.setSuccess(true);
		return "updateAppParams";
	}
	
	public String updateAppParams() throws Exception {
		// update db
		Map<String, String> args = this.service.updateAppParams(this.keys);
		// 更新缓存
		SysArgsUtil.update(args);
		this.setSuccess(true);
		return "updateAppParams";
	}
}
