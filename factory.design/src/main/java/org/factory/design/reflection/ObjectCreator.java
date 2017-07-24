package org.factory.design.reflection;

import org.factory.design.annotations.Component;
import org.factory.design.annotations.Components.DesignType;
import org.factory.design.annotations.Inject;
import org.factory.design.contracts.Factory;
import org.factory.design.contracts.Patterns;

@Component(name = "objectCreator")
public class ObjectCreator {

	@Inject(qualifier = "pattern")
	private Patterns pattern;

	public void initAppObjectCreation() {
		//List<Class<?>> listOfInjectableClasses = Util.findAllInjectableClasses();
		Factory factory = pattern.getDesignPatternFactory(DesignType.CREATIONAL);

	}

}
