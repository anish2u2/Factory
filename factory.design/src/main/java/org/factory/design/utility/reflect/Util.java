package org.factory.design.utility.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.adapter.framework.files.FilesUtility;
import org.factory.design.annotations.Components;
import org.factory.design.annotations.InjectBean;
import org.factory.design.config.elements.FactoryConfig;
import org.factory.design.contracts.Context;
import org.factory.design.utility.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

public class Util {
	@Autowired
	@Qualifier("factoryContext")
	private static Context context;

	public static Class<?>[] getAllComponentAnnotatedClass(Class<?> composingClass) {
		try {
			Map<Class<?>, List<Class<?>>> componentAnnotatedClasses = getAnnotatedClassMapFromContext();
			if (componentAnnotatedClasses.get(composingClass) == null) {
				componentAnnotatedClasses.put(composingClass, new LinkedList<Class<?>>());
				final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
						false);
				provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
				String packageName = ((FactoryConfig) context.get(AppConstant.APP_CONFIG)).getComponentScan()
						.getPackageName();
				final Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(packageName);
				for (BeanDefinition bean : beanDefinitions) {
					Class<?> clazz = Class.forName(bean.getBeanClassName());
					Components components = clazz.getAnnotation(Components.class);
					if (components != null) {
						componentAnnotatedClasses.get(Components.class).add(clazz);
					}
				}
			}
			if (!componentAnnotatedClasses.get(Components.class).isEmpty()) {
				Class<?>[] arrayOfClass = new Class<?>[componentAnnotatedClasses.get(Components.class).size()];
				int counter = 0;
				for (Object classObject : componentAnnotatedClasses.get(Components.class).toArray()) {
					arrayOfClass[counter] = (Class<?>) classObject;
					counter++;
				}
				return arrayOfClass;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static Map<Class<?>, List<Class<?>>> getAnnotatedClassMapFromContext() {
		Map<Class<?>, List<Class<?>>> componentAnnotatedClasses = (Map<Class<?>, List<Class<?>>>) context
				.get(AppConstant.COMPONENT_ANNOTATED);
		if (componentAnnotatedClasses == null) {
			componentAnnotatedClasses = new HashMap<Class<?>, List<Class<?>>>();

		}
		return componentAnnotatedClasses;
	}

	/*
	 * public static Object createObjectUsingReflection(Class<?>
	 * targetClassType, Class<?>[] componentClasses) { Object targetObject =
	 * targetClassType.newInstance(); try { List<Field> listOfAnnotatedBeans =
	 * getDeclaredFieldsForInjectingBeans(targetClassType); for (Field field :
	 * listOfAnnotatedBeans) {
	 * getDeclaredFieldsForInjectingBeans(field.getType()); } } catch (Exception
	 * ex) { ex.printStackTrace(); } return targetObject; }
	 */

	public static List<Class<?>> findAllInjectableClasses(String packageName) {
		List<Class<?>> listOfClasses = new LinkedList<Class<?>>();
		Map<Class<?>, Set<Class<?>>> listOfClassesContainingAnnotations = FilesUtility
				.getAllClassesThatContainsAnnotatedclass(InjectBean.class, packageName);
		for (Class<?> clazz : listOfClassesContainingAnnotations.keySet()) {
			listOfClasses.addAll(listOfClassesContainingAnnotations.get(clazz));
		}
		return listOfClasses;
	}

	public static Object createObject(Class<?> targetObjectClass) {
		Object object = null;
		try {
			object = targetObjectClass.newInstance();
			List<Field> listOfAnnotatedBeans = getDeclaredFieldsForInjectingBeans(targetObjectClass);
			for (Field field : listOfAnnotatedBeans) {
				field.set(object, createObject(field.getType()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return object;
	}

	public static List<Field> getDeclaredFieldsForInjectingBeans(Class<?> targetClass) {
		List<Field> listOfFieldToBeInjected = new ArrayList<Field>();
		try {
			Field[] fields = targetClass.getDeclaredFields();

			for (Field field : fields) {
				if (field.getType().isAssignableFrom(InjectBean.class)) {
					listOfFieldToBeInjected.add(field);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listOfFieldToBeInjected;
	}

	public Object createProxy(Object actualObject) {
		// Proxy.newProxyInstance(loader, interfaces, h)
		return null;
	}
}
