package com.bitso.rest.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.rest.client.BitsoOrderBook;

@Component
public class BitsoOrderBookImpl implements BitsoOrderBook {

	@Value("${bitso.url.orderbook}")
	private String bitsoOrderBookUrl;

	@Value("${bitso.book.btc_mxn}")
	private String bitsoBookBtcMxn;;

	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	protected DataConfiguration dataConfiguration;
	
	@Override
	public OrderBookRestResponse getOrderBook() {
		OrderBookRestResponse returnResponse = null;
		HttpHeaders tokenHeader = new HttpHeaders();
		tokenHeader.add("User-Agent", "curl/7.51.0");
		tokenHeader.add("Accept", "application/json");
		HttpEntity request = new HttpEntity(tokenHeader);
		ResponseEntity<OrderBookRestResponse> response;
		try {
			response = restTemplate.exchange(bitsoOrderBookUrl + "?book=" + bitsoBookBtcMxn, HttpMethod.GET, request,
					OrderBookRestResponse.class);
			returnResponse = response.getBody();
		} catch (HttpStatusCodeException e) {
			HttpStatus status = e.getStatusCode();
			e.printStackTrace();
		}
		return returnResponse;
	}

}
