/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.entity.BitsoResponse;
import com.bitso.entity.Payload;
import com.bitso.rest.client.BitsoTicker;
import java.util.Calendar;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Jorge
 */
@ComponentScan({"com.bitso.api.websocket.impl",
    "com.bitso.configuration",
//    "com.bitso.entity",
    "com.bitso.rest.client.impl"
    })
@PropertySource("classpath:application.properties")
public class WebSocketOrderImplTest {

    ApplicationContext applicationContext;
    
    BitsoTicker bitsoTicker = mock(BitsoTicker.class);

    @Before
    public void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        ((AnnotationConfigApplicationContext) applicationContext).register(WebSocketOrderImplTest.class);
        ((AnnotationConfigApplicationContext) applicationContext).refresh();
    }

    @Test
    public void testWebSocketConnection() {
        boolean end = false;
        
        BitsoResponse bitsoResponse = new BitsoResponse();
        bitsoResponse.setSuccess(true);
        Payload payload = new Payload();
        payload.setAsk( String.valueOf(Math.random()));
        payload.setBid(String.valueOf(Math.random()));
        payload.setBook("btc_mxn");
        payload.setCreatedAt(Calendar.getInstance().getTime());
        payload.setHigh(String.valueOf(Math.random()));
        payload.setLast(String.valueOf(Math.random()));
        payload.setLow(String.valueOf(Math.random()));
        payload.setVolume(String.valueOf(Math.random()));
        payload.setVwap(String.valueOf(Math.random()));
        bitsoResponse.setPayload(payload);
        
        BitsoOrdersChannel sC = applicationContext.getBean(BitsoOrdersChannel.class);
        BitsoDiffOrdersChannel sCDO = applicationContext.getBean(BitsoDiffOrdersChannel.class);
        try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
            BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext.getBean(BitsoWebSocketOrderObserverImpl.class);
            ((BitsoWebSocketOrderObserverImpl)bitsoWebSocketOrderObserver).bitsoTicker=this.bitsoTicker;
            when(bitsoTicker.getTrades()).thenReturn(bitsoResponse);
            ((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
            webSocketOrder.openConnection();
            sC.subscribeBitsoChannel();
            sCDO.subscribeBitsoChannel();
            Thread.sleep(20000);
            webSocketOrder.closeConnection();
            
            for(String s:bitsoWebSocketOrderObserver.getMessageReceived()){
                System.out.println("message: "+s);
            }
            
            System.out.println("Trades: ");
            for(BitsoResponse bR: bitsoWebSocketOrderObserver.getListBitsoRespone()){
                System.out.println(bR.getPayload().toString());
            }
        
            end = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(end);
    }

}
