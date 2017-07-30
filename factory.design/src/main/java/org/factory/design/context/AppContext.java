package org.factory.design.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adapter.framework.contracts.BeanFactory;
import org.factory.design.cache.DefaultBeanCache;
import org.factory.design.contracts.BeanCache;
import org.factory.design.contracts.Context;
import org.factory.design.loaders.SubFactoryClassLoader;
import org.springframework.context.annotation.Bean;

/*
 *@author Anish Singh
 *
 * This class will be used as a context for the current flow(i.e for current thread).
 * 
 * further release we will make it for current thread.  
 */

public class AppContext implements Context {

	private Map<Object, Object> context;

	private static Context appContext;

	private static List<SubFactoryClassLoader> subFactoryClassLoaders;

	public BeanCache beanCache = DefaultBeanCache.getCache();

	private AppContext() {
		context = new HashMap<Object, Object>();
	}

	@Bean(name = "factoryContext")
	public static Context createInstance() {
		if (appContext == null)
			appContext = new AppContext();
		return appContext;
	}

	public void add(Object object) {

	}

	public void destroy() {
		context.clear();
		context = null;
	}

	public Object get(String key) {
		return context.get(key);
	}

	public Object[] get() {
		return context.entrySet().toArray();
	}

	public Object getObject(Object object) {
		Object proxyObject = findInSubLoaders(object);
		if (proxyObject != null)
			return proxyObject;
		return context.get(object);
	}

	public void add(String key, Object object) {
		context.put(key, object);
	}

	private Object findInSubLoaders(Object classObject) {
		Object object = null;
		for (SubFactoryClassLoader subFactoryClassLoader : subFactoryClassLoaders) {
			object = subFactoryClassLoader.getProxy().getProxyBean((Class<?>) classObject);
			if (object != null) {
				beanCache.addToCache((Class<?>) classObject, object);
				return object;
			}
		}
		return object;
	}

	public static List<SubFactoryClassLoader> getSubFactoryClassLoaders() {
		return subFactoryClassLoaders;
	}

	public static void setSubFactoryClassLoaders(List<SubFactoryClassLoader> subFactoryClassLoaders) {
		AppContext.subFactoryClassLoaders = subFactoryClassLoaders;
	}

}
