package org.factory.design.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/*
 * @author Anish Singh
 * 
 * This Annotation is being used to inject beans.
 * 
 */

@Target(ElementType.FIELD)
public @interface Inject {

	String qualifier() default "";

}
