/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bitso.api.main.InitApp;
import com.bitso.api.table.model.TradePayloadModel;
import com.bitso.api.table.model.WebSocketPayloadModel;
import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.TradePayload;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTicker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Jorge
 */
@Component
public class TickerController implements Initializable {

	/////////////////////////////////////////////////////
	@FXML
	private TableView<WebSocketPayload> bestAsksTableView;
	@FXML
	private TableView<WebSocketPayload> bestBidsTableView;
	/////////////////////////////////////////////////////

	@FXML
	private TableView<TradePayload> tradePayloadTableView;
	@FXML
	private TableColumn<TradePayload, Object> book;
	@FXML
	private TableColumn<TradePayload, Object> makerSide;
	@FXML
	private TableColumn<TradePayload, Object> amount;
	@FXML
	private TableColumn<TradePayload, Object> price;
	@FXML
	private TableColumn<TradePayload, Object> tid;
	@FXML
	private TableColumn<TradePayload, Object> createdAt;

	@FXML
	private TableColumn<WebSocketPayload, Object> bestAsksId;
	@FXML
	private TableColumn<WebSocketPayload, Object> bestAsksMakerSide;
	@FXML
	private TableColumn<WebSocketPayload, Object> bestAsksAmount;

	@FXML
	private TableColumn<WebSocketPayload, Object> bestBidsIdentifier;
	@FXML
	private TableColumn<WebSocketPayload, Object> bestBidsMakerSide;
	@FXML
	private TableColumn<WebSocketPayload, Object> bestBidsAmount;

	@Autowired
	private BitsoTicker bitsoTicker;

	private DataConfiguration dataConfiguration;

	private TradePayloadModel tradePayloadTableModel;
	private TradePayloadModel tradePayloadTableModel2;

	private BitsoWebSocketObserver bitsoWebSocketOrderObserver;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ApplicationContext applicationContext = InitApp.getInstance().getApplicationContext();
		dataConfiguration = applicationContext.getBean(DataConfiguration.class);
		bitsoWebSocketOrderObserver = applicationContext.getBean(BitsoWebSocketObserver.class);

		price.setCellFactory(column -> new TradePayloadModel());
		makerSide.setCellFactory(column -> new TradePayloadModel());
		book.setCellFactory(column -> new TradePayloadModel());
		amount.setCellFactory(column -> new TradePayloadModel());
		tid.setCellFactory(column -> new TradePayloadModel());
		createdAt.setCellFactory(column -> new TradePayloadModel());

		bestAsksId.setCellFactory(column -> new WebSocketPayloadModel());
//		bestAsksMakerSide.setCellFactory(column -> new WebSocketPayloadModel());
		bestAsksAmount.setCellFactory(column -> new WebSocketPayloadModel());

		bestBidsIdentifier.setCellFactory(column -> new WebSocketPayloadModel());
//		bestBidsMakerSide.setCellFactory(column -> new WebSocketPayloadModel());
		bestBidsAmount.setCellFactory(column -> new WebSocketPayloadModel());



		List<TradePayload> listBitsoResponse = null;
		if (tradePayloadTableView != null) {
			dataConfiguration.setTradePayloadTableView(tradePayloadTableView);
		}
		if (bestAsksTableView != null) {
			dataConfiguration.setTableViewBestAsks(bestAsksTableView);
		}

		if (bestBidsTableView != null) {
			dataConfiguration.setTableViewBestBids(bestBidsTableView);
		}
	}

}
