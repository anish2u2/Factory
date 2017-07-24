package org.factory.design.contracts;

public interface Context {

	public Object get(String key);

	public Object[] get();

	public Object getObject(Object object);

	public void add(String key, Object object);

	public void add(Object object);

	public void destroy();

}
