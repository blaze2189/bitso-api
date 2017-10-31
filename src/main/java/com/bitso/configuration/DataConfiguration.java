package com.bitso.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bitso.api.util.PayloadSocketComparator;
import com.bitso.entity.DiffOrdersWocketResponse;
import com.bitso.entity.OrderBookRestResponse;
import com.bitso.entity.TradePayload;
import com.bitso.entity.WebSocketPayload;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class DataConfiguration {

	private TableView<TradePayload> tradePayloadTableView;
	private TableView<WebSocketPayload> tableViewBestAsks;
	private TableView<WebSocketPayload> tableViewBestBids;

	private Integer upTicketsStrategy;
	private Integer downTicketsStrategy;
	private Integer totalRecentTrades;
	private Integer totalBestTrades;
	private Integer lastSequenceTrade;
	
	@Autowired
	private PayloadSocketComparator payloadSocketComparator;

	private List<TradePayload> listTradePayload = FXCollections.observableArrayList();
	private List<DiffOrdersWocketResponse> recentBids = Collections.synchronizedList(new ArrayList<>());
	private List<DiffOrdersWocketResponse> recentAsks = Collections.synchronizedList(new ArrayList<>());
	
	private Set<WebSocketPayload> listTopAsk;
	private Set<WebSocketPayload> listTopBid;


	@Bean(name = "orderBook")
	public OrderBookRestResponse orderBook() {
		return new OrderBookRestResponse();
	}


}
