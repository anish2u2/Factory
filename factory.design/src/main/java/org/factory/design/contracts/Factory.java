package org.factory.design.contracts;

public interface Factory {

	public void initFactory(Context appContext);

	public Object createObject(Class<?> classType);

	public void destroyContext();

}
