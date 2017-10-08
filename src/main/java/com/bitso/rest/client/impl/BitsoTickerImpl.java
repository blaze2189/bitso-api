/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.rest.client.impl;

import com.bitso.entity.BitsoResponse;
import com.bitso.rest.client.BitsoTicker;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge
 */
@Component
public class BitsoTickerImpl implements BitsoTicker {

    private final String BITSO_TICKER = "https://api-dev.bitso.com/v3/ticker/?book=btc_mxn";

    @Autowired
    protected RestTemplate restTemplate;

    @Override
    public BitsoResponse getTrades() {
        BitsoResponse returnResponse = null;
        HttpHeaders tokenHeader = new HttpHeaders();
//        tokenHeader.add("Content-type",MediaType.APPLICATION_JSON_VALUE);
//        tokenHeader.add("Content-type", "application/json");
        tokenHeader.add("User-Agent", "curl/7.51.0");
        tokenHeader.add("Accept", "application/json");
        HttpEntity request = new HttpEntity(tokenHeader);
        ResponseEntity<BitsoResponse> response;
        ResponseEntity<Map> responseMap;
        try {
//            List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
//            for(HttpMessageConverter h : list){
//                System.out.println("h: "+h);
//            }
            response = restTemplate.exchange(BITSO_TICKER, HttpMethod.GET, request, BitsoResponse.class);
//            responseMap = restTemplate.exchange(BITSO_TICKER, HttpMethod.GET, request, Map.class);
//            for(Object k:responseMap.getBody().keySet()){
//                System.out.println(k+":"+responseMap.getBody().get(k));
//            }
            returnResponse = response.getBody();
        } catch (HttpStatusCodeException e) {
            HttpStatus status = e.getStatusCode();
            System.out.println("Error " + status + ": " + e.getMessage());
        } 
        return returnResponse;
    }

}
