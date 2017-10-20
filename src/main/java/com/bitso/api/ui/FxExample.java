package com.bitso.api.ui;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
	
	@Autowired
	private DataConfiguration data;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Path path = Paths.get("src/main/resources/com/bitso/api/ui/fxml/example.fxml");
		FileInputStream fxmlStream = new FileInputStream(path.toAbsolutePath().toFile());
		ApplicationContext ac = InitApp.getInstance().getApplicationContext();
		
		System.out.println("data config "+data);
		DataConfiguration data=ac.getBean(DataConfiguration.class);
		System.out.println("data config2 "+data);
		VBox root = (VBox) loader.load(fxmlStream);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("title");
		stage.show();
	}
	
	public static void main(String[] args) {
		
		InitApp.getInstance().start();
		launch(args);
	}
	
}
