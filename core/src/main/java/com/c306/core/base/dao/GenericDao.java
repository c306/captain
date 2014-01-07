package com.c306.core.base.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.c306.core.support.Page;
import com.c306.utils.BeanPropertyAccessUtils;

/**
 * Hibernate Dao的泛型基类.
 * <p/>
 * 继承于Spring的HibernateDaoSupport,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 * 
 * @see HibernateDaoSupport
 * @see HibernateEntityDao
 * 
 * @author chens
 */
@SuppressWarnings("unchecked")
//@Repository
public class GenericDao {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected SessionFactory sessionFactory;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	
	protected Session getSession(){
        Session sess = this.sessionFactory.getCurrentSession();
        return sess;
	}

	public void afterPropertiesSet() {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * Create a new Array Object.
	 * @param arrs
	 * @return
	 */
	protected static <A> A[] buildArray(A... arrs) {
		return arrs;
	}
	
	/**
	 * 根据ID获取对象
	 */
	public <T> T load(Class<T> entityClass, Serializable id) {
		return (T) (this.getSession().load(entityClass, id));
	}

	public void flush() {
		this.getSession().flush();
	}

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建Criteria对象.
	 * 
	 * @param criterions
	 *            可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
	 */
	public <T> Criteria createCriteria(Class<T> entityClass, Criterion... criterions) {
		return createCriteria("__alias0", entityClass, criterions);
	}
	public <T> Criteria createCriteria(String alias, Class<T> entityClass, Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass, alias);
		if( criterions != null )
			for (Criterion c : criterions) {
				criteria.add(c);
			}
		return criteria;
	}
	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * 
	 * @see #createCriteria(Class,Criterion[])
	 */
	public <T> Criteria createCriteria(Class<T> entityClass, Criterion[] criterions, Order... orders) {

		Criteria criteria = createCriteria(entityClass, criterions);
		for (Order order : orders) {
			criteria.addOrder(order);
		}

		return criteria;
	}

	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * 
	 * @see #createCriteria(Class,Criterion[])
	 */
	@Deprecated
	public <T> Criteria createCriteria(Class<T> entityClass, String orderProperty, boolean isAsc, Criterion... criterions) {

		Criteria criteria = createCriteria(entityClass, criterions);

		if(StringUtils.isNotBlank(orderProperty)) {
			if (isAsc)
				criteria.addOrder(Order.asc(orderProperty));
			else
				criteria.addOrder(Order.desc(orderProperty));
		} else {
//			logger.warn("排序字段为空！");
//			logger.warn("Order Property is null !");
//			if(BaseHbmEntity.class.isAssignableFrom(entityClass)){
//				criteria.addOrder(Order.desc("updatetime"));
//				logger.warn("默认按最后修改时间.");
//			}
		}

		return criteria;
	}

//	/**
//	 * hql查询.
//	 * 
//	 * @param values
//	 *            可变参数 用户可以如下四种方式使用 dao.find(hql) dao.find(hql,arg0);
//	 *            dao.find(hql,arg0,arg1); dao.find(hql,new
//	 *            Object[arg0,arg1,arg2])
//	 */
//	protected List find(String hql, Object... values) {
//		return this.getSession().createQuery(queryString);
//	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return createCriteria(entityClass).list();
	}
	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> find(Class<T> entityClass, String name, Object value) {
		return createCriteria(entityClass, Restrictions.eq(name, value)).list();
	}
	public <T> List<T> find(Class<T> entityClass, Map<String, Object> propertyNameValues) {
		return createCriteria(entityClass, Restrictions.allEq(propertyNameValues)).list();
	}
	public <T> List<T> find(Class<T> entityClass, String name, Object value, String orderProperty, boolean isAsc) {
		return createCriteria(entityClass, orderProperty, isAsc, Restrictions.eq(name, value)).list();
	}
	public <T> List<T> find(Class<T> entityClass, Map<String, Object> propertyNameValues, String orderProperty, boolean isAsc) {
		return createCriteria(entityClass, orderProperty, isAsc, Restrictions.allEq(propertyNameValues)).list();
	}
	public <T> List<T> find(Class<T> entityClass, Map<String, Object> propertyNameValues, Order... orders) {
		return createCriteria(entityClass, buildArray(Restrictions.allEq(propertyNameValues)), orders).list();
	}
	public <T> List<T> find(Class<T> entityClass, Criterion[] criterions, Order... orders) {
		return createCriteria(entityClass, criterions, orders).list();
	}

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象.
	 */
	public <T> List<T> findByLike(Class<T> entityClass, String name, String value) {
		return createCriteria(entityClass, Restrictions.like(name, value, MatchMode.ANYWHERE)).list();
	}

