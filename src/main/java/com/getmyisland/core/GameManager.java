package com.getmyisland.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.getmyisland.core.game.Card;
import com.getmyisland.core.game.Card.Color;
import com.getmyisland.core.game.Card.Value;
import com.getmyisland.core.game.Deck;
import com.getmyisland.core.game.DiscardPile;
import com.getmyisland.core.game.Player;
import com.getmyisland.fx.GameViewController;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogEvent;

public class GameManager {
	private final GameViewController gameViewController;

	private List<Player> players = new ArrayList<>();
	private Player currentPlayer;

	private Deck deck;
	private DiscardPile discardPile;

	private Color wishColor = null;

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

	private void updateCards() {
		if (deck.isEmpty()) {
			// Create a new deck from the discard pile
			deck = new Deck(discardPile.getCards());

			// Clear the discard pile
			discardPile = new DiscardPile(deck.drawCard());
		}

		if (wishColor != null) {
			discardPile.getTopCard().changeWishColor(wishColor);
		}

		gameViewController.displayTopDiscardPileCard(discardPile.getTopCard());
	}

	private void onPlayerTurnEnd(boolean skipNextPlayer) {
		updateCards();

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

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Skipped Player");
			alert.setContentText("Skipped " + players.get(players.size() - 2).getName());
			alert.showAndWait();
		}

		gameViewController.updateCurrentPlayerText(currentPlayer);
		displayNextPlayerInformationPopup();
	}

	private void displayNextPlayerInformationPopup() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Next Player");
		alert.setContentText("The next player will be " + currentPlayer.getName());
		alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
			public void handle(DialogEvent event) {
				updatePlayerCards();
			}
		});
		alert.show();
	}

	private void displayColorWishPopup() {
		if (wishColor != null) {
			return;
		}

		ChoiceDialog<Color> dialog = new ChoiceDialog<>(Color.values()[0], Arrays.asList(Color.values()));
		dialog.setTitle("Wish Color Selection");
		dialog.setHeaderText("Select the color of your choice");
		dialog.setContentText("Choose a color");
		dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
			@Override
			public void handle(DialogEvent event) {
				// Get the value
				wishColor = dialog.getResult();

				onPlayerTurnEnd(false);
			}
		});

		dialog.show();
	}

	public void updatePlayerCards() {
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

		// Reset the wish color to null
		wishColor = null;

		if (card.getValue() == Value.BUBE) {
			// Display wished color
			displayColorWishPopup();
		} else {
			// If its an ass don't end the turn
			if (card.getValue() != Card.Value.ASS) {
				onPlayerTurnEnd(card.getValue() == Card.Value.ACHT);
			} else {
				updateCards();
				updatePlayerCards();
			}
		}
	}
}
