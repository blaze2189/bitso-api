/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.TableCell;
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
	private TableColumn<TradePayload,Object> makerSide;
//	private TableColumn<TradePayload, String> makerSide;
	@FXML
	private TableColumn<TradePayload, Object> amount;
	@FXML
	private TableColumn<TradePayload, Object> price;
	@FXML
	private TableColumn<TradePayload, Object> tid;
	@FXML
	private TableColumn<TradePayload, Object> createdAt;
	
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
		listData = FXCollections.observableArrayList();
		
//		book.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("book"));
//		makerSide.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("makerSide"));
//		amount.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("amount"));
//		price.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("price"));
//		tid.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("tid"));
//		createdAt.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("createdAt"));
		
		price.setCellFactory(column -> new TableModel());
		makerSide.setCellFactory(column -> new TableModel());
		book.setCellFactory(column -> new TableModel());
		amount.setCellFactory(column -> new TableModel());
		tid.setCellFactory(column -> new TableModel());
		createdAt.setCellFactory(column-> new TableModel());
		
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
	
	private class TableModel extends TableCell<TradePayload,Object>{
		
		@Override
		protected void updateItem(Object item,boolean empty) {		
			String style =getTableRow()!=null && 
					getTableRow().getItem()!=null && 
					getTableRow().getItem().isReal() ?
							"-fx-text-fill: green":
								"-fx-text-fill:red";
			if(item instanceof String) {
			setText(String.valueOf(item));
			}
			else if(item instanceof Date) {
				DateFormat df = new SimpleDateFormat("dd-MM-yy 24hh:mm:ss");
				String stringDate = df.format((Date)item);
				setText(stringDate);
			}else if(item instanceof Double) {
				NumberFormat numberFormat = new DecimalFormat("#0.000000");
				String doubleString = numberFormat.format(item);
				setText(doubleString);
			}
			setStyle(style);
			
		}
	}

}
