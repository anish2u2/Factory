package org.factory.design.abstracts.patterns.creational;

import org.factory.design.contracts.Factory;
import org.factory.design.contracts.patterns.creational.AbstractFactoryPattern;

public abstract class CompositAbstractFactoryPattern implements AbstractFactoryPattern {

	protected static Factory factory;

	protected abstract Object initObject(Class<?> classTypeObject);

	public Object createObject(Class<?> classType) {
		return initObject(classType);
	}

}
