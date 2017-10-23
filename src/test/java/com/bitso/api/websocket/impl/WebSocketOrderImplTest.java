/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bitso.api.main.test.ConfigurationTest;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTrade;

/**
 *
 * @author Jorge
 */
public class WebSocketOrderImplTest {

	ApplicationContext applicationContext;

	BitsoTrade bitsoTrade = mock(BitsoTrade.class);

	private List<TradePayload> listBitsoResponse;

	private Set<WebSocketPayload> listTopAsksTrades;

	private Set<WebSocketPayload> listTopBidsTrades;
	
	private DataConfiguration dataConfiguration;

	private Integer totalRecentTrades;

	@Before
	public void setUp() {
		applicationContext = new AnnotationConfigApplicationContext();
		((AnnotationConfigApplicationContext) applicationContext).register(ConfigurationTest.class);
		((AnnotationConfigApplicationContext) applicationContext).refresh();
		dataConfiguration = applicationContext.getBean(DataConfiguration.class);
		listBitsoResponse = applicationContext.getBean("listTradePayload", List.class);
		listTopAsksTrades = applicationContext.getBean("topAsks", Set.class);
		listTopBidsTrades = applicationContext.getBean("topBids", Set.class);
		totalRecentTrades = applicationContext.getBean("totalRecentTrades", Integer.class);
		totalRecentTrades = 10;
	}

	private TradeRestResponse createBitsoResponse() {
		TradeRestResponse bitsoResponse = new TradeRestResponse();
		bitsoResponse.setSuccess(true);
		List<TradePayload> listTradePayload = new ArrayList<>();
		TradePayload payload;

		for (int i = 0; i < 16; i++) {
			payload = new TradePayload();
			payload.setBook("btc_mxn");
			payload.setCreatedAt(Calendar.getInstance().getTime());
			payload.setAmount(String.valueOf(Math.random()));
			payload.setMakerSide("buy");
			payload.setPrice(String.valueOf(Math.random()));
			payload.setTid(String.valueOf(Math.random()));

			listTradePayload.add(payload);

		}

		bitsoResponse.setTradePayload(listTradePayload);

		return bitsoResponse;
	}

	@Test
	public void testWebSocketConnection() {
		boolean end = false;
		BitsoChannelSubscriber orderChannel = applicationContext.getBean(BitsoOrdersChannel.class);
		BitsoChannelSubscriber diffOrderChannel = applicationContext.getBean(BitsoDiffOrdersChannel.class);
		BitsoChannelSubscriber tradeChannel = applicationContext.getBean(BitsoTradesChannel.class);
		try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
			BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext
					.getBean(BitsoWebSocketOrderObserverImpl.class);
			((BitsoWebSocketOrderObserverImpl) bitsoWebSocketOrderObserver).bitsoTrade = this.bitsoTrade;
			when(bitsoTrade.getRecentTrades()).thenReturn(createBitsoResponse());
			((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
			((BitsoWebSocketOrderObserverImpl) bitsoWebSocketOrderObserver).totalRecentTrades = 10;
			webSocketOrder.openConnection();
//			orderChannel.subscribeBitsoChannel();
//			 diffOrderChannel.subscribeBitsoChannel();
			 tradeChannel.subscribeBitsoChannel();

			Runnable task = () -> {
				
				System.out.println("display " + totalRecentTrades + " of "
						+listBitsoResponse.size());
				listBitsoResponse.forEach(new Consumer<TradePayload>() {
					int i = 0;

					public void accept(TradePayload tradeRestResponse) {
						if (i < totalRecentTrades) {
							System.out.println(tradeRestResponse);
						}
					}
				
				});
				
			};
//			task.run();
//			ExecutorService executor = Executors.newSingleThreadExecutor();
//			executor.execute(task);
			Thread.sleep(20000);
//                        while(Math.random()>0){}
//			executor.shutdown();
//			executor.awaitTermination(10, TimeUnit.SECONDS);
			webSocketOrder.closeConnection();
//			bitsoWebSocketOrderObserver.getMessageReceived().forEach((s) -> {
//
//			});
			listBitsoResponse=bitsoWebSocketOrderObserver.getListBitsoRespone();
//			listBitsoResponse=dataConfiguration.getListTradePayload();
			assertTrue(listBitsoResponse.size() > 0);
			end = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(end);
	}

}
