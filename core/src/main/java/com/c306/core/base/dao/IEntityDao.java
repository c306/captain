package com.c306.core.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

/**
 * 针对单个Entity对象的操作定义.
 * 不依赖于具体ORM实现方案.
 *
 * 
 * @author chens
 */
public interface IEntityDao<T> {

	T get(Serializable id);
	
	@Deprecated
	Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize);
	
//	Page pagedQuery(String hql, int pageNo, int pageSize, Object... values);
	
	T save(T obj);
	
	T saveOrUpdate(T obj);
	
	void saveOrUpdateAll(List<T> obj);
	
	T saveOrUpdate(String entityName, T obj);
	
	T merge(T detachedInstance);

	void remove(Serializable id);
	
	void remove(T o);

	void removeById(Serializable id);
	
	void removeById(String entityName, Serializable id);
	
    void removeByIds(List<String> ids);
    
	Integer getCount(String sql);
	
	boolean saveByAjax(Map<String, Object> data);
	
	String removeByAjax(Map<String, Object> data);


	List<T> findAll();
	
	List<T> findAllDistinct();
	
	List<T> find(String name, Object value);

	List<T> find(String name, Object value, String orderProperty, boolean isAsc);

	List<T> find(Map<String, Object> params, Order... orders);

	List<T> find(Criterion[] criterions, Order... orders);
	

	List<T> findByLike(String name, String value);

	boolean exists(Serializable id);

	boolean exists(T instance, String names);

	void reindex();

	void reindexAll(boolean async);

	List<T> search(String q);

	Page pagedQuery(QueryParameters query);

	void removeByIds(String[] ids);

	void move2trash(String[] ids);
	
}
