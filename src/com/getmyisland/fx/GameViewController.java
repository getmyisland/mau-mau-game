package com.getmyisland.fx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.getmyisland.core.GameManager;
import com.getmyisland.core.Main;
import com.getmyisland.game.Card;
import com.getmyisland.game.Player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * TODO better way of handling sevens and eigth
 * 
 * @author MFI
 *
 */
public class GameViewController {
	private GameManager gameManager;
	private Player currentPlayer;

	@FXML
	private Label labelCurrentPlayer;

	private List<Button> cardButtons = new ArrayList<>();
	private int cardOffset = 0;

	@FXML
	private Button btnCard01;

	@FXML
	private Button btnCard02;

	@FXML
	private Button btnCard03;

	@FXML
	private Button btnCard04;

	@FXML
	private Button btnCard05;

	@FXML
	private Button btnCard06;

	@FXML
	private Button btnCard07;

	@FXML
	private Button btnCard08;

	@FXML
	private Button btnCard09;

	@FXML
	private Button btnCard10;

	@FXML
	private Button btnCard11;

	@FXML
	private Button btnDrawCard;

	@FXML
	private Button btnDeckCard;

	@FXML
	private Button btnDiscardPile;

	public void initialize() {
		cardButtons.clear();
		
		// Populate Card Buttons
		cardButtons.add(btnCard01);
		cardButtons.add(btnCard02);
		cardButtons.add(btnCard03);
		cardButtons.add(btnCard04);
		cardButtons.add(btnCard05);
		cardButtons.add(btnCard06);
		cardButtons.add(btnCard07);
		cardButtons.add(btnCard08);
		cardButtons.add(btnCard09);
		cardButtons.add(btnCard10);
		cardButtons.add(btnCard11);

	 	gameManager = new GameManager(this);
		gameManager.startGame();
	}

	public void populateCardButtons(Player currentPlayer) {
		cardOffset = 0;
		this.currentPlayer = currentPlayer;

		populateCurrentPlayerCardButtons();
	}

	private void populateCurrentPlayerCardButtons() {
		if (currentPlayer == null || currentPlayer.getHand().isEmpty()) {
			return;
		}

		if (cardButtons == null || cardButtons.isEmpty()) {
			return;
		}

		List<Card> rotatedPlayerCards = currentPlayer.getHand();
		Collections.sort(rotatedPlayerCards, new Comparator<Card>() {
		      @Override
		      public int compare(Card card1, Card card2) {
		        if (card1.getSuit() == card2.getSuit()) {
		          return card1.getValue().compareTo(card2.getValue());
		        } else {
		          return card1.getSuit().compareTo(card2.getSuit());
		        }
		      }
		    });
		Collections.rotate(rotatedPlayerCards, -cardOffset);
		
		for (int i = 0; i < cardButtons.size(); i++) {
			if (cardButtons.get(i) == null) {
				continue;
			}

			if (i < currentPlayer.getHand().size()) {
				// Get the card
				final Card card = rotatedPlayerCards.get(i);

				cardButtons.get(i).setText(card.getFormattedText());
				cardButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						gameManager.playCard(card);
					}
				});
			} else {
				cardButtons.get(i).setText("");
				cardButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// Do nothing
					}
				});
			}
		}
	}

	public void clearCardButtons() {
		if (cardButtons == null || cardButtons.isEmpty()) {
			return;
		}

		for (Button button : cardButtons) {
			if (button == null) {
				continue;
			}

			button.setText("");
		}
	}

	public void displayTopDiscardPileCard(Card card) {
		if (btnDiscardPile == null) {
			return;
		}

		btnDiscardPile.setText(card.getFormattedText());
	}

	public void updateCurrentPlayerText(Player currentPlayer) {
		if (labelCurrentPlayer != null) {
			labelCurrentPlayer.setText("Current Player: " + currentPlayer.getName());
		}
	}

	@FXML
	public void onBtnDrawCardPressed() {
		gameManager.playerDrawCard();
	}

	@FXML
	public void onReturnToConfigMenuButtonPressed() {
		Main.loadGameConfig();
	}

	@FXML
	public void onLeftButtonPressed() {
		if (cardOffset == 1) {
			cardOffset = -1;
			populateCurrentPlayerCardButtons();
			cardOffset = 0;
		}

		cardOffset--;

		if (cardOffset < 0) {
			cardOffset = 0;
		}

		populateCurrentPlayerCardButtons();
	}

	@FXML
	public void onRightButtonPressed() {
		cardOffset++;

		if ((cardButtons.size() + cardOffset) > currentPlayer.getHand().size()
				|| currentPlayer.getHand().size() <= cardButtons.size()) {
			cardOffset--;
			return;
		}

		populateCurrentPlayerCardButtons();
	}
}
