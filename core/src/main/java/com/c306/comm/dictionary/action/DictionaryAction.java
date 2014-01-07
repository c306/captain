package com.c306.comm.dictionary.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.comm.dictionary.entity.Dictionary;
import com.c306.comm.dictionary.service.DictionaryManager;
import com.c306.core.base.action.BaseGenericActionSupport;

public class DictionaryAction extends BaseGenericActionSupport<Dictionary> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private DictionaryManager service;
	
	@Override
	protected DictionaryManager getService() {
		return service;
	}
	
	/**
	 * 取key下所有值  param domain.key required
	 * @throws Exception
	 */
	public void retrieveList() throws Exception {
		this.items = this.service.retrieveList(this.domain.getKey());
	}

	@Override
	public String list() throws Exception {
		return super.list();
	}
	
	/**
	 * 根据id查询color
	 */
	public void query() throws Exception {
		if(domain != null && domain.getObjid() != null) {
			this.items = this.service.query(domain);
		}
	}

}
