package com.bitso.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bitso.api.util.PayloadSocketComparator;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.entity.WebSocketPayload;

import javafx.collections.FXCollections;
import lombok.Getter;
import lombok.Setter;

@Component
@Scope("singleton")
@Getter
@Setter
public class DataConfiguration {
	
	@Autowired
	private PayloadSocketComparator payloadSocketComparator;

	@Bean(name = "totalBestTrades")
	private Integer totalBestTraiding() {
		return 0;
	}

	@Bean(name = "totalRecentTrades")
	private Integer totalRecentTraiding() {
		return 0;
	}

	@Bean
	private TradeRestResponse tradeRestResponse() {
		return new TradeRestResponse();
	}
	
	@Bean(name = "lastSequenceTrade")
	public Integer lastSequenceTrade() {
		return 0;
	}

//	private List<TradePayload> listTradePayload =Collections.synchronizedList(new ArrayList<TradePayload>());
	private List<TradePayload> listTradePayload =FXCollections.observableArrayList();
	@Bean(name = "listTradePayload")
	public List<TradePayload> listTradePayload() {
		return listTradePayload;
	}	
	
	@Bean(name = "topAsks")
	public Set<WebSocketPayload> listTopAsk() {
		return Collections.synchronizedSet(new TreeSet<>(payloadSocketComparator));
	}

	@Bean(name = "topBids")
	public Set<WebSocketPayload> listTopBid() {
		return Collections.synchronizedSet(new TreeSet<>(payloadSocketComparator));
	}

	@Bean(name = "orderBook")
	public OrderBookRestResponse orderBook() {
		return new OrderBookRestResponse();
	}

	@Bean(name = "recentBids")
	public List<DiffOrdersWocketResponse> recentBids() {
		return Collections.synchronizedList(new ArrayList<>());
	}

	@Bean(name = "recentAsks")
	public List<DiffOrdersWocketResponse> recentAsks() {
		return Collections.synchronizedList(new ArrayList<>());
	}

}
