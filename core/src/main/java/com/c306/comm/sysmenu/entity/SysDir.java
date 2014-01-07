package com.c306.comm.sysmenu.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;


/**
 * The persistent class for the sys_dir database table.
 * 
 */
@Entity
@Table(name="sys_dir")
@NamedQuery(name="SysDir.findAll", query="SELECT s FROM SysDir s")
public class SysDir extends com.c306.core.base.entity.BaseAnnotationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(unique=true, nullable=false)
	private String oid;

	@Column(length=255)
	private String desc;

	@Column(nullable=false, length=255)
	private String ename;

	@Column(nullable=false, length=255)
	private String name;

	@Column(name="node_path", length=255)
	private String nodePath;

	//bi-directional many-to-one association to SysDir
	@ManyToOne
	@JoinColumn(name="pid")
	private SysDir parentDir;

	//bi-directional many-to-one association to SysDir
	@OneToMany(mappedBy="parentDir")
	private List<SysDir> childDirs;

	//uni-directional many-to-one association to SysResc
	@ManyToOne
	@JoinColumn(name="resc_id")
	private SysResc resc;

	public SysDir() {
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

	public String getNodePath() {
		return this.nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public SysDir getParentDir() {
		return this.parentDir;
	}

	public void setParentDir(SysDir parentDir) {
		this.parentDir = parentDir;
	}

	public List<SysDir> getChildDirs() {
		return this.childDirs;
	}

	public void setChildDirs(List<SysDir> childDirs) {
		this.childDirs = childDirs;
	}

	public SysDir addChildDir(SysDir childDir) {
		getChildDirs().add(childDir);
		childDir.setParentDir(this);

		return childDir;
	}

	public SysDir removeChildDir(SysDir childDir) {
		getChildDirs().remove(childDir);
		childDir.setParentDir(null);

		return childDir;
	}

	public SysResc getResc() {
		return this.resc;
	}

	public void setResc(SysResc resc) {
		this.resc = resc;
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
        return this.getOid() == ((SysDir)o).getOid();
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
                .append(this.desc)
                .append(this.resc==null?"":this.resc.toString())
                .toString();
    }

}