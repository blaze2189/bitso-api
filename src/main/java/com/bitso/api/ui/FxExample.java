package com.bitso.api.ui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bitso.api.exception.SocketDisconnectedException;
import com.bitso.api.main.InitApp;
import com.bitso.configuration.DataConfiguration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class FxExample extends Application {

	@Override
	public void stop() throws Exception {
		InitApp.getInstance().stop();

	}

	private static final Logger logger = Logger.getLogger(FxExample.class);

	private DataConfiguration data;

	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		
		Path path = Paths.get("src/main/resources/com/bitso/api/ui/fxml/example.fxml");
		FileInputStream fxmlStream;
		
//		String classLoader =getClass().getClassLoader().getResource("com/bitso/api/main/InitApp.class").toString();
//		String classLoader =getClass().getClassLoader().getResource("com/bitso/api/ui/fxml/example.fxml").toString();
		
		
		InputStream classLoaderUrl =getClass().getResourceAsStream("");
		
		BufferedReader bR = new BufferedReader(new InputStreamReader( classLoaderUrl));
		String classLoader =classLoaderUrl.toString();
		
		
//		Path path = Paths.get("src/main/resources/com/bitso/api/ui/fxml/example.fxml");
//		FileInputStream fxmlStream = new FileInputStream(path.toAbsolutePath().toFile());
//		FileInputStream fxmlStream = new FileInputStream(classLoader);
//		FileInputStream fxmlStream = classLoaderUrl;
		
		
		ApplicationContext ac = InitApp.getInstance().getApplicationContext();
		data=ac.getBean(DataConfiguration.class);
		data.setTotalRecentTrades(15);
		data.setTotalBestTrades(15);
		data.setUpTicketsStrategy(1);
		data.setDownTicketsStrategy(1);
		
		fxmlStream = new FileInputStream(path.toAbsolutePath().toFile());
		
		VBox root = (VBox) loader.load(fxmlStream);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("title");
		stage.show();
	}

	public static void main(String[] args) {

		try {
			InitApp.getInstance().start();

			ApplicationContext ac = InitApp.getInstance().getApplicationContext();

			DataConfiguration data = ac.getBean(DataConfiguration.class);

//			Integer recentTrades = Integer.parseInt(args[0]);
//			Integer topTrades = Integer.parseInt(args[1]);
//			Integer setUpTicketsStrategy = Integer.parseInt(args[2]);
//			Integer setDownTicketsStrategy = Integer.parseInt(args[3]);
			 Integer recentTrades = 15;
			 Integer topTrades = 12;
			 Integer setUpTicketsStrategy = 3;
			 Integer setDownTicketsStrategy = 1;

			data = ac.getBean(DataConfiguration.class);	
			data.setTotalRecentTrades(recentTrades);
			data.setTotalBestTrades(topTrades);
			data.setUpTicketsStrategy(setUpTicketsStrategy);
			data.setDownTicketsStrategy(setDownTicketsStrategy);

			launch(args);
			logger.debug("start");
		} catch (SocketDisconnectedException e) {
			logger.error("Error while connecting to socket ");
			logger.error(e);
		}

	}

}
