package com.c306.core.base.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.c306.core.CoreConstants;
import com.c306.core.base.entity.BaseAnnotationEntity;
import com.c306.core.base.entity.BaseHbmEntity;
import com.c306.core.support.Page;
import com.c306.core.support.QueryParameters;
import com.c306.utils.BeanPropertyAccessUtils;
import com.c306.utils.GenericsUtils;

/**
 * 负责为单个Entity对象提供CRUD操作的Hibernate DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作. eg.
 * 
 * <pre>
 * public class UserManager extends HibernateEntityDao&lt;User&gt; {
 * }
 * </pre>
 * 
 * @author chens
 */
@SuppressWarnings("unchecked")
public class HibernateEntityDao<T> extends GenericDao implements IEntityDao<T>, InitializingBean, DisposableBean {
	
	public final static int COUNT_MODE = 1;
	public final static int SCROLL_MODE = 2;
	public final static int LIST_MODE = 3;
	/**
	 * DAO所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	/**
	 * 取得entityClass. JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 在构造函数中将泛型T.class赋给entityClass
	 */
	public HibernateEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 根据ID获取对象
	 * 
	 * @param id
	 * @return (T) (this.getSession().load(entityClass, id));
	 */
	public T load(Serializable id) {
		return super.load(getEntityClass(), id);
	}
	
	/**
	 * 取得指定对象，同时带出指定关系名
	 * load,get
	 * （1）当记录不存在时候，get方法返回null,load方法产生异常
	 * （2）load方法可以返回实体的代理类，get方法则返回真是的实体类
	 * （3）load方法可以充分利用hibernate的内部缓存和二级缓存中的现有数据，而get方法仅仅在内部缓存中进行数据查找，如果没有发现数据則将越过二级缓存，直接调用SQL查询数据库。
	 * （4）也许别人把数据库中的数据修改了，load如何在缓存中找到了数据，则不会再访问数据库，而get则会返回最新数据。
	 * @param id
	 * @param relations
	 * @return
	 */
	public T load(Serializable id, String[] relations) {
		T obj = load(id);
		try {
			if (relations != null) {
				@SuppressWarnings("unused")
				Object temp = null;
				for (String rl : relations) {
					//触发hibernate抓取关联对象
					temp = PropertyUtils.getProperty(obj, rl);
					logger.info("relations:" + rl);
				}
				temp = null;
			}
		} catch (Exception e) {
			logger.error("error.", e);
		}
		return obj;
	}

	/**
	 * 根据ID获取对象
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NumberFormatException 
	 */
	public T get(Serializable id) {
		return (T)get(getEntityClass(), id);
	}

