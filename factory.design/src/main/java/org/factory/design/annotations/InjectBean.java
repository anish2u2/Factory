package org.factory.design.annotations;

/*
 * @author Anish Singh 
 * 
 * This will be used for custom injection of Objects from Factories.
 *  
 */

public @interface InjectBean {
	public enum Pattern {
		FACTORY, ABSTRACTFACTORY
	}

	public Pattern pattern() default Pattern.FACTORY;
}
