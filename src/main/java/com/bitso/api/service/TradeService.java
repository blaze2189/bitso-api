package com.bitso.api.service;

import java.util.List;

import com.bitso.entity.TradePayload;

public interface TradeService {

	
	
	List<TradePayload> getNRecentTrades();
	
}
