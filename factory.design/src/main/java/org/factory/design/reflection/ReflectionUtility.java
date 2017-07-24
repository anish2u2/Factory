package org.factory.design.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import org.factory.design.cache.DefaultBeanCache;
import org.factory.design.contracts.BeanCache;

public class ReflectionUtility {

	public static void createCacheObjectOfAnnotatedBeans(List<Class<?>> annotatedClasses, BeanCache cache,
			List<Class<?>> listOfClazz) {
		for (Class<?> clazz : listOfClazz) {
			Annotation[] annotations = clazz.getDeclaredAnnotations();
			System.out.println("Finding Annotation for Class:" + clazz.getName());
			for (Annotation annotation : annotations) {
				System.out.println("Annotations:" + annotation.annotationType().getName());
				if (annotatedClasses.contains(annotation.annotationType())) {
					createCacheObjects(cache, clazz);
				}

			}
		}
	}

	public static void createCacheObjects(BeanCache cache, Class<?> clazz) {
		if (cache.findBean(clazz) == null)
			cache.addToCache(clazz, createInstance(clazz));

	}

	public static void createObjectAndPutInCache(BeanCache cache, List<Class<?>> listOfClazz) {
		for (Class<?> clazz : listOfClazz) {
			if (cache.findBean(clazz) == null)
				cache.addToCache(clazz, createInstance(clazz));
		}
	}

	public static Object createInstance(Class<?> clazz) {
		try {

			Constructor<?>[] constructors = clazz.getDeclaredConstructors();
			for (Constructor<?> constructor : constructors) {
				if (constructor.isAccessible()) {
					return clazz.newInstance();
				} else {
					constructor.setAccessible(true);
					Class<?>[] parameters = constructor.getParameterTypes();
					if (parameters.length > 0) {
						ReflectionUtility.createObjectAndPutInCache(DefaultBeanCache.getCache(),
								Arrays.asList(parameters));
						Object[] params = new Object[parameters.length];
						int counter = 0;
						for (Class<?> param : parameters) {
							params[counter] = DefaultBeanCache.getCache().findBean(param);
						}
						return constructor.newInstance(params);
					} else {
						return constructor.newInstance();
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public static boolean hasDefaultPublicConstructor(Constructor<?>[] constructors) {
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterTypes().length == 0 && constructor.isAccessible()) {
				return true;
			}
		}
		return false;
	}

}
