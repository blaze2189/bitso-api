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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.OrderSocketResponse;
import com.bitso.entity.RestOrderBookPayload;
import com.bitso.entity.RestPayload;
import com.bitso.entity.RestResponse;
import com.bitso.entity.TradeInformation;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTicker;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoWebSocketOrderObserverImpl implements BitsoWebSocketOrderObserver {

	@Autowired
	protected BitsoTicker bitsoTicker;

	private List<String> messageReceived;
	@Autowired
	@Qualifier("tradesList")
	private List<RestResponse> listBitsoResponse;
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
	public List<RestResponse> getListBitsoRespone() {
		return listBitsoResponse;
	}

	@Override
	public Boolean isConnected() {
		return isConnected;
	}

	@Autowired
	@Qualifier("orderBook")
	public RestResponse orderBook;

	@Autowired
	@Qualifier("lastSequenceTrade")
	public Integer lastSequenceTrade;

	@Override
	public void update(Observable o, Object arg) {
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
					case "orders":
						OrderSocketResponse orderSocketResponse = objectMapper.readValue(message,
								OrderSocketResponse.class);
						setBids = orderSocketResponse.getOrderPayloadSocketResponse().getBids();
						setAsks = orderSocketResponse.getOrderPayloadSocketResponse().getAsks();
						messageReceived.add(message);
						RestResponse bitsoResponse = bitsoTicker.getTrades();
						if (listBitsoResponse.size() < 6) {
							listBitsoResponse.add(bitsoResponse);
						} else {
							listBitsoResponse.remove(0);
							listBitsoResponse.add(bitsoResponse);
						}
						break;
					case "diff-orders":
						
						DiffOrdersWocketResponse diffOrderResponse = null;
						try {
							diffOrderResponse = objectMapper.readValue(message, DiffOrdersWocketResponse.class);
						} catch (Exception e) {
							System.out.println(e);
						}
						Integer sequence = null;
						Integer sequenceMessage = diffOrderResponse.getSequence();
						Integer markerSide = diffOrderResponse.getPayload().get(0).getMarkerSide();
						RestPayload payload = orderBook.getPayload();
						RestOrderBookPayload restOrderBookPayload = null;
						List<TradeInformation> listMarkerSide = null;
						if (payload instanceof RestOrderBookPayload) {
							restOrderBookPayload = (RestOrderBookPayload) payload;
							sequence = restOrderBookPayload.getSequence();
						}

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
