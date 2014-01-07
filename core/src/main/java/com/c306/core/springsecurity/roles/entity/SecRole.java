package com.c306.core.springsecurity.roles.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import com.c306.core.springsecurity.users.entity.SecUser;

/**
 * SecRole entity. @author MyEclipse Persistence Tools
 */

public class SecRole extends com.c306.core.base.entity.BaseHbmEntity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private Long id;
	private String name;
	private String descn;
	
	//
	private Set<SecUser> users = new HashSet<SecUser>(0);

	// Constructors

	/** default constructor */
	public SecRole() {
	}

	/** minimal constructor */
	public SecRole(Long id) {
		this.id = id;
	}

	/** full constructor */
	public SecRole(Long id, String name, String descn) {
		this.id = id;
		this.name = name;
		this.descn = descn;
	}

    /**
     * @return the name property (getAuthority required by Acegi's GrantedAuthority interface)
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Transient
    public String getAuthority() {
        return getName();
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

	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public Set<SecUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SecUser> users) {
		this.users = users;
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
        return this.getId() == ((SecRole)o).getId();
	}

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .toString();
    }
	
	public void addUser(SecUser u){
		this.getUsers().add(u);
		u.getRoles().add(this);
	}
	public void removeUser(SecUser u){
		this.getUsers().remove(u);
		u.getRoles().remove(this);
	}

}