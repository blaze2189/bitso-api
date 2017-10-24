package com.bitso.api.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.bitso.api.websocket.BitsoChannelSubscriber;
import com.bitso.api.websocket.BitsoWebSocketOrderObserver;
import com.bitso.api.websocket.WebSocketConnection;
import com.bitso.api.websocket.impl.BitsoDiffOrdersChannel;
import com.bitso.api.websocket.impl.BitsoOrdersChannel;
import com.bitso.api.websocket.impl.BitsoTradesChannel;
import com.bitso.api.websocket.impl.BitsoWebSocketOrderObserverImpl;
import com.bitso.api.websocket.impl.WebSocketConnectionImpl;

import lombok.Getter;

@ComponentScan({ "com.bitso.api.websocket.impl", "com.bitso.configuration", "com.bitso.api.util",
	"com.bitso.api.service.impl","com.bitso.api.ui", "com.bitso.controller", "com.bitso.rest.client.impl" })
@PropertySource("classpath:application.properties")
@Getter
public class InitApp {

	private ApplicationContext applicationContext;
	
	private WebSocketConnection webSocketOrder;
	
	private InitApp() {}
	
	private static InitApp initApp=new InitApp();
	
	public static InitApp getInstance() {
		return initApp;
	}
	
	
	public void stop() {
		try {
			webSocketOrder.closeConnection();
		} catch (InterruptedException e) {
		}
	}
	
	public  void start() {
			
			 applicationContext = new AnnotationConfigApplicationContext();
			((AnnotationConfigApplicationContext) applicationContext).register(InitApp.class);
			((AnnotationConfigApplicationContext) applicationContext).refresh();
			
			
			BitsoChannelSubscriber orderChannel = applicationContext.getBean(BitsoOrdersChannel.class);
			BitsoChannelSubscriber diffOrderChannel = applicationContext.getBean(BitsoDiffOrdersChannel.class);
			BitsoChannelSubscriber tradeChannel = applicationContext.getBean(BitsoTradesChannel.class);
			try  {
				webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class);
				BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext
						.getBean(BitsoWebSocketOrderObserverImpl.class);
				((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
				webSocketOrder.openConnection();
//				orderChannel.subscribeBitsoChannel();
//				 diffOrderChannel.subscribeBitsoChannel();
				 tradeChannel.subscribeBitsoChannel();
//				 Thread.sleep(20000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	
	}
	
}