//	/**
//	 * 分页查询函数，使用Criteria.
//	 * 
//	 * @param pageNo
//	 *            页号
//	 * @
//	 */
//	protected Page pagedQuery(Class entityClass, int pageNo, int pageSize, String orderProperty, boolean isAsc, Criterion... criterions) {
//		Criteria criteria = createCriteria(entityClass, orderProperty, isAsc, criterions);
//		return this.pagedQuery(criteria, pageNo, pageSize);
//	}
//	/**
//	 * 分页查询函数，使用Criteria.
//	 * @param entityClass
//	 * @param pageNo
//	 * @param pageSize
//	 * @param criterions
//	 * @return Page
//	 */
//	protected Page pagedQuery(Class entityClass, int pageNo, int pageSize, Criterion... criterions) {
//		Criteria criteria = createCriteria(entityClass, criterions);
//		return this.pagedQuery(criteria, pageNo, pageSize);
//	}

	/**
	 * 分页查询函数，使用DetachedCriteria.
	 * 
	 * @param criteria
	 * 		DetachedCriteria
	 * @param pageNo
	 *			页号
	 * @param pageSize
	 * 			页面大小
	 * 
	 * @return Page
	 */
	public Page pagedQuery(DetachedCriteria criteria, int pageNo, int pageSize) {
		return this.pagedQuery(criteria.getExecutableCriteria(this.getSession()), pageNo, pageSize);
	}

	/**
	 * 分页查询函数，使用Criteria.
	 * 
	 * @param criteria
	 * 		Criteria
	 * @param pageNo
	 *			页号
	 * @param pageSize
	 * 			页面大小
	 * @return Page
	 */
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {

		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries = (List) BeanPropertyAccessUtils.getDeclaredProperty(impl, "orderEntries");
		BeanPropertyAccessUtils.setDeclaredProperty(impl, "orderEntries", new ArrayList());
		// logger.info("page type:"+criteria.setProjection(Projections.rowCount()).uniqueResult().getClass());
		// 执行查询
		Long c = (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		int totalCount = c.intValue();
		
		Page page = new Page(pageSize, pageNo, totalCount);

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		BeanPropertyAccessUtils.setDeclaredProperty(impl, "orderEntries", orderEntries);

		// 返回分页对象
		if (totalCount < 1)
			return page;

		int startIndex = page.getFromIndex();
		List list = criteria.setFirstResult(startIndex).setMaxResults(page.getPageSize()).list();
		page.setDatas(list);
		return page;
	}
	
	/**
	 * 分页查询函数，使用hql.
	 * 
	 * @param hql
	 * 		hql
	 * @param pageNo
	 *			页号
	 * @param pageSize
	 * 			页面大小
	 * @return Page
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
		
		// Count查询
		String countQueryString = "select count (*) " + removeSelect(removeOrders(hql));
		Long totalCount = (Long) this.createQuery(countQueryString, values).uniqueResult();
//		int totalCount = 0;
//		if (countlist.get(0) instanceof Long)
//			totalCount = ((Long) countlist.get(0)).intValue();
//		else
//			totalCount = (Integer) countlist.get(0);

		Page page = new Page(pageSize, pageNo, totalCount.intValue());

		if (totalCount < 1)
			return page;

		// 实际查询返回分页对象
		int startIndex = page.getFromIndex();
		Query query = createQuery(hql, values);
		page.setDatas(query.setFirstResult(startIndex).setMaxResults(page.getPageSize()).list());
		
		return page;
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,，用于pagedQuery.
	 */
	protected static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 */
	protected static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 构造Criteria的排序条件默认函数.可供其他查询函数调用
	 * 
	 * @param criteria
	 *            Criteria实例.
	 * @param sortMap
	 *            排序条件.
	 * @param entity
	 *            entity对象,用于使用反射来获取某些属性信息
	 */
	protected void sortCriteria(Criteria criteria, Map<String, String> sortMap, Object entity) {
		if (!sortMap.isEmpty()) {
			for (String sortName : sortMap.keySet()) {
				String dir = sortMap.get(sortName);

				// 处理嵌套属性如category.name,modify_user.id,暂时只处理一级嵌套
				if (sortName.indexOf('.') != -1) {
					String alias = StringUtils.substringBefore(sortName, ".");
					String aliasType = alias;
					try {
						aliasType = PropertyUtils.getProperty(entity, alias).getClass().getSimpleName();
					} catch (Exception e) {
						logger.error("Get property" + alias + " error");
					}
					criteria.createAlias(aliasType, alias);
				}

				if ("asc".equalsIgnoreCase(dir)) {
					criteria.addOrder(Order.asc(sortName));
				} else {
					criteria.addOrder(Order.desc(sortName));
				}
			}
		}
	}
	
}