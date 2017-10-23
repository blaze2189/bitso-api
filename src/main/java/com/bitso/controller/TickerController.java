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
import org.springframework.stereotype.Component;

import com.bitso.api.main.InitApp;
import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.TradePayload;
import com.bitso.entity.WebSocketPayload;
import com.bitso.rest.client.BitsoTicker;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
	private TableColumn<TradePayload, String> book;
	@FXML
	private TableColumn<TradePayload, String> makerSide;
	@FXML
	private TableColumn<TradePayload, String> amount;

	private List<TradePayload> listData;

	@Autowired
	private BitsoTicker bitsoTicker;

	private DataConfiguration dataConfiguration;

	// @Autowired
	private BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		dataConfiguration = InitApp.getInstance().getApplicationContext().getBean(DataConfiguration.class);
		bitsoWebSocketOrderObserver = InitApp.getInstance().getApplicationContext()
				.getBean(BitsoWebSocketOrderObserver.class);
		System.out.println("initializer----------------------------------------H-------------------");
		listData = FXCollections.observableArrayList();
		book.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("book"));
		makerSide.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("makerSide"));
		amount.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("amount"));

		System.out.println("DataConfiguration (TickerController): " + dataConfiguration);
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
		if (dataConfiguration != null) {
			listBitsoResponse = bitsoWebSocketOrderObserver.getListBitsoRespone();
		}
		if (listBitsoResponse != null) {
			tradePayloadTableView.getItems().setAll(listBitsoResponse);
			tradePayloadTableView.refresh();

		}

	}

}
