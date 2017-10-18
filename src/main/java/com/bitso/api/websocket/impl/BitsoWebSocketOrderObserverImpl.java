/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.api.websocket.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.entity.OrderSocketResponse;
import com.bitso.entity.TradeInformation;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTrade;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoWebSocketOrderObserverImpl implements BitsoWebSocketOrderObserver {

	@Autowired
	protected BitsoTrade bitsoTrade;

	@Autowired
	protected TradeRestResponse bitsoTradeResponse;

	@Autowired
	@Qualifier("totalRecentTrades")
	protected Integer totalRecentTrades;

	private List<String> messageReceived;
	@Autowired
	@Qualifier("tradesList")
	private List<TradeRestResponse> listBitsoResponse;
	private Boolean isConnected;

	@Autowired
	@Qualifier("topBids")
	private Set<WebSocketPayload> setBids;

	@Autowired
	@Qualifier("topAsks")
	private Set<WebSocketPayload> setAsks;

	@Autowired
	@Qualifier("recentBids")
	private List<DiffOrdersWocketResponse> recentBids;

	@Autowired
	@Qualifier("recentAsks")
	private List<DiffOrdersWocketResponse> recentAsks;

	{
		messageReceived = new ArrayList<>();
		isConnected = false;
	}

	@Override
	public List<String> getMessageReceived() {
		return messageReceived;
	}

	@Override
	public List<TradeRestResponse> getListBitsoRespone() {
		return listBitsoResponse;
	}

	@Override
	public Boolean isConnected() {
		return isConnected;
	}

	@Autowired
	@Qualifier("orderBook")
	public OrderBookRestResponse orderBook;

	@Autowired
	@Qualifier("lastSequenceTrade")
	public Integer lastSequenceTrade;

	@Override
	public void tradeSubscribeAction() {
		bitsoTradeResponse = bitsoTrade.getRecentTrades();
		List<TradePayload> listTradePayload = bitsoTradeResponse.getTradePayload();
	}

	@Override
	public void orderSubscribeAction(String message) {
		OrderSocketResponse orderSocketResponse = objectMapper.readValue(message, OrderSocketResponse.class);
		setBids = orderSocketResponse.getOrderPayloadSocketResponse().getBids();
		setAsks = orderSocketResponse.getOrderPayloadSocketResponse().getAsks();
		messageReceived.add(message);
	}

	@Override
	public void diffOrderSubscribeAction(String message) {
		DiffOrdersWocketResponse diffOrderResponse = null;
		try {
			diffOrderResponse = objectMapper.readValue(message, DiffOrdersWocketResponse.class);
		} catch (Exception e) {
			System.out.println(e);
		}
		Integer markerSide = diffOrderResponse.getPayload().get(0).getMarkerSide();
		List<TradeInformation> listMarkerSide = null;
		switch (markerSide) {
		case 1:
			// bids
			recentBids.add(diffOrderResponse);
			break;
		case 0:
			// asks
			recentAsks.add(diffOrderResponse);
			break;
		default:

		}

	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("message " + arg);
		if (arg instanceof String) {
			String message = (String) arg;

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Map<String, Object> messageMap = objectMapper.readValue(message, Map.class);
				if (messageMap.containsKey("type") && !message.contains("action")) {
					String type = (String) messageMap.get("type");
					switch (type) {
					case "ka":
						break;
					case "trades":
						tradeSubscribeAction();
						break;
					case "orders":
						orderSubscribeAction();
						break;
					case "diff-orders":
						diffOrderSubscribeAction();
					default:

					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (arg instanceof Boolean) {
			isConnected = (Boolean) arg;
		}
	}

}
