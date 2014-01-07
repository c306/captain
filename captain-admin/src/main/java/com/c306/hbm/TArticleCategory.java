package com.c306.hbm;

import java.util.Date;

/**
 * TArticleCategory entity. @author MyEclipse Persistence Tools
 */

public class TArticleCategory extends com.c306.core.base.entity.BaseHbmEntity
		implements java.io.Serializable {

	// Fields

	private String oid;
	private String pid;
	private String objtype;
	private String title;
	private String etitle;

	// Constructors

	/** default constructor */
	public TArticleCategory() {
	}

	/** minimal constructor */
	public TArticleCategory(String oid) {
		this.oid = oid;
	}

	/** full constructor */
	public TArticleCategory(String oid, String objtype, String title,
			String etitle, Date createtime, Date updatetime, Integer ordinal,
			Integer status) {
		this.oid = oid;
		this.objtype = objtype;
		this.title = title;
		this.etitle = etitle;
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

	public String getObjtype() {
		return this.objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEtitle() {
		return this.etitle;
	}

	public void setEtitle(String etitle) {
		this.etitle = etitle;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}