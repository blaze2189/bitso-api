package com.bitso.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bitso.api.exception.SocketDisconnectedException;
import com.bitso.api.service.OrderBookMaintainerService;
import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.entity.RestOrderBookPayload;
import com.bitso.entity.RestResponse;
import com.bitso.rest.client.BitsoOrderBook;

@Service
public class OrderBookMaintainerServiceImpl implements OrderBookMaintainerService{

	@Autowired
	private BitsoOrderBook bitsoOrderBook;
	
	@Autowired
	@Qualifier("bitsoDiffOrdersChannel")
	private BitsoChannelSubscriber bitsoDiffOrdersChannel;
	
	@Autowired
	@Qualifier("orderBook")
	private RestResponse orderBook;
	
	@Autowired
	@Qualifier("lastSequenceTrade")
	private Integer lastSequenceTrade;
	
	@Override
	public void updateOrderBook() {
		 
		try {
			bitsoDiffOrdersChannel.subscribeBitsoChannel();
			orderBook=bitsoOrderBook.getOrderBook();
//			lastSequenceTrade=((RestOrderBookPayload)orderBook.getPayload()).getSequence()!=null?0:1;;
			lastSequenceTrade=38978974;
			System.out.println("lastsequencetrade "+lastSequenceTrade);
		} catch (SocketDisconnectedException e) {
			e.printStackTrace();
		}
	}

}
