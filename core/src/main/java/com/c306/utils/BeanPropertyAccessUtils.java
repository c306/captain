package com.c306.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.c306.core.CoreConstants;

/**
 * 访问在当前类声明的private/protected成员变量或方法.
 * 因为必须为当前类声明的变量,通过继承获得的protected变量将不能访问,需要转型到声明该变量的类
 */
public class BeanPropertyAccessUtils {
	protected static final Log logger = LogFactory.getLog(BeanPropertyAccessUtils.class);
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}	
	/**
	 * 暴力获取当前类声明的private/protected变量
	 */
	static public Object getDeclaredProperty(Object object, String propertyName) {
		try {
			Assert.notNull(object);
			Assert.hasText(propertyName);
			Field field = object.getClass().getDeclaredField(propertyName);
			return getDeclaredProperty(object, field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 暴力获取当前类声明的private/protected变量
	 */
	static public Object getDeclaredProperty(Object object, Field field) throws IllegalAccessException {
		Assert.notNull(object);
		Assert.notNull(field);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object result = field.get(object);
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置当前类声明的private/protected变量
	 */
	static public void setDeclaredProperty(Object object, String propertyName, Object newValue) {
		try {
			Assert.notNull(object);
			Assert.hasText(propertyName);

			Field field = object.getClass().getDeclaredField(propertyName);
			setDeclaredProperty(object, field, newValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 暴力设置当前类声明的private/protected变量
	 */
	static public void setDeclaredProperty(Object object, Field field, Object newValue) throws IllegalAccessException {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.set(object, newValue);
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用当前类声明的private/protected函数
	 */
	static public Object invokePrivateMethod(Object object, String methodName, Object... params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = method.invoke(object, params);
		method.setAccessible(accessible);
		return result;
	}
	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName,
			Object newValue) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			//logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}
	/**
	 * 通过集合中的属性与值暴力设置对象变量值, 忽略private,protected修饰符的限制. *
	 * 
	 * @param bean
	 * @param data
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NumberFormatException
	 */
	public static void forceSetProperty(Object bean, Map<String, Object> data)
			throws NumberFormatException, InstantiationException,
			IllegalAccessException {

		Assert.notNull(data);

		Set<String> keySet = data.keySet();
		Assert.notNull(keySet);

		Iterator<String> keys = keySet.iterator();
		while (keys.hasNext()) {
			String property = keys.next();
			Class type = null;

			try {
				type = getPropertyType(bean.getClass(), property);
			} catch (Exception e) {
				// bean中不存在指定的属性
				continue;
			}

			Object value = data.get(property);
			Object propertyValue = null;
			/*if(property.indexOf(".")>0){
				try {
				Ognl.setValue(property,bean,value);
				
				} catch (Exception e) {
					// bean中不存在指定的属性
					e.printStackTrace();
					continue;
				}
				continue;
			}*/
			if (type.equals(Long.class)||type.getName().equals("long")) {
				if(value!=null){
				String str=value.toString();
				if(str==null ||str.trim().length()==0)
					continue;				
				propertyValue = new Long(value.toString());
				}
			}else if (type.equals(Float.class)||type.getName().equals("float")) {
				
				propertyValue = new Float(value.toString());
			
			}else if (type.equals(Double.class)||type.getName().equals("double")) {
				
				propertyValue = new Double(value.toString());
			
			}else if (type.equals(Integer.class)||type.getName().equals("int")) {
				if(value!=null){
					String str=value.toString();
					if(str==null ||str.trim().length()==0)
						continue;				
					propertyValue = new Integer(value.toString());
					}
				}
			else if (type.equals(Boolean.class)||type.getName().equals("boolean")) {
				if(value!=null){
					String str=value.toString();
					if(str==null ||str.trim().length()==0)
						continue;
					propertyValue = new Boolean(value.toString());
				}
			}else if (type.newInstance() instanceof java.util.Date) {
				continue;
				//propertyValue = UtilDateTime.toDataTime(value.toString());
				
			} else {				
				propertyValue = value;				
			}

			try {				
				String methodName = getSetterName(property);
				invokePrivateMethod(bean, methodName, propertyValue);

			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				//logger.error(ExceptionUtils.formatStackTrace(e));
			}catch (Exception e) {
				
			}
		}
	}
	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class getPropertyType(Class type, String name) {
		try {
			return getDeclaredField(type, name).getType();
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获得field的getter函数名称.
	 */
	public static String getSetterName(String fieldName) {
		Assert.hasText(fieldName, "FieldName required");
		return "set" + StringUtils.capitalize(fieldName);
	}
	/**
	 * 将字符型数据根据类型转换为基本类型的对象
	 * @param type
	 * @param value
	 * @return
	 * @throws NumberFormatException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ParseException 
	 */
	public static Serializable convertByClass(Class type,String value) {
		Serializable propertyValue;
		try {
			propertyValue = null;
			if(value==null || value.trim().length()==0)
				return propertyValue;
			if (type.equals(Long.class)) {
				propertyValue = new Long(value);
			} else if (type.equals(Integer.class)) {
				propertyValue = new Integer(value);
			} else if (type.newInstance() instanceof java.util.Date) {
//			propertyValue = UtilDateTime.toDataTime(value);
				propertyValue = DateUtils.parseDateStrictly(value, CoreConstants.DATETIME_DEFAULT_PATTERNS);
			} else {
				propertyValue = value;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return propertyValue;
	}	
}
