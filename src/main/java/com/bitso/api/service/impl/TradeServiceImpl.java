package com.bitso.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bitso.api.service.TradeService;
import com.bitso.entity.TradePayload;

public class TradeServiceImpl implements TradeService {

	@Autowired
	protected Integer totalRecentTrades;
	
	
	
	@Override
	public List<TradePayload> getNRecentTrades() {

		return null;
		
	}

}
