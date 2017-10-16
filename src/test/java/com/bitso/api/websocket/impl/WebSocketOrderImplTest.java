/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.entity.BitsoResponse;
import com.bitso.entity.OrderSocketResponse;
import com.bitso.entity.RestPayload;
import com.bitso.rest.client.BitsoTicker;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    "com.bitso.rest.client.impl"
})
@PropertySource("classpath:application.properties")
public class WebSocketOrderImplTest {
    
    ApplicationContext applicationContext;
    
    BitsoTicker bitsoTicker = mock(BitsoTicker.class);

//    @Autowired
//    @Qualifier("tradesList")
    private List<BitsoResponse> listBitsoResponse;
    
    @Before
    public void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        ((AnnotationConfigApplicationContext) applicationContext).register(WebSocketOrderImplTest.class);
        ((AnnotationConfigApplicationContext) applicationContext).refresh();
        listBitsoResponse = applicationContext.getBean("tradesList", List.class);
    }
    
    private BitsoResponse createBitsoResponse() {
        BitsoResponse bitsoResponse = new BitsoResponse();
        bitsoResponse.setSuccess(true);
        RestPayload payload = new RestPayload();
        payload.setAsk(String.valueOf(Math.random()));
        payload.setBid(String.valueOf(Math.random()));
        payload.setBook("btc_mxn");
        payload.setCreatedAt(Calendar.getInstance().getTime());
        payload.setHigh(String.valueOf(Math.random()));
        payload.setLast(String.valueOf(Math.random()));
        payload.setLow(String.valueOf(Math.random()));
        payload.setVolume(String.valueOf(Math.random()));
        payload.setVwap(String.valueOf(Math.random()));
        bitsoResponse.setPayload(payload);
        return bitsoResponse;
    }
    
    @Test
    public void testWebSocketConnection() {
        ObjectMapper objMapper = new ObjectMapper();
        boolean end = false;
        BitsoOrdersChannel sC = applicationContext.getBean(BitsoOrdersChannel.class);
        BitsoDiffOrdersChannel sCDO = applicationContext.getBean(BitsoDiffOrdersChannel.class);
        BitsoTradesChannel sT = applicationContext.getBean(BitsoTradesChannel.class);
        try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
            BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext.getBean(BitsoWebSocketOrderObserverImpl.class);
            ((BitsoWebSocketOrderObserverImpl) bitsoWebSocketOrderObserver).bitsoTicker = this.bitsoTicker;
            when(bitsoTicker.getTrades()).thenReturn(createBitsoResponse());
            ((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
            webSocketOrder.openConnection();
            sC.subscribeBitsoChannel();
//            sCDO.subscribeBitsoChannel();
//            sT.subscribeBitsoChannel();
            Thread.sleep(20000);
            webSocketOrder.closeConnection();
            bitsoWebSocketOrderObserver.getMessageReceived().forEach((s) -> {
                
                try {
                    System.out.println("\n message:");
                    OrderSocketResponse mapResult = objMapper.readValue(s, OrderSocketResponse.class);
//                    System.out.println(mapResult);
                    System.out.println("Some (bids: "+mapResult.getOrderPayloadSocketResponse().getBids().size()+",àsks: "+mapResult.getOrderPayloadSocketResponse().getAsks().size()+")");
//                    Set<String> keySet = mapResult.keySet();
//                    keySet.stream().forEach((key) -> {
//                        Object mapValue = mapResult.get(key);
//                       
//                        
//                            System.out.println(key + ":" + mapValue);
//                        
//                    });
                } catch (IOException ex) {
                    Logger.getLogger(WebSocketOrderImplTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            System.out.println("Trades: ");
            listBitsoResponse.forEach(bR -> System.out.println(bR.getPayload().toString()));
            end = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(end);
    }
    
}
