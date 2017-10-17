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
import com.bitso.api.service.impl.OrderBookMaintainerServiceImpl;
import com.bitso.controller.TickerController;
import com.bitso.entity.RestResponse;
import com.bitso.rest.client.BitsoTicker;

/**
 *
 * @author Jorge
 */
public class BitsoTickerImplTest {

	ApplicationContext applicationContext;

	/**
	 * Test of getTrades method, of class BitsoTickerImpl.
	 */
	// @Test
	public void testGetTrades() {
		BitsoTicker bitsoTicker = mock(BitsoTickerImpl.class);
		System.out.println("getTrades");
		when(bitsoTicker.getTrades()).thenReturn(new RestResponse());
		RestResponse bResponse = bitsoTicker.getTrades();
		assertNotNull(bResponse);
	}

	@Test
	public void callingRealMethod() {
		TickerController tC = applicationContext.getBean(TickerController.class);
		RestResponse bR = tC.getBistoResponse();
		assertNotNull(bR);
	}

	@Before
	public void setUp() {
		applicationContext = new AnnotationConfigApplicationContext();
		((AnnotationConfigApplicationContext) applicationContext).register(ConfigurationTest.class);
		((AnnotationConfigApplicationContext) applicationContext).refresh();
	}

}
