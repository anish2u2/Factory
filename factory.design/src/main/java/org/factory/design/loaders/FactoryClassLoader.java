package org.factory.design.loaders;

import java.util.LinkedList;
import java.util.List;

import org.adapter.framework.files.FilesUtility;

public class FactoryClassLoader extends ClassLoader {

	private List<Class<?>> listOfLoadedClass = new LinkedList<Class<?>>();

	public Package[] retrieveAllPackage() {
		return super.getPackages();
	}

	public void loadClassfromPackage(String packageName) throws Exception {
		System.out.println("Loading classes from package:" + packageName);
		for (String classFiles : FilesUtility.readFileNamesOfTypeFromJarInPackage(packageName, null, ".class")) {
			listOfLoadedClass.add(super.loadClass(classFiles.substring(0, classFiles.length() - 6)));
		}
	}

	public List<Class<?>> getListOfLoadedClass() {
		return this.listOfLoadedClass;
	}

}
