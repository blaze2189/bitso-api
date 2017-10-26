package com.bitso.api.service;

import java.util.Set;

import com.bitso.entity.TradePayload;

public interface TradeService {
	
	Set<TradePayload> getNRecentTrades();
	
}
