package com.bitso.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bitso.api.exception.SocketDisconnectedException;
import com.bitso.api.service.OrderBookMaintainerService;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.rest.client.BitsoOrderBook;

@Service
public class OrderBookMaintainerServiceImpl implements OrderBookMaintainerService{

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
	private Integer lastSequenceTrade;
	
	@Override
	public void updateOrderBook() {
		 
		try {
			bitsoDiffOrdersChannel.subscribeBitsoChannel();
			orderBook=bitsoOrderBook.getOrderBook();
			lastSequenceTrade=orderBook.getOrderPayload().getSequence();
		} catch (SocketDisconnectedException e) {
			e.printStackTrace();
		}
	}

}
