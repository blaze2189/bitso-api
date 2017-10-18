/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.rest.client.impl;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bitso.api.main.test.ConfigurationTest;
import com.bitso.controller.TradeController;
import com.bitso.entity.RestResponse;
import com.bitso.entity.TradeRestResponse;
import com.bitso.rest.client.BitsoTrade;

/**
 *
 * @author Jorge
 */
public class BitsoTradeImplTest {

	ApplicationContext applicationContext;

	@Test
	public void callingRealMethod() {
		TradeController tradeController = applicationContext.getBean(TradeController.class);
		TradeRestResponse bR = tradeController.getBistoResponse();
		assertNotNull(bR);
	}

	@Before
	public void setUp() {
		applicationContext = new AnnotationConfigApplicationContext();
		((AnnotationConfigApplicationContext) applicationContext).register(ConfigurationTest.class);
		((AnnotationConfigApplicationContext) applicationContext).refresh();
	}

}
