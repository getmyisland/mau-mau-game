package com.getmyisland.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.getmyisland.core.game.Card;
import com.getmyisland.core.game.Deck;
import com.getmyisland.core.game.DiscardPile;
import com.getmyisland.core.game.Player;
import com.getmyisland.fx.GameViewController;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogEvent;

public class GameManager {
	private final GameViewController gameViewController;

	private List<Player> players = new ArrayList<>();
	private Player currentPlayer;

	private Deck deck;
	private DiscardPile discardPile;

	public GameManager(final GameViewController gameViewController) {
		this.gameViewController = gameViewController;
	}

	public void startGame() {
		// Create a new deck
		deck = new Deck();
		// Create new discard pile
		discardPile = new DiscardPile(deck.drawCard());

		// Clear players just for safety
		players.clear();

		// Create Players
		for (int i = 0; i < GameConfig.PLAYERS; i++) {
			players.add(new Player("Player " + (i + 1)));
		}

		// Give cards to each Player
		for (int i = 0; i < GameConfig.CARD_AMOUNT_PER_PLAYER; i++) {
			for (Player player : players) {
				player.addCardToHand(deck.drawCard());
			}
		}

		onPlayerTurnEnd(false);
	}

	private void onPlayerTurnEnd(boolean skipNextPlayer) {
		if (deck.isEmpty()) {
			// Create a new deck from the discard pile
			deck = new Deck(discardPile.getCards());

			// Clear the discard pile
			discardPile = new DiscardPile(deck.drawCard());
		}

		gameViewController.displayTopDiscardPileCard(discardPile.getTopCard());

		// Clear the cards for the current player
		gameViewController.clearCardButtons();

		if (!skipNextPlayer) {
			// Calculate the next player
			currentPlayer = players.get(0);
			Collections.rotate(players, -1);
		} else {
			// Calculate the next player but skip the next player
			currentPlayer = players.get(1);
			Collections.rotate(players, -2);
		}

		displayNextPlayerInformationPopup();
	}

	private void displayNextPlayerInformationPopup() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Next Player");
		alert.setContentText("The next player will be " + currentPlayer.getName());
		alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
			public void handle(DialogEvent event) {
				onNextPlayerTurn();
			}
		});
		alert.show();
	}

	public void onNextPlayerTurn() {
		// Show cards for the next player
		gameViewController.populateCardButtons(currentPlayer);
	}

	public void playerDrawCard() {
		// The current player draws a card and ends the turn
		currentPlayer.addCardToHand(deck.drawCard());
		onPlayerTurnEnd(false);
	}

	public void playCard(Card card) {
		Card topCard = discardPile.getTopCard();

		// Check if the card can be placed
		if (topCard.getColor() != card.getColor() && topCard.getValue() != card.getValue()) {
			return;
		}

		// Remove the played card from the players hand
		currentPlayer.removeCardFromHand(card);

		// Add the card to the discard pile
		discardPile.discardCard(card);

		// If its an ass don't end the turn
		if (card.getValue() != Card.Value.ASS) {
			onPlayerTurnEnd(card.getValue() == Card.Value.ACHT);
		}
	}
}
