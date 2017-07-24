package org.factory.design.init;

import org.adapter.framework.contracts.InitFramework;
import org.adapter.framework.event.contract.Context;
import org.factory.design.cache.DefaultBeanCache;
import org.factory.design.config.elements.FactoryConfig;
import org.factory.design.context.AppContext;
import org.factory.design.init.AppInitConfig;
import org.factory.design.loaders.FactoryClassLoader;

/*
 * @author Anish Singh
 * 
 * This Class will be used by Adapter Framework to Initialized this framework.
 * 
 */

public class InitApp implements InitFramework {

	private FactoryClassLoader loader = null;

	public Context init() {
		AppInitConfig config = new AppInitConfig();
		config.initAppConfigs();
		FactoryConfig factoryConfig = config.getConfigurations();
		loader = new FactoryClassLoader();
		try {
			loader.loadClassfromPackage(factoryConfig.getComponentScan().getPackageName());
			System.out.println("Loaded classes:" + loader.getListOfLoadedClass());
			System.out.println("Now Starting creating objects..");
			config.createAnnotatedClassObjects(loader.getListOfLoadedClass());
			System.out.println("Objects created successfully");
			System.out.println("Created objects:" + DefaultBeanCache.getCache().toString());
			return (Context) AppContext.createInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void destroy() {
		loader = null;
	}

}
