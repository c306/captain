package com.c306.code.order.action;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.order.service.TOrderInfoService;
import com.c306.code.page.utils.ActionUtils;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.hbm.TOrderinfo;

public class OrderAction extends BaseGenericActionSupport<TOrderinfo> {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private TOrderInfoService service;
	@Override
	protected TOrderInfoService getService() {
		return this.service;
	}
	
	public String insertInfo() throws Exception {
		this.domain.setTitle(escapeUTF8(this.domain.getTitle()));
		this.domain.setNote(escapeUTF8(this.domain.getNote()));
		this.domain.setIssend(0);
		this.domain.setCreatetime(ActionUtils.getDate());
		this.domain.setUpdatetime(ActionUtils.getDate());
		this.service.insertInfo(this.domain, type);
		this.getHttpServletResponse().getWriter().write("1");
		
		return null;
	}
	
	public String escapeUTF8(String name) throws UnsupportedEncodingException {
		return (new String(name.getBytes("iso8859-1"), "UTF-8"));
	}
	
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
