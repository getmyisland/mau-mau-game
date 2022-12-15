package com.getmyisland.fx;

import com.getmyisland.core.GameConfig;
import com.getmyisland.core.Main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
				calculateCardsLeftInDeck();
			}

		});

		sliderCardsPerPlayer.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				labelCardsPerPlayer.textProperty().setValue(String.valueOf(newValue.intValue()));
				calculateCardsLeftInDeck();
			}

		});

		labelPlayerAmount.textProperty().setValue(String.valueOf((int) sliderPlayerAmount.getValue()));
		labelCardsPerPlayer.textProperty().setValue(String.valueOf((int) sliderCardsPerPlayer.getValue()));
		calculateCardsLeftInDeck();
	}

	/**
	 * Calculates the cards left in the deck with the current values. If there are
	 * less than 5 cards in the deck the Play button is disabled.
	 */
	private void calculateCardsLeftInDeck() {
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
		GameConfig.PLAYERS = (int) sliderPlayerAmount.getValue();
		System.out.println(GameConfig.PLAYERS + " Player(s)");
		GameConfig.CARD_AMOUNT_PER_PLAYER = (int) sliderCardsPerPlayer.getValue();
		System.out.println(GameConfig.CARD_AMOUNT_PER_PLAYER + " cards per Player");
		
		Main.loadGameView();
	}
}