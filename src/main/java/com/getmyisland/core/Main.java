package com.getmyisland.core;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {

		// Set the stage values
		primaryStage.setTitle("Mau Mau Game");
		primaryStage.setResizable(true);
		primaryStage.setMinWidth(1280);
		primaryStage.setMinHeight(720);
		
		// Load a scene with fxml loader
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/GameView.fxml"));
		Scene scene = new Scene((Parent) fxmlLoader.load());
		
		// Set the loaded scene
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
