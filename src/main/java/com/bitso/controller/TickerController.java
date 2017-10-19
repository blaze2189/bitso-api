/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bitso.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.rest.client.BitsoTicker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Jorge
 */
@Controller
public class TickerController implements Initializable {

	@FXML
	private TableView<TradePayload> tableView;
	@FXML
	private TableColumn<TradePayload, String> book;
	@FXML
	private TableColumn<TradePayload, String> makerSide;
	@FXML
	private TableColumn<TradePayload, String> amount;

	@Autowired
	private BitsoTicker bitsoTicker;

	@Autowired
	private BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver;

	@FXML
	public TradeRestResponse getBitsoResponse() {
		return bitsoTicker.getTradingInformation();
	}

	@FXML
	public void print() {
		System.out.println("a;ldfkjasdf;epwo;fidfh");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		book.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("book"));
		makerSide.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("makerSide"));
		amount.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("amount"));
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bitsoWebSocketOrderObserver);
		List<TradePayload> listBitsoResponse=null;
		if (bitsoWebSocketOrderObserver != null)
			listBitsoResponse = bitsoWebSocketOrderObserver.getListBitsoRespone();
		if (listBitsoResponse != null && listBitsoResponse.size() > 0)
			tableView.getItems().setAll(listBitsoResponse);
	}

	private List<TradePayload> initList() {
		ObservableList<TradePayload> lP = FXCollections.observableArrayList();

		// lP.addListener(new ListChangeListener<TradePayload>() {
		// @Override
		// public void onChanged(javafx.collections.ListChangeListener.Change<? extends
		// TradePayload> c) {
		// System.out.println("Changed on " + c);
		// if (c.next()) {
		// System.out.println(c.getFrom());
		// }
		//// tableView.getItems().add(c);
		//// tableView.refresh();
		// }
		//
		// });

		Runnable task = () -> {
			System.out.println("Task is running");
			for (;;) {
				TradePayload p = new TradePayload();
				p.setBook("btc_mxn");
				p.setCreatedAt(new Date());
				p.setAmount(String.valueOf(Math.random()));
				p.setMakerSide("1");
				p.setPrice(String.valueOf(Math.random()));
				p.setTid(String.valueOf(Math.random()));
				lP.add(p);
				tableView.getItems().add(p);
				// tableView.refresh();
				System.out.println("add");
			}
		};

		for (int i = 0; i < 3; i++) {
			TradePayload p = new TradePayload();
			p.setBook("btc_mxn");
			p.setCreatedAt(new Date());
			p.setAmount(String.valueOf(Math.random()));
			p.setMakerSide("1");
			p.setPrice(String.valueOf(Math.random()));
			p.setTid(String.valueOf(Math.random()));
			lP.add(p);
		}
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(task);
		return lP;
	}

}
