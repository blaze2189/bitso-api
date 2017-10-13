/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.api.websocket.WebSocketConnection;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Jorge
 */
@ComponentScan({"com.bitso.api.websocket.impl",
    "com.bitso.configuration"})
@PropertySource("classpath:application.properties")
public class WebSocketOrderImplTest {

    ApplicationContext applicationContext;

    @Before
    public void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        ((AnnotationConfigApplicationContext) applicationContext).register(WebSocketOrderImplTest.class);
        ((AnnotationConfigApplicationContext) applicationContext).refresh();
    }

    @Test
    public void testWebSocketConnection() {
        boolean end = false;
        BitsoOrdersChannel sC = applicationContext.getBean(BitsoOrdersChannel.class);
        BitsoDiffOrdersChannel sCDO = applicationContext.getBean(BitsoDiffOrdersChannel.class);
        try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
            BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext.getBean(BitsoWebSocketOrderObserverImpl.class);
            ((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
            webSocketOrder.openConnection();
            sC.subscribeBitsoChannel();
            sCDO.subscribeBitsoChannel();
            Thread.sleep(20000);
            webSocketOrder.closeConnection();
            end = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(end);
    }

}
