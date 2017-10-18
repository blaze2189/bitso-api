package com.bitso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bitso.entity.TradeRestResponse;
import com.bitso.rest.client.BitsoTrade;

@Controller
public class TradeController {

	 @Autowired
	    private BitsoTrade bitsoTrade;

	    public TradeRestResponse getBistoResponse() {

	        return bitsoTrade.getRecentTrades();

	    }
	
}
