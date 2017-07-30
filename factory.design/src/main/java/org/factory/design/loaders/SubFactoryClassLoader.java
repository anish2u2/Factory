package org.factory.design.loaders;

import java.util.LinkedList;
import java.util.List;

import org.adapter.framework.files.FilesUtility;
import org.factory.design.contracts.patterns.creational.Proxy;

public class SubFactoryClassLoader extends ClassLoader {

	private List<Class<?>> listOfLoadedClass = new LinkedList<Class<?>>();

	private Proxy proxy;

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

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

}
