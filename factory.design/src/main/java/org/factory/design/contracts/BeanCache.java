package org.factory.design.contracts;

public interface BeanCache {

	public Object findBean(Class<?> clazz);

	public Object findByName(String name);

	public void addToCache(Class<?> clazz, Object object);

	public void addObjectToCache(String name, Object object);
}
