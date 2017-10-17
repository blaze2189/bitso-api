package com.bitso.api.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bitso.api.main.test.ConfigurationTest;
import com.bitso.api.service.OrderBookMaintainerService;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.api.websocket.impl.BitsoTradesChannel;
import com.bitso.api.websocket.impl.BitsoWebSocketOrderObserverImpl;
import com.bitso.api.websocket.impl.WebSocketConnectionImpl;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.RestResponse;

public class OrderBookMaintainerServiceImplTest {

	private ApplicationContext applicationContext;

	private OrderBookMaintainerService orderBookMaintainerService;

	private List<DiffOrdersWocketResponse> recentBids;
	private List<DiffOrdersWocketResponse> recentAsks;
	private Integer lastSequenceTrade;

	@Before
	public void setUp() {
		applicationContext = new AnnotationConfigApplicationContext();
		((AnnotationConfigApplicationContext) applicationContext).register(ConfigurationTest.class);
		((AnnotationConfigApplicationContext) applicationContext).refresh();
		orderBookMaintainerService = applicationContext.getBean(OrderBookMaintainerServiceImpl.class);
	}

	@Test
	public void updateOrderBook() {
		boolean end = false;
		recentBids = applicationContext.getBean("recentBids", List.class);
		recentAsks = applicationContext.getBean("recentAsks", List.class);
		lastSequenceTrade = applicationContext.getBean("lastSequenceTrade", Integer.class);
		RestResponse orderBook = applicationContext.getBean("orderBook", RestResponse.class);
		BitsoChannelSubscriber tradeChannel = applicationContext.getBean(BitsoTradesChannel.class);
		try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
			BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext
					.getBean(BitsoWebSocketOrderObserverImpl.class);
			((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);

			webSocketOrder.openConnection();
			orderBookMaintainerService.updateOrderBook();
			Thread t = new Thread(new OrderMainteiner());
			t.start();
			Thread.sleep(20000);
			t.destroy();
			webSocketOrder.closeConnection();
			end = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(end);
		assertNotNull(orderBook);

	}

	private class OrderMainteiner implements Runnable {

		@Override
		public void run() {
			System.out.println("running a thread");
			while(true) {
			List<DiffOrdersWocketResponse>newAsks= recentAsks.stream().filter(ask -> ask.getSequence()>lastSequenceTrade).collect(Collectors.toList());
			List<DiffOrdersWocketResponse>newBids= recentBids.stream().filter(bid -> bid.getSequence()>lastSequenceTrade).collect(Collectors.toList());
			System.out.println("asks");
			//newAsks.forEach(ask-> System.out.println(ask.getSequence()));
			System.out.println(newAsks.size());
			System.out.println("bids");
			System.out.println(newBids.size());
//			newBids.forEach(bid-> System.out.println(bid.getSequence()));
					}
		}

	}

}
