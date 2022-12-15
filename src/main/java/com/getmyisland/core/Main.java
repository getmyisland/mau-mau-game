package com.getmyisland.core;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage = null;

	@Override
	public void start(Stage primaryStage) throws IOException {
		// Set the stage
		Main.stage = primaryStage;

		// Set the stage values
		primaryStage.setTitle("Mau Mau Game");
		primaryStage.setResizable(true);

		loadGameConfig();

		primaryStage.show();
	}

	public static void loadGameConfig() {
		try {
			System.out.println("Loading game config...");
			
			// Load a scene with fxml loader
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/GameConfig.fxml"));
			Scene scene;

			scene = new Scene((Parent) fxmlLoader.load());

			// Set the loaded scene
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadGameView() {
		try {
			System.out.println("Loading game stage...");
			
			// Load a scene with fxml loader
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/GameView.fxml"));
			Scene scene;

			scene = new Scene((Parent) fxmlLoader.load());

			// Set the loaded scene
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
