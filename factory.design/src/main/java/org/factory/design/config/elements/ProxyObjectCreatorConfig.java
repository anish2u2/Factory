package org.factory.design.config.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProxyObjectCreatorConfig {

	@XmlElement(name = "register-annotation")
	private RegisterAnnotation registerAnnotation;

	@XmlElement(name = "proxy-creator")
	private ProxyObjectCreator proxyObjectCreator;

	public RegisterAnnotation getRegisterAnnotation() {
		return registerAnnotation;
	}

	public void setRegisterAnnotation(RegisterAnnotation registerAnnotation) {
		this.registerAnnotation = registerAnnotation;
	}

	public ProxyObjectCreator getProxyObjectCreator() {
		return proxyObjectCreator;
	}

	public void setProxyObjectCreator(ProxyObjectCreator proxyObjectCreator) {
		this.proxyObjectCreator = proxyObjectCreator;
	}

}
