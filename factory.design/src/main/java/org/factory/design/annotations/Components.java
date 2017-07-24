package org.factory.design.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @author Anish Singh
 * 
 * This annotations is used to read the component classes for creating object of components objects.
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Components {

	public enum DesignType {
		CREATIONAL, BEHAVIOURAL, STRUCTURAL
	}

	DesignType getDesignType() default DesignType.CREATIONAL;

	Class<?> compositClass();

}
