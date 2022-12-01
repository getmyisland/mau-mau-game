package com.getmyisland.fx;

import java.io.IOException;

import com.getmyisland.core.GameConfig;
import com.getmyisland.core.Main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class GameConfigController {
	@FXML
	private Slider sliderPlayerAmount;

	@FXML
	private Label labelPlayerAmount;

	@FXML
	private Slider sliderCardsPerPlayer;

	@FXML
	private Label labelCardsPerPlayer;

	@FXML
	private Label labelCalculatedCardsLeftInDeck;

	@FXML
	private TextArea txtAreaErrorLog;

	@FXML
	private Button btnPlay;

	public void initialize() {
		sliderPlayerAmount.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				labelPlayerAmount.textProperty().setValue(String.valueOf(newValue.intValue()));
				CalculateCardsLeftInDeck();
			}

		});

		sliderCardsPerPlayer.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				labelCardsPerPlayer.textProperty().setValue(String.valueOf(newValue.intValue()));
				CalculateCardsLeftInDeck();
			}

		});

		labelPlayerAmount.textProperty().setValue(String.valueOf((int) sliderPlayerAmount.getValue()));
		labelCardsPerPlayer.textProperty().setValue(String.valueOf((int) sliderCardsPerPlayer.getValue()));
		CalculateCardsLeftInDeck();
	}

	/**
	 * Calculates the cards left in the deck with the current values. If there are
	 * less than 5 cards in the deck the Play button is disabled.
	 */
	private void CalculateCardsLeftInDeck() {
		int cardsLeftInDeck = GameConfig.CARDS_PER_DECK
				- ((int) sliderPlayerAmount.getValue() * (int) sliderCardsPerPlayer.getValue());
		labelCalculatedCardsLeftInDeck.textProperty()
				.setValue(String.valueOf(cardsLeftInDeck + " cards left after each Player draws his card"));

		if (cardsLeftInDeck < 5) {
			txtAreaErrorLog.textProperty().setValue(
					"After each player has drawn his cards, there should be at least 5 cards left in the deck.");
			btnPlay.setDisable(true);
		} else {
			txtAreaErrorLog.textProperty().setValue("");
			btnPlay.setDisable(false);
		}
	}

	@FXML
	public void onQuitButtonClicked() {
		System.out.println("Exiting game...");
		System.exit(0);
	}

	@FXML
	public void onPlayButtonClicked() {
		System.out.println("Loading game stage...");

		GameConfig.PLAYERS = (int) sliderPlayerAmount.getValue();
		System.out.println(GameConfig.PLAYERS + " Player(s)");
		GameConfig.CARD_AMOUNT_PER_PLAYER = (int) sliderCardsPerPlayer.getValue();
		System.out.println(GameConfig.CARD_AMOUNT_PER_PLAYER + " cards per Player");

		try {
			// Load a scene with fxml loader
			FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/GameView.fxml"));
			Scene scene = new Scene((Parent) fxmlLoader.load());

			// Set the loaded scene
			Main.stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}