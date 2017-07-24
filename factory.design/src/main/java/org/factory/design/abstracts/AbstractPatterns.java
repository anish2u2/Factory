package org.factory.design.abstracts;

import java.util.Map;
import java.util.WeakHashMap;

import org.factory.design.annotations.Components;
import org.factory.design.annotations.Components.DesignType;
import org.factory.design.contracts.Factory;
import org.factory.design.contracts.Patterns;
import org.factory.design.impl.creational.FactoryPatternImpl;

public abstract class AbstractPatterns implements Patterns {
	
	protected Map<Components.DesignType, Factory> initializedFactories = new WeakHashMap<Components.DesignType, Factory>();

	public Factory getDesignPatternFactory(DesignType designType) {
		Factory factory = null;
		if (DesignType.CREATIONAL == designType) {
			factory = new FactoryPatternImpl();
			initializedFactories.put(designType, factory);
		}
		return factory;
	}

}
