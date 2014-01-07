package com.c306.core.springsecurity.resources.entity;

/**
 * SecResc entity. @author MyEclipse Persistence Tools
 */

public class SecResc extends com.c306.core.base.entity.BaseHbmEntity {

	private static final long serialVersionUID = 1L;
	

	// Fields

	private Long id;
	private String name;
	private String resType;
	private String resString;
	private Integer priority;
	private String descn;
	
	private transient Long roleId;

	// Constructors

	/** default constructor */
	public SecResc() {
	}

	/** minimal constructor */
	public SecResc(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SecResc(Long id, String name, String resType, String resString,
			Integer priority, String descn) {
		this.id = id;
		this.name = name;
		this.resType = resType;
		this.resString = resString;
		this.priority = priority;
		this.descn = descn;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResType() {
		return this.resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResString() {
		return this.resString;
	}

	public void setResString(String resString) {
		this.resString = resString;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}