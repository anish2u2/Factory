package org.factory.design.abstracts;

import org.factory.design.contracts.CreateMethods;

public abstract class AbstractCreateMethods<T> implements CreateMethods<T> {

	public T createObject() {
		return null;
	}

}
