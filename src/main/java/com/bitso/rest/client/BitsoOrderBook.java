package com.bitso.rest.client;

import java.util.List;

import com.bitso.entity.OrderBookRestResponse;
import com.bitso.entity.WebSocketPayload;

public interface BitsoOrderBook {

	OrderBookRestResponse getOrderBook();

}