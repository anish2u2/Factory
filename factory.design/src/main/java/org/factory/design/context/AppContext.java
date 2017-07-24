package org.factory.design.context;

import java.util.HashMap;
import java.util.Map;

import org.adapter.framework.contracts.BeanFactory;
import org.factory.design.cache.DefaultBeanCache;
import org.factory.design.contracts.BeanCache;
import org.factory.design.contracts.Context;
import org.springframework.context.annotation.Bean;

/*
 *@author Anish Singh
 *
 * This class will be used as a context for the current flow(i.e for current thread).
 * 
 * further release we will make it for current thread.  
 */

public class AppContext implements Context, org.adapter.framework.event.contract.Context {

	private Map<Object, Object> context;

	private static Context appContext;

	private BeanFactory beanFactory = null;

	public BeanCache beanCache = DefaultBeanCache.getCache();

	private AppContext() {
		context = new HashMap<Object, Object>();
		beanFactory = new BeanFactory() {

			public Object getBean(Class<?> targetClassType) {

				return beanCache.findBean(targetClassType);
			}

			public Object getObject(String qualifier) {
				return beanCache.findByName(qualifier);
			}
		};
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
		return context.get(object);
	}

	public void add(String key, Object object) {
		context.put(key, object);

	}

	public BeanFactory getBeanfactory() {

		return beanFactory;
	}

}
