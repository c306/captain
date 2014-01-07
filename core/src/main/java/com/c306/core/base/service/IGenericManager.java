package com.c306.core.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 *
 * 
 * @author chens
 */
public interface IGenericManager<T> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> findAll();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(Serializable id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(Serializable id);

	boolean exists(T instance, String names);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     */
    T save(T object);

    /**
     * Generic method to delete an object
     * @param object the object to remove
     */
    void remove(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(Serializable id);

    /**
     * Generic method to search for an object.
     * @param searchTerm the search term
     * @return a list of matched objects
     */
    List<T> search(String searchTerm);
    /**
     * Generic method to regenerate full text index of the persistent class T
     */
    void reindex();

    /**
     * Generic method to regenerate full text index of all indexed classes
     *
     * @param async
     *            true to perform the reindexing asynchronously
     */
    void reindexAll(boolean async);
    
    
	@Deprecated
	Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize);

	Page pagedQuery(QueryParameters query);
	
	T saveOrUpdate(T obj);
	
	T saveOrUpdate(String entityName, T obj);

	void saveOrUpdateAll(List<T> list);
	
	T merge(T detachedInstance);


	void removeById(Serializable id);
	
	void removeById(String entityName, Serializable id);

    void removeByIds(String[] ids);
    
    void removeByIds(List<String> ids);

	void move2trash(String[] array);
	
	String removeByAjax(Map<String, Object> data);

    
	Integer getCount(String sql);
	
	boolean saveByAjax(Map<String, Object> data);
	

	List<T> findAllDistinct();
	
	List<T> find(String name, Object value);

	List<T> find(String name, Object value, String orderProperty, boolean isAsc);

	List<T> findByLike(String name, String value);
	
    
}
