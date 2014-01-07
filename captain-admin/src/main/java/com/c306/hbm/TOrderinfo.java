package com.c306.hbm;

import java.util.Date;

/**
 * TOrderinfo entity. @author MyEclipse Persistence Tools
 */

public class TOrderinfo extends com.c306.core.base.entity.BaseHbmEntity implements
		java.io.Serializable {

	// Fields

	private long oid;
	private String objid;
	private String title;
	private String note;
	private Integer issend;

	// Constructors

	/** default constructor */
	public TOrderinfo() {
	}

	/** minimal constructor */
	public TOrderinfo(long oid) {
		this.oid = oid;
	}

	/** full constructor */
	public TOrderinfo(long oid, String objid, String title, String note,
			Integer issend, Date createtime, Date updatetime) {
		this.oid = oid;
		this.objid = objid;
		this.title = title;
		this.note = note;
		this.issend = issend;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	// Property accessors

	public long getOid() {
		return this.oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getObjid() {
		return this.objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getIssend() {
		return this.issend;
	}

	public void setIssend(Integer issend) {
		this.issend = issend;
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

}