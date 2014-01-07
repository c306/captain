package com.c306.core.base.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * 
 * @author chens
 *
 */
public class BaseHbmEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Date createtime;
	protected Date updatetime;
	protected Integer status;
	protected Integer ordinal = 100;

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
//	public boolean equals(Object o) {
//		return EqualsBuilder.reflectionEquals(this, o);
//	}
//	public int hashCode() {
//		return HashCodeBuilder.reflectionHashCode(this);
//	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
