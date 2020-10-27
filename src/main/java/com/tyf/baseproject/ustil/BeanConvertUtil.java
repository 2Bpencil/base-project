package com.tyf.baseproject.ustil;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BeanConvertUtil {

	private static final Logger logger = LoggerFactory.getLogger(BeanConvertUtil.class);

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */

	public static <T> T convertMap(Class<T> type, Map map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];

				if (value instanceof SimpleNumber) {
					value = (int) Integer.parseInt(value.toString());
				} else if (value instanceof SimpleScalar) {
					if ("truefalse".contains(value.toString())) {
						value = (boolean) Boolean.valueOf(value.toString());
					} else {
						value = value.toString();
					}
				}

				args[0] = value;
				try {
					descriptor.getWriteMethod().invoke(obj, args);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return (T) obj;
	}

	public static Map<String, Object> getparamsToIndex(Object obj) {
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		List<String> transientfields = new ArrayList<String>();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Transient.class)) {
				transientfields.add(field.getName());
			}
		}

		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!StringUtils.equals(name, "class") && !descriptors[i].getReadMethod().isAnnotationPresent(Transient.class) && !transientfields.contains(name)) {
					logger.info("key: " + propertyToField(name) + "----value:  " + propertyUtilsBean.getNestedProperty(obj, name));
					params.put(propertyToField(name), propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/*
	 * 对象属性转换为字段 例如：userName to user_name
	 * 
	 * @param property 字段名
	 * 
	 * @return
	 */
	public static String propertyToField(String property) {
		if (null == property) {
			return "";
		}

		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (CharUtils.isAsciiAlphaUpper(c)) {
				sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 字段转换成对象属性 例如：user_name to userName
	 * 
	 * @param field
	 * @return
	 */
	public static String fieldToProperty(String field) {
		if (null == field) {
			return "";
		}
		field = field.toLowerCase();
		char[] chars = field.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		if (field.indexOf("_") == 1) {
			sb.replace(0, 1, StringUtils.upperCase(field.substring(0, 1)));
		}
		return sb.toString();
	}
	
	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
	
	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	//首字母转大写
	public static String toUpperCaseFirstOne(String s){
	  if(Character.isUpperCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
