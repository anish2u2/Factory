package org.factory.design.init;

import java.util.LinkedList;
import java.util.List;

import org.factory.design.annotations.Component;
import org.factory.design.annotations.Components;
import org.factory.design.annotations.Inject;
import org.factory.design.annotations.InjectBean;
import org.factory.design.annotations.Repository;
import org.factory.design.annotations.Service;
import org.factory.design.cache.DefaultBeanCache;
import org.factory.design.config.XMLFileHandler;
import org.factory.design.config.elements.FactoryConfig;
import org.factory.design.reflection.ReflectionUtility;
import org.factory.design.utility.AppConstant;

/*
 * @author Anish Singh
 * 
 * This file is responsible for init the application utility configs. 
 * 
 */

@Component(name = "initAppListener")
public class AppInitConfig {

	private FactoryConfig config = null;

	public void initAppConfigs() {
		config = (FactoryConfig) XMLFileHandler.unmarshal(AppConstant.FACTORY_CONFIG_FILE, FactoryConfig.class);

	}

	public void createAnnotatedClassObjects(List<Class<?>> listOfLoadedClasses) {
		List<Class<?>> listOfAnnotations = new LinkedList<Class<?>>();
		listOfAnnotations.add(Component.class);
		listOfAnnotations.add(Components.class);
		listOfAnnotations.add(Inject.class);
		listOfAnnotations.add(InjectBean.class);
		listOfAnnotations.add(Repository.class);
		listOfAnnotations.add(Service.class);
		ReflectionUtility.createCacheObjectOfAnnotatedBeans(listOfAnnotations, DefaultBeanCache.getCache(),
				listOfLoadedClasses);
	}

	public FactoryConfig getConfigurations() {
		return config;
	}
}
