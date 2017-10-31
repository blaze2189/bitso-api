package com.bitso.api.main.test;

import java.net.URL;

import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan({ "com.bitso.api.websocket.impl", "com.bitso.configuration", "com.bitso.api.util",
		"com.bitso.api.service.impl", "com.bitso.controller", "com.bitso.rest.client.impl" })
@PropertySource("classpath:application.properties")
public class ConfigurationTest {

	@Test
	public void routesTest() {
		System.out.println("adjfadlfs");
		Class claz = getClass();
		ClassLoader clazLoader = claz.getClassLoader();
		URL url = clazLoader.getResource("");
//		String classLoader =getClass().getClassLoader().getResource("./FxExample.java");
		String classLoader =url.toString();
		System.out.println("location? "+ classLoader);
		
	}
	
}