package com.bitso.api.ui;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@ComponentScan({ "com.bitso.api.websocket.impl", "com.bitso.configuration", "com.bitso.api.util",
	"com.bitso.api.service.impl", "com.bitso.controller", "com.bitso.rest.client.impl" })
@PropertySource("classpath:application.properties")
public class FxExample extends Application {

	
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Path path = Paths.get("src/main/resources/com/bitso/api/ui/fxml/example.fxml");
		FileInputStream fxmlStream = new FileInputStream(path.toAbsolutePath().toFile());
		
		VBox root = (VBox) loader.load(fxmlStream);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("title");
		stage.show();
	}
	
	public static void main (String...args ) {
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		((AnnotationConfigApplicationContext) applicationContext).register(FxExample.class);
		((AnnotationConfigApplicationContext) applicationContext).refresh();
		
		
		BitsoChannelSubscriber orderChannel = applicationContext.getBean(BitsoOrdersChannel.class);
		BitsoChannelSubscriber diffOrderChannel = applicationContext.getBean(BitsoDiffOrdersChannel.class);
		BitsoChannelSubscriber tradeChannel = applicationContext.getBean(BitsoTradesChannel.class);
		try (WebSocketConnection webSocketOrder = applicationContext.getBean(WebSocketConnectionImpl.class)) {
			BitsoWebSocketOrderObserver bitsoWebSocketOrderObserver = applicationContext
					.getBean(BitsoWebSocketOrderObserverImpl.class);
			((WebSocketConnectionImpl) webSocketOrder).addObserver(bitsoWebSocketOrderObserver);
			webSocketOrder.openConnection();
			orderChannel.subscribeBitsoChannel();
			 diffOrderChannel.subscribeBitsoChannel();
			 tradeChannel.subscribeBitsoChannel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Application.launch(args);
		
	}
	
}
