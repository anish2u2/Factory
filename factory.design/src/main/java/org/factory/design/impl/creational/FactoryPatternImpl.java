package org.factory.design.impl.creational;

import java.util.Map;
import java.util.WeakHashMap;

import org.factory.design.abstracts.patterns.creational.AbstractFactoryPattern;
import org.factory.design.annotations.Component;
import org.factory.design.annotations.Inject;
import org.factory.design.contracts.Context;
import org.factory.design.utility.reflect.Util;

@Component(name = "factoryPattern")
public class FactoryPatternImpl extends AbstractFactoryPattern {

	private Map<Class<?>, Object> instanceHolder = new WeakHashMap<Class<?>, Object>();

	@Inject(qualifier = "factoryContext")
	private Context context;

	public FactoryPatternImpl() {
		factory = this;
	}

	public static void createInstance() {
		if (factory == null)
			factory = new FactoryPatternImpl();
	}

	public void initFactory(Context appContext) {

	}

	public void destroyContext() {
		context.destroy();
		context = null;
	}

	@Override
	protected Object initObject(Class<?> classTypeObject) {
		Object targetObject = null;
		try {
			if (instanceHolder.containsKey(classTypeObject)) {
				return instanceHolder.get(classTypeObject);
			}
			targetObject = Util.createObject(classTypeObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return targetObject;
	}

	@Override
	protected void finalize() throws Throwable {
		destroyContext();
		super.finalize();
	}

}
