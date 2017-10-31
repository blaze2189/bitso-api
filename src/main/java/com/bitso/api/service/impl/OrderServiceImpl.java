package com.bitso.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bitso.api.exception.SocketDisconnectedException;
import com.bitso.api.service.OrderService;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.rest.client.BitsoOrderBook;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	protected BitsoOrderBook bitsoOrderBook;

	@Autowired
	@Qualifier("bitsoDiffOrdersChannel")
	protected BitsoChannelSubscriber bitsoDiffOrdersChannel;

	@Autowired
	@Qualifier("orderBook")
	protected OrderBookRestResponse orderBook;

	@Autowired
	protected DataConfiguration dataConfiguration;

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
			Integer lastSequenceTrade = orderBook.getOrderPayload().getSequence();
			dataConfiguration.setLastSequenceTrade(lastSequenceTrade);
		} catch (SocketDisconnectedException e) {
			logger.error("Error connecting to socket\n"+e);
		}
	}

}
