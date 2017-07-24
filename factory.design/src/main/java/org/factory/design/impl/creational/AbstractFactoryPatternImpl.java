package org.factory.design.impl.creational;

import org.factory.design.abstracts.patterns.creational.CompositAbstractFactoryPattern;
import org.factory.design.annotations.Component;
import org.factory.design.contracts.Context;

@Component(name = "abstractFactory")
public class AbstractFactoryPatternImpl extends CompositAbstractFactoryPattern {

	private Context context;

	private AbstractFactoryPatternImpl() {
		factory = this;
	}

	public static void createInstance() {
		if (factory == null)
			new AbstractFactoryPatternImpl();
	}

	public void initFactory(Context appContext) {
		context = appContext;
	}

	public void destroyContext() {
		context.destroy();
		context = null;
	}

	@Override
	protected Object initObject(Class<?> classTypeObject) {
		return null;
	}

	@Override
	protected void finalize() throws Throwable {
		destroyContext();
		super.finalize();
	}

}
