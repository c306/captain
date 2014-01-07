package com.c306.core.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * 
 * @author chens
 *
 */
public abstract class BaseAnnotationEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic
    @Temporal(TemporalType.TIMESTAMP)
	protected Date createtime;
	@Basic
    @Temporal(TemporalType.TIMESTAMP)
	protected Date updatetime;
	@Basic
	protected int status;
	@Basic
	protected int ordinal = 100;
	
	


    /**
     * Returns a multi-line String with key=value pairs.
     * @return a String representation of this class.
     */
	public abstract String toString();

    /**
     * Compares object equality. When using Hibernate, the primary key should
     * not be a part of this comparison.
     * @param o object to compare to
     * @return true/false based on equality tests
     */
    public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     * @return hashCode
     */
    public abstract int hashCode();
    
    

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(int ordinal) {
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
