package com.bitso.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bitso.api.exception.SocketDisconnectedException;
import com.bitso.api.service.OrderService;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.rest.client.BitsoOrderBook;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	protected BitsoOrderBook bitsoOrderBook;

	@Autowired
	@Qualifier("bitsoDiffOrdersChannel")
	protected BitsoChannelSubscriber bitsoDiffOrdersChannel;

	@Autowired
	@Qualifier("orderBook")
	protected OrderBookRestResponse orderBook;

	@Autowired
	@Qualifier("lastSequenceTrade")
	protected Integer lastSequenceTrade;

	@Autowired
	protected WebSocketConnection webSocketConnection;

	@Override
	public void updateOrderBook() throws InterruptedException {

		try {
			if (!webSocketConnection.isConnected()) {
				webSocketConnection.openConnection();
			}
			bitsoDiffOrdersChannel.subscribeBitsoChannel();
			orderBook = bitsoOrderBook.getOrderBook();
			lastSequenceTrade = orderBook.getOrderPayload().getSequence();
		} catch (SocketDisconnectedException e) {
			e.printStackTrace();
		}
	}

}
