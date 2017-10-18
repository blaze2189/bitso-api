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

import com.bitso.entity.TradeRestResponse;
import com.bitso.rest.client.BitsoTrade;

@Component
public class BitsoTradeImpl implements BitsoTrade {

	@Value("${bitso.url.trades}")
    private String bitsoTrade;
	
	@Value("${bitso.book.btc_mxn}")
	private String bookBtcMxn;

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public TradeRestResponse getRecentTrades() {
    	TradeRestResponse returnResponse = null;
        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.add("User-Agent", "curl/7.51.0");
        tokenHeader.add("Accept", "application/json");
        HttpEntity request = new HttpEntity(tokenHeader);
        ResponseEntity<TradeRestResponse> response;
        try {
            response = restTemplate.exchange(bitsoTrade+"?book="+bookBtcMxn, HttpMethod.GET, request, TradeRestResponse.class);
            returnResponse = response.getBody();
        } catch (HttpStatusCodeException e) {
            HttpStatus status = e.getStatusCode();
            e.printStackTrace();
        } 
        return returnResponse;
    }

	
}
