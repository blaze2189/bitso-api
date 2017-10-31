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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bitso.api.service.TradeService;
import com.bitso.api.websocket.BitsoWebSocketObserver;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.entity.OrderSocketResponse;
import com.bitso.entity.TradePayload;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTrade;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.control.TableView;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoWebSocketObserverImpl implements BitsoWebSocketObserver {

	private Boolean isConnected;

	private static final Logger logger = Logger.getLogger(BitsoWebSocketObserverImpl.class);

//	@Autowired
//	@Qualifier("recentBids")
//	private List<DiffOrdersWocketResponse> recentBids;
//
//	@Autowired
//	@Qualifier("recentAsks")
//	private List<DiffOrdersWocketResponse> recentAsks;

	{
		isConnected = false;
	}

	@Override
	public Boolean isConnected() {
		return isConnected;
	}

//	@Autowired
//	@Qualifier("orderBook")
//	public OrderBookRestResponse orderBook;

	@Autowired
	private DataConfiguration dataConfiguration;

	@Autowired
	protected TradeService tradeService;

	@Override
	public void tradeSubscribeAction() throws JsonParseException, JsonMappingException, IOException {
		TableView<TradePayload> tableView = dataConfiguration.getTradePayloadTableView();
		Set<TradePayload> setPayload;
		if (tableView != null) {
			setPayload = tradeService.getNRecentTrades();
			tableView.getItems().setAll(setPayload);
			tableView.refresh();
		}
	}

	@Override
	public void orderSubscribeAction(String message) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		OrderSocketResponse orderSocketResponse = objectMapper.readValue(message, OrderSocketResponse.class);
		Set<WebSocketPayload>setBids = orderSocketResponse.getOrderPayloadSocketResponse().getBids();
		Set<WebSocketPayload>setAsks = orderSocketResponse.getOrderPayloadSocketResponse().getAsks();

		Integer lastTrade = dataConfiguration.getLastSequenceTrade();
		Integer topBestTrades = dataConfiguration.getTotalBestTrades();

		TableView<WebSocketPayload> tableViewBest;

		List<WebSocketPayload> topBestBids = new ArrayList<>();
		setBids.forEach(bid -> {
			if (topBestBids.size() < topBestTrades) {
				topBestBids.add(bid);
			}
		});

		if (dataConfiguration.getTableViewBestBids() != null) {
			tableViewBest = dataConfiguration.getTableViewBestBids();
			tableViewBest.getItems().setAll(topBestBids);
			tableViewBest.refresh();
		}
		List<WebSocketPayload> topBestAsks = new ArrayList<>();
		setAsks.forEach(ask -> {
			if (topBestAsks.size() < topBestTrades) {
				topBestAsks.add(ask);
			}
		});
		if (dataConfiguration.getTableViewBestAsks() != null) {
			tableViewBest = dataConfiguration.getTableViewBestAsks();
			tableViewBest.getItems().setAll(topBestAsks);
			tableViewBest.refresh();
		}
		dataConfiguration.setListTopAsk(setAsks);
		dataConfiguration.setListTopBid(setBids);

	}

	@Override
	public void diffOrderSubscribeAction(String message) throws JsonParseException, JsonMappingException, IOException {
		DiffOrdersWocketResponse diffOrderResponse = null;
		ObjectMapper objectMapper = new ObjectMapper();
		diffOrderResponse = objectMapper.readValue(message, DiffOrdersWocketResponse.class);
		Integer markerSide = diffOrderResponse.getPayload().get(0).getMakerSide();
		switch (markerSide) {
		case 1:
			// bids
			dataConfiguration.getRecentBids().add(diffOrderResponse);
			break;
		case 0:
			// asks
			dataConfiguration.getRecentAsks().add(diffOrderResponse);
			break;
		default:

		}

	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String message = (String) arg;
			if (message != null) {
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
							orderSubscribeAction(message);
							break;
						case "diff-orders":
							diffOrderSubscribeAction(message);
						default:

						}

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else if (arg instanceof Boolean) {
			isConnected = (Boolean) arg;
		}
	}

}
