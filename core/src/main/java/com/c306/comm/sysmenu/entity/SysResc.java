package com.c306.comm.sysmenu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The persistent class for the sys_resc database table.
 * 
 */
@Entity
@Table(name="sys_resc")
@NamedQuery(name="SysResc.findAll", query="SELECT s FROM SysResc s")
public class SysResc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(unique=true, nullable=false)
	private String oid;

	@Column(length=255)
	private String desc;

	@Column(length=255)
	private String ename;

	@Column(nullable=false, length=255)
	private String name;

	@Column(nullable=false, length=255)
	private String url;

	public SysResc() {
	}

	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


    /**
     * {@inheritDoc}
     */
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this == null || o == null || !this.getClass().isInstance(o)) {
            return false;
        }
        return this.getOid() == ((SysResc)o).getOid();
	}

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (oid != null ? oid.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .append(this.ename)
                .append(this.url)
                .append(this.desc)
                .toString();
    }
	

}