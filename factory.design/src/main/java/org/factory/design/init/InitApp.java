package org.factory.design.init;

import java.util.LinkedList;
import java.util.List;

import org.adapter.framework.contracts.BeanFactory;
import org.adapter.framework.contracts.InitFramework;
import org.adapter.framework.event.contract.Context;
import org.factory.design.cache.DefaultBeanCache;
import org.factory.design.config.elements.FactoryConfig;
import org.factory.design.config.elements.ProxyObjectCreatorConfig;
import org.factory.design.context.AppContext;
import org.factory.design.contracts.InitFactory;
import org.factory.design.contracts.patterns.creational.Proxy;
import org.factory.design.init.AppInitConfig;
import org.factory.design.loaders.FactoryClassLoader;
import org.factory.design.loaders.SubFactoryClassLoader;
import org.factory.design.reflection.ReflectionUtility;

/*
 * @author Anish Singh
 * 
 * This Class will be used by Adapter Framework to Initialized this framework.
 * 
 */

public class InitApp implements InitFramework {

	private FactoryClassLoader loader = null;

	private List<SubFactoryClassLoader> subFactoryClassLoaders = new LinkedList<SubFactoryClassLoader>();

	public Context init() {
		AppInitConfig config = new AppInitConfig();
		config.initAppConfigs();
		FactoryConfig factoryConfig = config.getConfigurations();
		loader = new FactoryClassLoader();

		try {
			loader.loadClassfromPackage(factoryConfig.getComponentScan().getPackageName());
			System.out.println("Loaded classes:" + loader.getListOfLoadedClass());
			for (ProxyObjectCreatorConfig conf : config.getProxyObjectCreatorConfig()) {
				SubFactoryClassLoader annotationLoader = new SubFactoryClassLoader();
				System.out.println("Start scaning package:" + conf.getRegisterAnnotation().getPackageName());
				annotationLoader.loadClassfromPackage(conf.getRegisterAnnotation().getPackageName());
				InitFactory initSubFactory = (InitFactory) ReflectionUtility
						.createInstance(Class.forName(conf.getProxyObjectCreator().getClazz()));
				annotationLoader.setProxy((Proxy) initSubFactory.init());
				subFactoryClassLoaders.add(annotationLoader);
			}
			config.registerSubCLassLoaders(subFactoryClassLoaders);
			System.out.println("Now Starting creating objects..");
			AppContext.setSubFactoryClassLoaders(subFactoryClassLoaders);
			config.createAnnotatedClassObjects(loader.getListOfLoadedClass());
			System.out.println("Objects created successfully");
			System.out.println("Created objects:" + DefaultBeanCache.getCache().toString());
			return new Context() {

				public BeanFactory getBeanfactory() {
					// TODO Auto-generated method stub
					return new BeanFactory() {

						public Object getBean(Class<?> targetClassType) {
							System.out.println("Finding bean in Factory beans..");
							return AppContext.createInstance().getObject(targetClassType);
						}

						public Object getObject(String qualifier) {
							return AppContext.createInstance().get(qualifier);
						}
					};
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void destroy() {
		loader = null;
	}

}
