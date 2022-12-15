package com.getmyisland.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.getmyisland.core.game.Card;
import com.getmyisland.core.game.Card.Suit;
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

	private int currentDrawPenalty = 0;

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
		gameViewController.displayTopDiscardPileCard(discardPile.getTopCard());

		// Show cards for the next player
		gameViewController.populateCardButtons(currentPlayer);
	}

	private void consumeCardEvent(boolean playerPlayedCard) {
		if (!playerPlayedCard) {
			return;
		}

		Card topCard = discardPile.getTopCard();

		if (topCard.getValue() == Value.JACK) {
			displaySuitWishPopup();
		}

		if (topCard.getValue() != Value.EIGHT) {
			// Calculate the next player
			currentPlayer = players.get(0);
			Collections.rotate(players, -1);
		} else {
			// Calculate the next player but skip the next player
			currentPlayer = players.get(1);
			Collections.rotate(players, -2);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("8 of " + topCard.getSuit().toString() + "S played");
			alert.setContentText(players.get(players.size() - 2).getName() + " will be skipped this round");
			alert.showAndWait();
		}

		if (topCard.getValue() == Value.SEVEN) {
			currentDrawPenalty += 2;

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("7 of " + topCard.getSuit().toString() + "S played");
			alert.setContentText("Next player with no 7 must draw " + currentDrawPenalty + " cards");
			alert.showAndWait();
		} else {
			currentDrawPenalty = 0;
		}
	}

	private void onPlayerTurnEnd(boolean playerPlayedCard) {
		updateCards();

		if (!playerPlayedCard) {
			// Calculate the next player
			currentPlayer = players.get(0);
			Collections.rotate(players, -1);
		} else {
			consumeCardEvent(playerPlayedCard);
		}

		// Clear the cards for the current player
		gameViewController.clearCardButtons();

		gameViewController.updateCurrentPlayerText(currentPlayer);
		displayNextPlayerInformationPopup();
	}

	private void onPlayerTurnStart() {
		if (currentDrawPenalty > 0) {
			boolean sevenFound = false;
			for (Card card : currentPlayer.getHand()) {
				if (card.getValue() == Value.SEVEN) {
					// Player has a seven
					sevenFound = true;
					playCard(card);
					break;
				}
			}

			if (!sevenFound) {
				for (int i = 0; i < currentDrawPenalty; i++) {
					// The current player draws a card and ends the turn
					currentPlayer.addCardToHand(deck.drawCard());
				}

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Draw Card");
				alert.setContentText(
						players.get(players.size() - 1).getName() + " drew " + currentDrawPenalty + " cards");
				alert.showAndWait();

				currentDrawPenalty = 0;
				onPlayerTurnEnd(false);
				return;
			}
		}

		updateCards();
	}

	private void displayNextPlayerInformationPopup() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Next Player");
		alert.setContentText("The next player will be " + currentPlayer.getName());
		alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
			public void handle(DialogEvent event) {
				onPlayerTurnStart();
			}
		});
		alert.show();
	}

	private void displaySuitWishPopup() {
		ChoiceDialog<Suit> dialog = new ChoiceDialog<>(Suit.values()[0], Arrays.asList(Suit.values()));
		dialog.setTitle("Information");
		dialog.setHeaderText("Select a suit");
		dialog.setContentText("Choose suit");
		dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
			@Override
			public void handle(DialogEvent event) {
				// Get the value
				discardPile.getTopCard().wishForSuit(dialog.getResult());
			}
		});
		dialog.showAndWait();
	}

	public void playerDrawCard() {
		if (deck.isEmpty()) {
			// Create a new deck from the discard pile
			deck = new Deck(discardPile.getCards());

			// Clear the discard pile
			discardPile = new DiscardPile(deck.drawCard());
		}

		// The current player draws a card and ends the turn
		currentPlayer.addCardToHand(deck.drawCard());
		onPlayerTurnEnd(false);
	}

	public void playCard(Card card) {
		Card topCard = discardPile.getTopCard();

		// Check if the card can be placed
		if (topCard.getSuit() != card.getSuit() && topCard.getValue() != card.getValue()) {
			return;
		}

		// Remove the played card from the players hand
		currentPlayer.removeCardFromHand(card);

		// Add the card to the discard pile
		discardPile.discardCard(card);

		if (card.getValue() != Card.Value.ASS) {
			onPlayerTurnEnd(true);
		} else {
			updateCards();
		}
	}
}
