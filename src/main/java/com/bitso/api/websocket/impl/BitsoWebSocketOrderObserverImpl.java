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
import com.bitso.entity.BitsoResponse;
import com.bitso.entity.OrderSocketResponse;
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
	private List<BitsoResponse> listBitsoResponse;
	private Boolean isConnected;

	@Autowired
	@Qualifier("topBids")
	private Set<WebSocketPayload> setBids;

	@Autowired
	@Qualifier("topAsks")
	private Set<WebSocketPayload> setAsks;

	{
		messageReceived = new ArrayList<>();
		// listBitsoResponse = new Vector<>();
		isConnected = false;
	}

	@Override
	public List<String> getMessageReceived() {
		return messageReceived;
	}

	@Override
	public List<BitsoResponse> getListBitsoRespone() {
		return listBitsoResponse;
	}

	@Override
	public Boolean isConnected() {
		return isConnected;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String message = (String) arg;

			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Map<String, Object> messageMap = objectMapper.readValue(message, Map.class);
				if (messageMap.containsKey("action")) {

				} else if (messageMap.containsKey("type")) {
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
						BitsoResponse bitsoResponse = bitsoTicker.getTrades();
						if (listBitsoResponse.size() < 6) {
							listBitsoResponse.add(bitsoResponse);
						} else {
							listBitsoResponse.remove(0);
							listBitsoResponse.add(bitsoResponse);
						}
						break;
					default:

					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}

		}
		if (arg instanceof Boolean) {
			isConnected = (Boolean) arg;
		}
	}

}
