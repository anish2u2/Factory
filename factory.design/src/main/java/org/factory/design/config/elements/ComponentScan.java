package org.factory.design.config.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ComponentScan {

	@XmlAttribute(name = "package", required = true)
	private String packageName;

	public String getPackageName() {
		return packageName;
	}
}
