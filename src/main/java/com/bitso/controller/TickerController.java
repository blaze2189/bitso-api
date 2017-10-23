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
import org.springframework.stereotype.Component;

import com.bitso.api.main.InitApp;
import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.configuration.DataConfiguration;
import com.bitso.entity.TradePayload;
import com.bitso.entity.TradeRestResponse;
import com.bitso.rest.client.BitsoTicker;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
@Component
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

	// @Autowired
	private BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver;

	private DataConfiguration dataConfiguration;

	@FXML
	public TradeRestResponse getBitsoResponse() {
		return bitsoTicker.getTradingInformation();
	}

	@FXML
	public void print() {
		System.out.println("a;ldfkjasdf;epwo;fidfh");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		dataConfiguration = InitApp.getInstance().getApplicationContext().getBean(DataConfiguration.class);
		bitsoWebSocketOrderObserver = InitApp.getInstance().getApplicationContext()
				.getBean(BitsoWebSocketOrderObserver.class);
		System.out.println("initializer-----------------------------------------------------------");
		book.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("book"));
		makerSide.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("makerSide"));
		amount.setCellValueFactory(new PropertyValueFactory<TradePayload, String>("amount"));
	
		System.out.println("DataConfiguration (TickerController): " + dataConfiguration);
		List<TradePayload> listBitsoResponse = null;
		if (dataConfiguration != null){
			listBitsoResponse = bitsoWebSocketOrderObserver.getListBitsoRespone();
                }
		if (listBitsoResponse != null ){
//                    listBitsoResponse = initList();
			tableView.getItems().setAll(listBitsoResponse);
                        ((ObservableList) listBitsoResponse).addListener(new ListChangeListener<TradePayload>() {

                        @Override
                        public void onChanged(ListChangeListener.Change<? extends TradePayload> c) {
                            System.out.println("change");
                            tableView.getItems().forEach(item -> tableView.getItems().remove(item));
                            tableView.getItems().addAll(c.getList());
                            tableView.refresh();
                        }
//			@Override
//			public void onChanged(javafx.collections.ListChangeListener.Change<? extends TradePayload> c) {
//				System.out.println("Changed on " + c);
//				if (c.next()) {
//					System.out.println(c.getFrom());
//				}
//				tableView.getItems().add(c);
//				tableView.refresh();
//			}

		});
                }
		

	}

	private List<TradePayload> initList() {
		ObservableList<TradePayload> lP = FXCollections.observableArrayList();

		// lP.addC

		Runnable task = () -> {
			System.out.println("Task is running");
			for (;;) {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TradePayload p = new TradePayload();
				p.setBook("btc_mxn");
				p.setCreatedAt(new Date());
				p.setAmount(String.valueOf(Math.random()));
				p.setMakerSide("1");
				p.setPrice(String.valueOf(Math.random()));
				p.setTid(String.valueOf(Math.random()));
				lP.add(p);
//				tableView.getItems().add(p);
//				 tableView.refresh();
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
