/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.rest.client.impl;

import com.bitso.controller.TickerController;
import com.bitso.entity.BitsoResponse;
import com.bitso.rest.client.BitsoTicker;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Jorge
 */
@ComponentScan({"com.bitso.controller",
    "com.bitso.configuration",
    "com.bitso.entity",
    "com.bitso.rest.client.impl"})
public class BitsoTickerImplTest {

    

    /**
     * Test of getTrades method, of class BitsoTickerImpl.
     */
    @Test
    public void testGetTrades() {
        BitsoTicker bitsoTicker = mock(BitsoTickerImpl.class);
        System.out.println("getTrades");
        when(bitsoTicker.getTrades()).thenReturn(new BitsoResponse());
        BitsoResponse bResponse = bitsoTicker.getTrades();
        assertNotNull(bResponse);
    }

    @Test
    public void callingRealMethod() {
        ApplicationContext aC = new AnnotationConfigApplicationContext();
        ((AnnotationConfigApplicationContext) aC).register(com.bitso.rest.client.impl.BitsoTickerImplTest.class);
        ((AnnotationConfigApplicationContext) aC).refresh();
        TickerController tC = aC.getBean(TickerController.class);
        BitsoResponse bR= tC.getBistoResponse();
        assertNotNull(bR);
    }

}
