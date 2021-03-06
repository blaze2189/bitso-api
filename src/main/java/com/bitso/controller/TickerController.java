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
import com.bitso.api.websocket.BitsoWebSocketObserver;
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

	@FXML
	private TableView<WebSocketPayload> bestAsksTableView;
	@FXML
	private TableView<WebSocketPayload> bestBidsTableView;

	@FXML
	private TableColumn<WebSocketPayload,Object> bestAsksRate;
	@FXML
	private TableColumn<WebSocketPayload,Object> bestAsksValue;
	@FXML
	private TableColumn<WebSocketPayload,Object> bestAsksAmount;

	@FXML
	private TableColumn<WebSocketPayload,Object> bestBidsRate;
	@FXML
	private TableColumn<WebSocketPayload,Object> bestBidsValue;
	@FXML
	private TableColumn<WebSocketPayload,Object> bestBidsAmount;

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

	@Autowired
	private BitsoTicker bitsoTicker;

	private DataConfiguration dataConfiguration;

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

		bestBidsAmount.setCellFactory(column -> new WebSocketPayloadModel());
		bestAsksAmount.setCellFactory(column -> new WebSocketPayloadModel());
		bestBidsValue.setCellFactory(column -> new WebSocketPayloadModel());
		bestAsksValue.setCellFactory(column -> new WebSocketPayloadModel());
		bestBidsRate.setCellFactory(column -> new WebSocketPayloadModel());
		bestAsksRate.setCellFactory(column -> new WebSocketPayloadModel());

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
