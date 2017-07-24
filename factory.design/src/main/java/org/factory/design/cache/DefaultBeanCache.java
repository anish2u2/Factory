package org.factory.design.cache;

import java.util.Map;

import org.factory.design.collection.CollectionFactory;
import org.factory.design.contracts.BeanCache;
import org.factory.design.exception.BeanQualifierException;

public class DefaultBeanCache implements BeanCache {

	private static BeanCache cache;

	private static final Key key = new Key();

	@SuppressWarnings("unchecked")
	private static final Map<Key, Object> defaultBeanCache = CollectionFactory.createConcurrentMap(10);

	public Object findBean(Class<?> clazz) {
		Object obj = null;
		synchronized (key) {
			key.setClazz(clazz);
			obj = defaultBeanCache.get(key);
		}
		return obj;
	}

	private DefaultBeanCache() {

	}

	public static BeanCache getCache() {
		if (cache == null)
			cache = new DefaultBeanCache();
		return cache;
	}

	public Object findByName(String name) {
		synchronized (key) {
			return defaultBeanCache.get(name);
		}
	}

	public void addToCache(Class<?> clazz, Object object) {
		Key mapKey = new Key();
		mapKey.setClazz(clazz);
		defaultBeanCache.put(mapKey, object);
	}

	public void addObjectToCache(String name, Object object) {
		synchronized (key) {
			key.setQualifierName(name);
			if (!checkBeanAndThrowError(key)) {
				Key mapKey = new Key();
				mapKey.setQualifierName(name);
				defaultBeanCache.put(mapKey, object);
			}
		}
	}

	private boolean checkBeanAndThrowError(Key key) {
		if (key.getQualifierName() != null && defaultBeanCache.get(key) != null) {
			throw new BeanQualifierException(
					"bean Qualifier name should have to be different as there's exists another bean with same qualifier.");
		}
		return false;
	}

	public static class Key {

		private String qualifierName;

		private Class<?> clazz;

		public String getQualifierName() {
			return qualifierName;
		}

		public void setQualifierName(String qualifierName) {
			this.qualifierName = qualifierName;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj.getClass().isAssignableFrom(clazz)) {
				return (obj.getClass().equals(clazz));
			} else if (obj.getClass().isAssignableFrom(qualifierName.getClass())) {
				return obj.equals(qualifierName);
			}
			return false;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return defaultBeanCache.toString();
	}
}
