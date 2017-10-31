package com.bitso.api.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bitso.api.main.test.ConfigurationTest;
import com.bitso.api.service.OrderService;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.BitsoWebSocketObserver;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.api.websocket.impl.BitsoDiffOrdersChannel;
import com.bitso.api.websocket.impl.BitsoWebSocketObserverImpl;
import com.bitso.api.websocket.impl.WebSocketConnectionImpl;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.OrderBookRestResponse;

public class OrderServiceImplTest {

	private ApplicationContext applicationContext;

	@Before
	public void setUp() {
		applicationContext = new AnnotationConfigApplicationContext();
		((AnnotationConfigApplicationContext) applicationContext).register(ConfigurationTest.class);
		((AnnotationConfigApplicationContext) applicationContext).refresh();
	}

	@Test
	public void updateOrderBook() {
		BitsoChannelSubscriber bitsoOrdersChannel;
		DataConfiguration dataConfiguration;
		boolean end = false;
		dataConfiguration = applicationContext.getBean(DataConfiguration.class);
		List<DiffOrdersWocketResponse> recentBids = dataConfiguration.getRecentBids();
		List<DiffOrdersWocketResponse> recentAsks = dataConfiguration.getRecentAsks();
		Integer lastSequenceTrade = dataConfiguration.getLastSequenceTrade();
		OrderBookRestResponse orderBook = applicationContext.getBean("orderBook", OrderBookRestResponse.class);
		OrderService orderBookMaintainerService = applicationContext.getBean(OrderServiceImpl.class);
		// BitsoChannelSubscriber tradeChannel =
		// applicationContext.getBean(BitsoTradesChannel.class);
		try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
			BitsoWebSocketObserver bitsoWebSocketOrderObserver = applicationContext
					.getBean(BitsoWebSocketObserverImpl.class);
			bitsoOrdersChannel = applicationContext.getBean(BitsoDiffOrdersChannel.class);
			((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
			webSocketOrder.openConnection();
			bitsoOrdersChannel.subscribeBitsoChannel();
			orderBookMaintainerService.updateOrderBook();

			// Runnable task = () -> {
			// {
			// System.out.println("running a thread");
			// while (true) {
			// List<DiffOrdersWocketResponse> newAsks = recentAsks.stream()
			// .filter(ask -> ask.getSequence() >
			// lastSequenceTrade).collect(Collectors.toList());
			// List<DiffOrdersWocketResponse> newBids = recentBids.stream()
			// .filter(bid -> bid.getSequence() >
			// lastSequenceTrade).collect(Collectors.toList());
			// System.out.println("asks");
			// // newAsks.forEach(ask-> System.out.println(ask.getSequence()));
			// // System.out.println(newAsks.size());
			// System.out.println("bids");
			// // System.out.println(newBids.size());
			// // newBids.forEach(bid-> System.out.println(bid.getSequence()));
			// }
			// }
			// };
			// task.run();
			//
			// ExecutorService executor = Executors.newSingleThreadExecutor();
			// executor.execute(task);
			Thread.sleep(2000);
			// executor.shutdown();
			webSocketOrder.closeConnection();
			end = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(end);
		assertNotNull(orderBook);

	}

}
