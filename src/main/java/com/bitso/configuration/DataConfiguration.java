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
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class DataConfiguration {
	
	private TableView<TradePayload> tradePayloadTableView;
	private TableView<WebSocketPayload> tableViewBestAsks;
	private TableView<WebSocketPayload> tableViewBestBids;
	
	private Integer totalRecentTrades;
	private Integer totalBestTrades;
	
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

	private Integer lastSequenceTrade;
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
	public List<WebSocketPayload> listTopAsk() {
		return Collections.synchronizedList(new ArrayList<>());
//		return Collections.synchronizedSet(new TreeSet<>(payloadSocketComparator));
	}

	@Bean(name = "topBids")
	public List<WebSocketPayload> listTopBid() {
		return Collections.synchronizedList(new ArrayList<>());
//		return Collections.synchronizedSet(new TreeSet<>(payloadSocketComparator));
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
	
//	@FXML
//	private TableView<TradePayload> tableView;
//	private TableColumn<TradePayload, String> book;
//	private TableColumn<TradePayload, String> makerSide;
//	private TableColumn<TradePayload, String> amount;

}
