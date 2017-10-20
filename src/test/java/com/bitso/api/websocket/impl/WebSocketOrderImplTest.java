/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bitso.api.main.test.ConfigurationTest;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.entity.RestPayload;
import com.bitso.entity.RestResponse;
import com.bitso.entity.RestTickerPayload;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTicker;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import javax.validation.Payload;

/**
 *
 * @author Jorge
 */
public class WebSocketOrderImplTest {
    
    ApplicationContext applicationContext;
    
    BitsoTicker bitsoTicker = mock(BitsoTicker.class);

    private List<RestResponse> listBitsoResponse;
    
    private Set<WebSocketPayload> listTopAsksTrades;
    
    private Set<WebSocketPayload> listTopBidsTrades;
    
    @Before
    public void setUp() {
        applicationContext = new AnnotationConfigApplicationContext();
        ((AnnotationConfigApplicationContext) applicationContext).register(ConfigurationTest.class);
        ((AnnotationConfigApplicationContext) applicationContext).refresh();
        listBitsoResponse = applicationContext.getBean("tradesList", List.class);
        listTopAsksTrades = applicationContext.getBean("topAsks",Set.class);
        listTopBidsTrades = applicationContext.getBean("topBids",Set.class);
    }
    
    private RestResponse createBitsoResponse() {
        RestResponse bitsoResponse = new RestResponse();
        bitsoResponse.setSuccess(true);
        RestTickerPayload payload = new RestTickerPayload();
        payload.setAsk(String.valueOf(Math.random()));
        payload.setBid(String.valueOf(Math.random()));
        payload.setBook("btc_mxn");
        payload.setCreatedAt(Calendar.getInstance().getTime());
        payload.setHigh(String.valueOf(Math.random()));
        payload.setLast(String.valueOf(Math.random()));
        payload.setLow(String.valueOf(Math.random()));
        payload.setVolume(String.valueOf(Math.random()));
        payload.setVwap(String.valueOf(Math.random()));
        List<RestPayload> listPayload = new ArrayList<RestPayload>();
        listPayload.add(payload);
        bitsoResponse.setPayload(listPayload);
        return bitsoResponse;
    }
    
    @Test
    public void testWebSocketConnection() {
        ObjectMapper objMapper = new ObjectMapper();
        boolean end = false;
        BitsoChannelSubscriber orderChannel = applicationContext.getBean(BitsoOrdersChannel.class);
        BitsoChannelSubscriber diffOrderChannel = applicationContext.getBean(BitsoDiffOrdersChannel.class);
        BitsoChannelSubscriber tradeChannel = applicationContext.getBean(BitsoTradesChannel.class);
        try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
            BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext.getBean(BitsoWebSocketOrderObserverImpl.class);
            ((BitsoWebSocketOrderObserverImpl) bitsoWebSocketOrderObserver).bitsoTicker = this.bitsoTicker;
            when(bitsoTicker.getTrades()).thenReturn(createBitsoResponse());
            ((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
            webSocketOrder.openConnection();
            orderChannel.subscribeBitsoChannel();
//            sCDO.subscribeBitsoChannel();
//            sT.subscribeBitsoChannel();
            Thread.sleep(20000);
            
            webSocketOrder.closeConnection();
            bitsoWebSocketOrderObserver.getMessageReceived().forEach((s) -> {
               
            });
			assertTrue(listBitsoResponse.size() > 0);
            end = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(end);
    }
    
}
