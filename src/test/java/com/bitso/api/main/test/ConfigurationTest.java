package com.bitso.api.main.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan({ "com.bitso.api.websocket.impl", "com.bitso.configuration", "com.bitso.api.util",
		"com.bitso.api.service.impl", "com.bitso.controller", "com.bitso.rest.client.impl" })
@PropertySource("classpath:application.properties")
public class ConfigurationTest {

}