package com.c306.core.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.c306.core.base.dao.IEntityDao;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 *
 * 
 * @author chens
 */
public abstract class HibernateEntityManager<T> implements IGenericManager<T> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * GenericDao instance, set by constructor of child classes
     */
    abstract protected IEntityDao<T> getDao();


    /**
     * {@inheritDoc}
     */
    public T get(Serializable id) {
        return this.getDao().get(id);
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(Serializable id) {
        return this.getDao().exists(id);
    }
	/**
	 * 判断对象某些属性的值在数据库中是否存在重复
	 * except that the pk associate record if pk value is not null
	 */
    @Override
	public boolean exists(T instance, String names) {
        return this.getDao().exists(instance, names);
	}

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return this.getDao().save(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(T object) {
    	this.getDao().remove(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(Serializable id) {
    	this.getDao().remove(id);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Search implementation using Hibernate Search.
     */
    public List<T> search(String q) {
        if (q == null || "".equals(q.trim())) {
            return null;
        }

        return this.getDao().search(q);
    }

    /**
     * {@inheritDoc}
     */
    public void reindex() {
    	this.getDao().reindex();
    }

    /**
     * {@inheritDoc}
     */
    public void reindexAll(boolean async) {
    	this.getDao().reindexAll(async);
    }


	@Override
	public List<T> findAll() {
		
		return this.findAll();
	}

	@Override
	@Deprecated
	public Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize) {
		
		return this.getDao().pagedQuery(criteria, pageNo, pageSize);
	}


	@Override
	public T saveOrUpdate(T obj) {
		
		return this.getDao().saveOrUpdate(obj);
	}


	@Override
	public T saveOrUpdate(String entityName, T obj) {
		
		return this.getDao().saveOrUpdate(entityName, obj);
	}

	@Override
	public void saveOrUpdateAll(List<T> list) {
		this.getDao().saveOrUpdateAll(list);
	}

	@Override
	public T merge(T detachedInstance) {
		
		return this.getDao().merge(detachedInstance);
	}


	@Override
	public void removeById(Serializable id) {
		
		this.getDao().removeById(id);
	}


	@Override
	public void removeById(String entityName, Serializable id) {
		
		this.getDao().removeById(entityName, id);
	}


	@Override
	public void removeByIds(List<String> ids) {
		
		this.getDao().removeByIds(ids);
	}


	@Override
	public Integer getCount(String sql) {
		
		return this.getDao().getCount(sql);
	}


	@Override
	public boolean saveByAjax(Map<String, Object> data) {
		
		return this.getDao().saveByAjax(data);
	}


	@Override
	public String removeByAjax(Map<String, Object> data) {
		
		return this.getDao().removeByAjax(data);
	}


	@Override
	public List<T> findAllDistinct() {
		
		return this.getDao().findAllDistinct();
	}


	@Override
	public List<T> find(String name, Object value) {
		
		return this.getDao().find(name, value);
	}


	@Override
	public List<T> find(String name, Object value, String orderProperty,
			boolean isAsc) {
		
		return this.getDao().find(name, value, orderProperty, isAsc);
	}


	@Override
	public List<T> findByLike(String name, String value) {
		
		return this.getDao().findByLike(name, value);
	}

	@Override
	public Page pagedQuery(QueryParameters query) {
		return this.getDao().pagedQuery(query);
	}

	@Override
	public void removeByIds(String[] ids) {
		this.getDao().removeByIds(ids);
	}

	@Override
	public void move2trash(String[] ids) {
		this.getDao().move2trash(ids);
	}
}
