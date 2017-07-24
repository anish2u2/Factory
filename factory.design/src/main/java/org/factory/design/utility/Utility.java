package org.factory.design.utility;

import java.io.File;
import java.io.InputStream;

public class Utility {

	public static InputStream findResourcesInClassPath(String fileName) {
		try {
			return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		} catch (Exception ex) {
			try {
				return Thread.currentThread().getContextClassLoader().getResourceAsStream(File.separator + fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
