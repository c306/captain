package com.c306.comm.dictionary.entity;

/**
 * TDictionary entity. @author MyEclipse Persistence Tools
 */

public class Dictionary extends com.c306.core.base.entity.BaseHbmEntity implements
		java.io.Serializable {

	// Fields

	private Integer oid;
	private String key;
	private String name;
	private String ename;
	private String desc;
	private String edesc;
	private Integer type;
	
	private String objid;

	// Constructors

	/** default constructor */
	public Dictionary() {
	}

	/** full constructor */
	public Dictionary(String key, String name, String ename, String desc,
			String edesc, Integer ordinal, Integer type) {
		this.key = key;
		this.name = name;
		this.ename = ename;
		this.desc = desc;
		this.edesc = edesc;
		this.ordinal = ordinal;
		this.type = type;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEdesc() {
		return this.edesc;
	}

	public void setEdesc(String edesc) {
		this.edesc = edesc;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

}