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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.configuration.DataConfiguration;
import com.bitso.controller.TickerController;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.entity.OrderSocketResponse;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTrade;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;

import javafx.scene.control.TableView;

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
//	@Autowired
//	@Qualifier("tradesList")
//	private List<TradeRestResponse> listBitsoResponse;
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
	public List<TradePayload> getListBitsoRespone() {
		return dataConfiguration.getListTradePayload();
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

//	@Autowired
//	List<TradePayload> listTradePayload;
	
	@Autowired
	private DataConfiguration dataConfiguration;
	
	@Autowired
	private TickerController tickerController;
	
	@Override
	public void tradeSubscribeAction() {
		System.out.println("Dataocnfiguration(tradeSubscribe): "+dataConfiguration);
                bitsoTradeResponse = bitsoTrade.getRecentTrades();
                List<TradePayload> oldPayload = bitsoTradeResponse.getTradePayload();
                System.out.println("oldPayload: "+oldPayload+"\nsize: "+oldPayload.size());
                oldPayload.forEach(item -> System.out.println(item));
                TableView<TradePayload> tableView= dataConfiguration.getTradePayloadTableView();
//                tableView.getItems().addAll(oldPayload);
//                tableView.refresh();
//                if(oldPayload!=null ){
//                    oldPayload.forEach(item -> oldPayload.remove(item));
//                    bitsoTradeResponse.getTradePayload().forEach(item -> oldPayload.add(item));
//                }
                if(tableView!=null) {
                	List<TradePayload> newPayload= new ArrayList<>();
//                	AtomicInteger atomicInteger = new AtomicInteger();
//                	atomicInteger =0;
//                	int index=0;
                	 oldPayload.forEach(item->{if(newPayload.size()<=dataConfiguration.getTotalRecentTrades()) {newPayload.add(item);}});
                	tableView.getItems().setAll(newPayload);
                	tableView.refresh();
                        
                }
	}

	@Override
	public void orderSubscribeAction(String message) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		OrderSocketResponse orderSocketResponse = objectMapper.readValue(message, OrderSocketResponse.class);
		setBids = orderSocketResponse.getOrderPayloadSocketResponse().getBids();
		setAsks = orderSocketResponse.getOrderPayloadSocketResponse().getAsks();
		
		Integer lastTrade =dataConfiguration.getLastSequenceTrade();
		Integer topBestTrades = dataConfiguration.getTotalBestTrades();
		TableView<WebSocketPayload> tableViewBest;
		
		List<WebSocketPayload> topBestBids = new ArrayList<>(); 
		setBids.forEach(bid -> {if(topBestBids.size()<topBestTrades) {topBestBids.add(bid);}});
		
		tableViewBest=dataConfiguration.getTableViewBestBids();
		tableViewBest.getItems().setAll(topBestBids);
		tableViewBest.refresh();
		
		List<WebSocketPayload> topBestAsks = new ArrayList<>(); 
		setAsks.forEach(ask -> {if(topBestAsks.size()<topBestTrades) {topBestAsks.add(ask);}});
		tableViewBest=dataConfiguration.getTableViewBestAsks();
		tableViewBest.getItems().setAll(topBestAsks);
		tableViewBest.refresh();
		
//		messageReceived.add(message);
	}

	@Override
	public void diffOrderSubscribeAction(String message)throws JsonParseException, JsonMappingException, IOException {
		DiffOrdersWocketResponse diffOrderResponse = null;
		ObjectMapper objectMapper = new ObjectMapper();
			diffOrderResponse = objectMapper.readValue(message, DiffOrdersWocketResponse.class);
		Integer markerSide = diffOrderResponse.getPayload().get(0).getMakerSide();
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
		else if (arg instanceof Boolean) {
			isConnected = (Boolean) arg;
		}
	}

}
