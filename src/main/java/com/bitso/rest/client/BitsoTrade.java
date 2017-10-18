package com.bitso.rest.client;

import com.bitso.entity.TradeRestResponse;

public interface BitsoTrade {

	TradeRestResponse getRecentTrades();
	
}