	public Object get(Class entityClass, Serializable id) {
		String pkidName = getPkPropertyName(entityClass);
		Class pkidType = BeanPropertyAccessUtils.getPropertyType(this.entityClass, pkidName);
		id = (Serializable) BeanUtilsBean2.getInstance().getConvertUtils().convert(id, pkidType);
//		
//		List list = this.find("from " + entityClass.getName() + " where " + pkidName + "=?", new Object[] { id });
//		Object obj = null;
//		if (list != null && list.size() > 0)
//			obj = list.get(0);
//		return obj;
		return this.getSession().get(entityClass, id);
	}
	
	
	/**
	 * 将传入的detached状态的对象的属性复制到持久化对象中，并返回该持久化对象。 如果该session中没有关联的持久化对象，加载一个。
	 * 如果传入对象未保存，保存一个副本并作为持久对象返回，传入对象依然保持detached状态。
	 */
	public T merge(T detachedInstance) {
		logger.debug("merging T instance");
		try {
			T result = (T) this.getSession().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public T save(T instance) {
		checkDomain(instance);
		this.getSession().save(instance);
		return instance;
	}

	/**
	 * 将传入的对象持久化并保存。 如果对象是新对象（Transient状态），调用save方法保存。
	 * 如果对象已存在（Detached状态），调用update方法将对象与Session重新关联。
	 */
	public T saveOrUpdate(T instance) {
		checkDomain(instance);
		this.getSession().saveOrUpdate(instance);
		return instance;
	}
	
	public T saveOrUpdate(String entityName, T instance) {
		checkDomain(instance);
		this.getSession().saveOrUpdate(entityName, instance);
	
		return instance;
	}
	
	private void checkDomain(T instance) {

		if(instance instanceof BaseHbmEntity){
			BaseHbmEntity entity = (BaseHbmEntity)instance;
//			if(entity.getStatus() == null)
//				entity.setStatus(CoreConstants.RECORDSTATUS_VALID);
			if(entity.getCreatetime() == null)
				entity.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			if(entity.getUpdatetime() == null)
				entity.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		} else if(instance instanceof BaseAnnotationEntity){
			BaseAnnotationEntity entity = (BaseAnnotationEntity)instance;
//			if(entity.getStatus() == null)
//				entity.setStatus(CoreConstants.RECORDSTATUS_VALID);
			if(entity.getCreatetime() == null)
				entity.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			if(entity.getUpdatetime() == null)
				entity.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		}
	}

	/**
	 * 将传入的对象状态设置为Transient状态
	 * 
	 */
	@Deprecated
	public void attachClean(T instance) {
		logger.debug("attaching clean T instance");
		try {
			this.getSession().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 保存对象集
	 * 
	 * @param c
	 */
	public void saveOrUpdateAll(List<T> c) {
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			T object = (T) iterator.next();
			this.saveOrUpdate(object);
		}
	}
	
//	/**
//  * A default implementation that query without QueryParameters.
//  * Subclasses should override this method to set QueryParameters.
//	 * @param t
//	 * @param entityClass
//	 * @param pageNo
//	 * @param pageSize
//	 * @param criterions
//	 * @return
//	 */
//	public Page pagedQuery(QueryParameters params, Class entityClass, Criterion... criterions) {
//		return super.pagedQuery(entityClass, params.getPageNo(), params.getPageSize());
//	}
//
//	/**
//   * A default implementation that query without QueryParameters.
//   * Subclasses should override this method to set QueryParameters.
//	 * @param t
//	 * @param entityClass
//	 * @param pageNo
//	 * @param pageSize
//	 * @param criterions
//	 * @return
//	 */
//	public Page pagedQuery(QueryParameters params, String orderProperty, boolean isAsc, Criterion... criterions) {
//		return super.pagedQuery(getEntityClass(), params.getPageNo(), params.getPageSize(), orderProperty, isAsc, criterions);
//	}
//

	/**
     * A default implementation that query without QueryParameters.
     * Subclasses should override this method to set QueryParameters.
	 * 分页查询函数，
	 * 
	 * @param params
	 * 		QueryParameters
	 * @return Page
	 */
	public Page pagedQuery(QueryParameters params, Criterion[] criterions, Order... orders) {
		return super.pagedQuery(this.createCriteria(this.getEntityClass(), criterions, orders), params.getPageNo(), params.getPageSize());
	}

	/**
     * A default implementation that query without QueryParameters.
     * Subclasses should override this method to set QueryParameters.
	 * 分页查询函数，
	 * 
	 * @param params
	 * 		QueryParameters
	 * @return Page
	 */
	public Page pagedQuery(QueryParameters params) {
		return super.pagedQuery(this.createCriteria(), params.getPageNo(), params.getPageSize());
	}

	protected Page pagedQuery(Criteria criteria, QueryParameters params) {
		return super.pagedQuery(criteria, params.getPageNo(), params.getPageSize());
	}
	
	/**
	 * 获取主键名
	 * 
	 * @return
	 */
	public String getTableName() {
		return ((AbstractEntityPersister)this.sessionFactory.getClassMetadata(getEntityClass())).getTableName();
	}
	/**
	 * 获取主键名
	 * 
	 * @return
	 */
	public String getPkPropertyName() {
		return getPkPropertyName(getEntityClass());
	}

	/**
	 * 获取主键名
	 * 
	 * @return
	 */
	public String getPkPropertyName(Class entityClass) {
		String rt = null;
		rt = this.sessionFactory.getClassMetadata(entityClass).getIdentifierPropertyName();
		return rt;
	}
	
	/**
	* 根据属性名获得对应字段名
	* 此方法不考虑联合主键的情况
	*/
	public String getPkColumnName() {
		return getPkColumnName(this.entityClass, getPkPropertyName());
	}
	/**
	 * 根据属性名获得对应字段名
	 * 此方法不考虑联合主键的情况
	 */
	public String getPkColumnName(Class entityClass, String propertyName) {
		AbstractEntityPersister classMetadata = (AbstractEntityPersister) this.sessionFactory.getClassMetadata(entityClass);
		//添加主键
		return classMetadata.getIdentifierColumnNames()[0];
	}

	public List<String> getColumnNames() {
		List<String> resultList = new ArrayList<String>();
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) 
				this.sessionFactory.getClassMetadata(entityClass);
		// 添加主键
		resultList.addAll(Arrays.asList(classMetadata.getIdentifierColumnNames()));
		
		for (String propertyName : classMetadata.getPropertyNames()) {
			// 判断是否一对多的对像,移除
			boolean isCollection = classMetadata.getClassMetadata()
					.getPropertyType(propertyName).isCollectionType();
			if (!isCollection) {
				for (String columnName : classMetadata
						.getPropertyColumnNames(propertyName)) {
					resultList.add(columnName);
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 物理删除
	 */
	public void remove(T entity) {
		this.getSession().delete(entity);
	}
	/**
	 * 根据ID移除对象.
	 * 物理删除
	 */
	public void removeById(Serializable id) {
		removeById(getEntityClass().getName(), id);
	}
	/**
	 * for (String id : ids) {
	 *		removeById(id);
	 *	}
	 * @param ids
	 * @throws Exception
	 */
	public void removeByIds(String [] ids) {
		for (String id : ids) {
			removeById(id);
		}
	}
	
	/**
	 * 删除多条记录
	 * 同removeByIds(String [] ids)
	 * 
	 * @param ids
	 * @
	 */
	public void removeByIds(List<String> ids) {
//		removeByIds(ids.toArray(new String[0]));
		for (String id : ids) {
			removeById(id);
		}
	}

	/**
	 * 根据ID移除对象.移除前需要先load(id).
	 * 物理删除 this.getSession().delete(entityName, load(id));
	 */
	public void removeById(String entityName, Serializable id) {
		if (id != null) {
			if(id instanceof String) {
				// 主键名称
				String pkidName = getPkPropertyName(this.entityClass);
				id = BeanPropertyAccessUtils.convertByClass(BeanPropertyAccessUtils.getPropertyType(this.entityClass, pkidName), (String)id);
			}
			this.getSession().delete(entityName, load(id));
		}
	}

	/**
	 * 
	 * 逻辑删除
	 * "update " + getTableName() + " set status=0 where " + getPkColumnName() + " = ?";
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void move2trash(String [] ids) {
		// 主键名称
		String pkidName = getPkPropertyName();
		// 主键类型
		Class pkidType = BeanPropertyAccessUtils.getPropertyType(this.entityClass, pkidName);
		
//		String sql_del = "delete from " + getTableName() + " t where t." + getPkColumnName() + " = ?";
		//mysql 不能给表取别名
		String sql_trash = "update " + getTableName() + " set status=0 where " + getPkColumnName() + " = ?";
		//log
		logger.info("sql_trash=" + sql_trash);
		
		for (String pkidVlaue : ids) {
			if (pkidVlaue != null && pkidVlaue.trim().length() > 0) {
				Serializable pkid = BeanPropertyAccessUtils.convertByClass(pkidType, pkidVlaue);
				this.getSession().createSQLQuery(sql_trash).setParameter(0, pkid).executeUpdate();
			}
		}
	}
	
	/**
	 * 
	 * 物理删除
	 * "delete from " + getTableName() + " where " + getPkColumnName() + " = ?"
	 * @param ids
	 * @throws Exception
	 */
	public void removeEntityByIdsWithoutCascade(String [] ids) {
		// 主键名称
		String pkidName = getPkPropertyName();
		// 主键类型
		Class pkidType = BeanPropertyAccessUtils.getPropertyType(this.entityClass, pkidName);
		
//		String sql_del = "delete from " + getTableName() + " t where t." + getPkColumnName() + " = ?";
		//mysql 不能给表取别名
		String sql_del = "delete from " + getTableName() + " where " + getPkColumnName() + " = ?";
		//log
		logger.info("sql_del=" + sql_del);
		
		for (String pkidVlaue : ids) {
			if (pkidVlaue != null && pkidVlaue.trim().length() > 0) {
				Serializable pkid = BeanPropertyAccessUtils.convertByClass(pkidType, pkidVlaue);
				this.getSession().createSQLQuery(sql_del).setParameter(0, pkid).executeUpdate();
			}
		}
	}

	@Override
	public List<T> findAll() {
		return super.findAll(getEntityClass());
	}

    /**
     * {@inheritDoc}
     */
	@Override
    public List<T> findAllDistinct() {
        Collection<T> result = new LinkedHashSet<T>(findAll());
        return new ArrayList<T>(result);
    }
    
	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	@Override
	public List<T> find(String name, Object value) {
		return find(getEntityClass(), name, value);
	}
	@Override
	public List<T> find(String name, Object value, String orderProperty, boolean isAsc) {
		return find(getEntityClass(), name, value, orderProperty, isAsc);
	}
	@Override
	public List<T> find(Map<String, Object> params, Order... orders) {
		return super.find(getEntityClass(), params, orders);
	}
	@Override
	public List<T> find(Criterion[] criterions, Order... orders) {
		return super.find(getEntityClass(), criterions, orders);
	}

	public List<T> findByDetachedCriteria(DetachedCriteria dc) {
		return dc.getExecutableCriteria(this.getSession()).list();
	}

	public List findByHql(String hql, Object... values) {
		
//		Query qry = this.getSession().createQuery(hql);
//		
//		for (int i = 0; i < values.length; i++) {
//			qry.setParameter(i, values[i]);
//		}
		
		
		return this.createQuery(hql, values).list();
	}

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象.
	 */
	@Override
	public List<T> findByLike(String name, String value) {
		return findByLike(getEntityClass(), name, value);
	}

	/**
	 * 取得Entity的Criteria.
	 */
	protected Criteria getEntityCriteria() {
		return createCriteria(getEntityClass());
	}

	/**
	 * 判断对象某些属性的值在数据库中是否存在重复
	 * except that the pk associate record if pk value is not null
	 */
	@Override
	public boolean exists(T instance, String names) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(entityClass).setProjection(Projections.rowCount());
		// Criteria criteria = createCriteria(entityClass).setProjection(Projections.rowCount());
		String[] nameList = names.split(",");
		try {
			// 循环加入
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(instance, name)).ignoreCase());
			}

			// 通过Hibernate的MetaData接口取得主键名
			String pkName = this.getSession().getSessionFactory().getClassMetadata(entityClass)
					.getIdentifierPropertyName();

			if (pkName != null) {
				// get pkValue
				Object pkValue = PropertyUtils.getProperty(instance, pkName);
				// not include  pkColumn != pkValue
				if (pkValue != null)
					criteria.add(Restrictions.not(Restrictions.eq(pkName, pkValue)));
			} else {
				logger.error(this.getClass().getSimpleName() + "has no id column define");
				return false;
			}
		} catch (Exception e) {
			logger.error("query isExists err. ", e);
			throw new RuntimeException(e);
		}
		Long count = (Long) criteria.uniqueResult();
		return count > 0;
	}

	/**
	 * 判断主键是否存在，不考虑联合主键的情况
	 */
	@Override
	public boolean exists(Serializable id) {
		Object rs = this.getSession().createSQLQuery("select 1 from " + this.getTableName() + " t where t." + this.getPkColumnName() + " = ?")
			.setParameter(0, id)
			.uniqueResult();
		return rs != null;
	}

	/**
	 * 消除与 Hibernate Session 的关联
	 * 
	 * @param entity
	 */
	public void evit(Object entity) {
		this.getSession().evict(entity);
	}

	/**
	 * 统计条数
	 * 
	 * @param criteria
	 * @return
	 */
	public Integer getCount(Criteria criteria) {
		return (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * 根据map内的数据得到实体对象
	 * 
	 * @param data
	 * @return
	 */
	protected T buildEntity(Map<String, Object> data) {
		// 得到当前对象
		T entity = null;
		try {
			// 主键名称
			String pkidName = getPkPropertyName(this.entityClass);
			// 主键类型
			Class pkidType = BeanPropertyAccessUtils.getPropertyType(this.entityClass, pkidName);

			// 主键值
			if (!data.containsKey(pkidName)) {
				if (data.containsKey("recordKey"))
					data.put(pkidName, data.get("recordKey"));
			}
			String pkidVlaue = (String) data.get(pkidName);
			if (pkidVlaue != null && pkidVlaue.trim().length() > 0) {// 修改
				Serializable pkid = BeanPropertyAccessUtils.convertByClass(pkidType, pkidVlaue);
				try {
					entity = this.get(pkid);
				} catch (Exception e) {
					// 新增时不存在对象
				}
			}// 否则，新增记录
			if (entity == null)
				entity = this.getEntityClass().newInstance();

			// 将页面提交的数据转换为实体对象中的属性值
			BeanPropertyAccessUtils.forceSetProperty(entity, data);

		} catch (Exception e) {
			logger.error("buildEntity时出错。", e);
		}

		return entity;
	}

	/**
	 * 供页面ajax调用的保存方法
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 * @throws
	 */
	public boolean saveByAjax(Map<String, Object> data) {

		boolean result = true;

		try {

			// 得到当前对象
			T entity = buildEntity(data);

			// 将页面提交的数据转换为实体对象中的属性值
			// BeanPropertyAccessUtils.forceSetProperty(entity, data);

			if (result && entity != null) {
				this.saveOrUpdate(entity);
				// this.save(entity);

				this.flush();

				// 取主键值
				String pkidName = getPkPropertyName(this.entityClass);
				Object pkid = BeanUtils.getProperty(entity, pkidName);
				data.put(pkidName, pkid);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error.", e);
			result = false;

		}

		return result;
	}

	/**
	 * 删除操作
	 * 
	 * @param data
	 * @return 返回为空表示没有删除成功
	 */
	public String removeByAjax(Map<String, Object> data) {

		String id = null;
		try {
			/*
			 * entity = this.getEntityClass().newInstance(); //
			 * 将页面提交的数据转换为实体对象中的属性值 BeanPropertyAccessUtils.forceSetProperty(entity, data);
			 */
			// 主键名称
			String pkidName = getPkPropertyName(this.entityClass);
			// 主键类型
			Class pkidType = BeanPropertyAccessUtils.getPropertyType(this.entityClass, pkidName);
			// 主键值
			if (!data.containsKey(pkidName)) {
				if (data.containsKey("recordKey"))
					data.put(pkidName, data.get("recordKey"));
			}
			String pkidVlaue = (String) data.get(pkidName);
			if (pkidVlaue != null && pkidVlaue.trim().length() > 0) {// 修改
				Serializable pkid = BeanPropertyAccessUtils.convertByClass(pkidType, pkidVlaue);
				this.removeById(pkid);
				id = pkidVlaue;
			}

		} catch (Exception e) {
			logger.error("error.", e);
		}

		/*
		 * String id = null; if (result) { this.remove(entity);
		 * 
		 * String idName = this.getPkPropertyName(getEntityClass()); id = (String)
		 * data.get(idName); }
		 */

		return id;
	}

	/**
	 * 取得Entity的Criteria.
	 * 
	 * @see HibernateGenericDao#createCriteria(Class,Criterion[])
	 */
	public Criteria createCriteria(Criterion... criterions) {
		return createCriteria(getEntityClass(), criterions);
	}
	public Criteria createCriteria(String alias, Criterion... criterions) {
		return createCriteria(alias, getEntityClass(), criterions);
	}

	public Integer getCount(String sql) {
		String countQueryString = " select count (*)  " + removeSelect(removeOrders(sql));
		List countlist = getSession().createSQLQuery(countQueryString).list();
		int totalCount = 0;
		if (countlist.get(0) instanceof Integer)
			totalCount = ((Integer) countlist.get(0)).intValue();

		return totalCount;
	}

	/**
	 * 获得hql执行指定范围数据
	 * 
	 * @param hql
	 * @param startIndex
	 * @param maxResult
	 * @return
	 */
	public List getObjects(Class<T> entityClass, int startIndex, int maxResult, Criterion... criterions) {

		Criteria criteria = createCriteria(entityClass, criterions);

		return criteria.setFirstResult(startIndex).setMaxResults(maxResult).list();
	}

	/**
	 * 统计条数
	 * 
	 * @param criteria
	 * @return
	 */
	public Integer getCount(Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		return getCount(criteria);
	}

	@Override
	public void remove(Serializable id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reindex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reindexAll(boolean async) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> search(String q) {
		// TODO Auto-generated method stub
		return null;
	}
}