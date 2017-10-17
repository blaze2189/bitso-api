/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.bitso.entity.RestResponse;
import com.bitso.rest.client.BitsoTicker;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoTickerImpl implements BitsoTicker {

	@Value("${bitso.url.ticker}")
    private String bitsoTicker;

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public RestResponse getTrades() {
        RestResponse returnResponse = null;
        HttpHeaders tokenHeader = new HttpHeaders();
        tokenHeader.add("User-Agent", "curl/7.51.0");
        tokenHeader.add("Accept", "application/json");
        HttpEntity request = new HttpEntity(tokenHeader);
        ResponseEntity<RestResponse> response;
        try {
            response = restTemplate.exchange(bitsoTicker, HttpMethod.GET, request, RestResponse.class);
            returnResponse = response.getBody();
        } catch (HttpStatusCodeException e) {
            HttpStatus status = e.getStatusCode();
            e.printStackTrace();
        } 
        return returnResponse;
    }

}
