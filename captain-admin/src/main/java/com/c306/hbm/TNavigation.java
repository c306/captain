package com.c306.hbm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.c306.code.page.entity.TBar;

/**
 * TNavigation entity. @author MyEclipse Persistence Tools
 */

public class TNavigation extends com.c306.core.base.entity.BaseHbmEntity implements
		java.io.Serializable {

	// Fields

	private String oid;
	private String name;
	private String ename;
	private String objid;
	
	private List<List<TBar>> barList = new ArrayList<List<TBar>>();

	// Constructors

	/** default constructor */
	public TNavigation() {
	}

	/** minimal constructor */
	public TNavigation(String oid) {
		this.oid = oid;
	}

	/** full constructor */
	public TNavigation(String oid, String name, String ename, String objid,
			Date createtime, Date updatetime, Integer ordinal, Integer status) {
		this.oid = oid;
		this.name = name;
		this.ename = ename;
		this.objid = objid;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.ordinal = ordinal;
		this.status = status;
	}

	// Property accessors

	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getObjid() {
		return this.objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<List<TBar>> getBarList() {
		return barList;
	}

	public void setBarList(List<List<TBar>> barList) {
		this.barList = barList;
	}

}