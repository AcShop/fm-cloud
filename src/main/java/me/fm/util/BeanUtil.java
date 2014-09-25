package me.fm.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import me.fm.cloud.model.Music;

/**
 * java对象和map转换
 * @author:rex
 * @date:2014年9月17日
 * @version:1.0
 */
public class BeanUtil {

	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
	public static <T> T toBean(Map<String, Object> map, Class<T> clazz){
		T object = null;
		try {
			object = clazz.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					// 得到property对应的setter方法  
					Method setter = property.getWriteMethod();
					setter.invoke(object, value);
				}
			}
		} catch (Exception e) {
			System.out.println("map covert to bean error：" + e);
		}
		return object;

	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
	public static Map<String, Object> toMap(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性  
				if (!key.equals("class")) {
					// 得到property对应的getter方法  
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(null != value){
						map.put(key, value);
					}
				}

			}
		} catch (Exception e) {
			System.out.println("bean covert to map error：" + e);
		}
		return map;

	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		// 将map转换为bean  
		//transMap2Bean2(mp, person);

		// 将javaBean 转换为map  
		//Map<String, Object> map = transBean2Map(person);
		
		Music music = new Music();
		music.setId(123);
		
		Map<String, Object> map = BeanUtil.toMap(music);
		System.out.println(map);
		
		Music music2 = toBean(map, Music.class);
		System.out.println(music2);
	}
	
}
