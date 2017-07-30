package org.factory.design.contracts;

import org.factory.design.contracts.patterns.creational.Proxy;

public interface InitFactory {

	public Proxy init();

	public void destroy();

}
