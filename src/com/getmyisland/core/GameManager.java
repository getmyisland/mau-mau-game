package com.getmyisland.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.getmyisland.fx.GameViewController;
import com.getmyisland.game.Card;
import com.getmyisland.game.Card.Suit;
import com.getmyisland.game.Card.Value;
import com.getmyisland.game.Deck;
import com.getmyisland.game.DiscardPile;
import com.getmyisland.game.Player;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogEvent;

public class GameManager {
	/** The Game Ui controller */
	private final GameViewController gameViewController;

	/** The list of {@link Player}s */
	private List<Player> players = new ArrayList<>();
	/** The current {@link Player} */
	private Player currentPlayer;

	/** The {@link Card} {@link Deck} */
	private Deck deck;

	/** The {@link DiscardPile} were all cards are discarded */
	private DiscardPile discardPile;

	/** Draw penalty after a 7 gets played */
	private int currentDrawPenalty = 0;

	public GameManager(final GameViewController gameViewController) {
		this.gameViewController = gameViewController;
	}

	/**
	 * Start the game and set all settings
	 */
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

	/**
	 * Updates the {@link Card} ui for the {@link #discardPile} and {@link #currentPlayer}
	 */
	private void updateCards() {
		gameViewController.displayTopDiscardPileCard(discardPile.getTopCard());

		// Show cards for the next player
		gameViewController.populateCardButtons(currentPlayer);
	}

	/**
	 * Checks if the top {@link Card} of the {@link #discardPile} is a special {@link Card} like a
	 * Jack
	 */
	private void consumeCardEvent() {
		// Get the top card
		Card topCard = discardPile.getTopCard();

		// If the card is a jack
		if (topCard.getValue() == Value.JACK) {
			displaySuitWishPopup(topCard.getSuit());
		}

		// If the card is a eight
		if (topCard.getValue() != Value.EIGHT) {
			// Calculate the next player
			currentPlayer = players.get(0);
			Collections.rotate(players, -1);
		} else {
			// Calculate the next player but skip the next player
			currentPlayer = players.get(1);
			Collections.rotate(players, -2);

			// Show a information
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("8 of " + topCard.getSuit().toString() + " played");
			alert.setContentText(players.get(players.size() - 2).getName() + " will be skipped this round");
			alert.showAndWait();
		}

		// If the card is a seven
		if (topCard.getValue() == Value.SEVEN) {
			// Increase the draw penalty by 2
			currentDrawPenalty += 2;

			// Show an information to the player
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("7 of " + topCard.getSuit().toString() + " played");
			alert.setContentText("Next player with no 7 must draw " + currentDrawPenalty + " cards");
			alert.showAndWait();
		} else {
			// Reset the draw penalty
			currentDrawPenalty = 0;
		}
	}

	/**
	 * Gets called if the turn of a {@link Player} ends
	 * 
	 * @param playerPlayedCard if a {@link Player} played the card
	 */
	private void onPlayerTurnEnd(boolean playerPlayedCard) {
		updateCards();

		if (!playerPlayedCard) {
			// Calculate the next player
			currentPlayer = players.get(0);
			Collections.rotate(players, -1);
		} else {
			// If the card was played by a player consume the card event
			consumeCardEvent();
		}

		// Clear the cards for the current player
		gameViewController.clearCardButtons();

		// Display a information that shows the next player
		displayNextPlayerInformationPopup();
	}

	/**
	 * Gets called if the turn of a {@link Player} starts
	 */
	private void onPlayerTurnStart() {
		// If a seven was played last turn
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

			// If no seven was found in the player hand
			if (!sevenFound) {
				for (int i = 0; i < currentDrawPenalty; i++) {
					// The current player draws a card and ends the turn
					currentPlayer.addCardToHand(deck.drawCard());
				}

				// Display an information to the player
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Draw Card");
				alert.setContentText(
						players.get(players.size() - 1).getName() + " drew " + currentDrawPenalty + " cards");
				alert.showAndWait();

				// Reset the draw penalty
				currentDrawPenalty = 0;
				onPlayerTurnEnd(false);
				return;
			}
		}

		// Update the cards
		updateCards();

		// Update the current player text
		gameViewController.updateCurrentPlayerText(currentPlayer);
	}

	/**
	 * Display a information to the {@link Player} which {@link Player} will be next
	 */
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

	/**
	 * Open a wisher dialog to select a {@link Suit}
	 */
	private void displaySuitWishPopup(Suit suit) {
		ChoiceDialog<Suit> dialog = new ChoiceDialog<>(Suit.values()[0], Arrays.asList(Suit.values()));
		dialog.setTitle("Information");
		dialog.setHeaderText("Jack of " + suit.toString() + " played");
		dialog.setContentText("Choose suit");
		dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
			@Override
			public void handle(DialogEvent event) {
				Suit wishSuit = dialog.getResult();
				
				// If the player selects cancel use the default choice
				if(wishSuit == null) {
					wishSuit = dialog.getSelectedItem();
				}
				
				// Get the value
				discardPile.getTopCard().wishForSuit(wishSuit);
			}
		});
		dialog.showAndWait();
	}

	/**
	 * Draws a {@link Card} for the {@link #currentPlayer} from the {@link #deck}
	 */
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

	/**
	 * Plays a {@link Card}k for the {@link #currentPlayer}
	 * 
	 * @param card the {@link Card} that is played from the {@link #currentPlayer}
	 */
	public void playCard(Card card) {
		// Get the top card
		Card topCard = discardPile.getTopCard();

		// Check if the card can be placed
		if (topCard.getSuit() != card.getSuit() && topCard.getValue() != card.getValue()) {
			return;
		}

		// Remove the played card from the players hand
		currentPlayer.removeCardFromHand(card);

		// Add the card to the discard pile
		discardPile.discardCard(card);

		// If the card is an ace don't end the turn and just update the cards
		if (card.getValue() != Card.Value.ACE) {
			onPlayerTurnEnd(true);
		} else {
			updateCards();
		}
	}
}
