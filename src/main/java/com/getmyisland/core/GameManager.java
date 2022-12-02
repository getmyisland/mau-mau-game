package com.getmyisland.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.getmyisland.core.game.Card;
import com.getmyisland.core.game.Deck;
import com.getmyisland.core.game.DiscardPile;
import com.getmyisland.core.game.Player;

public class GameManager {
	private List<Player> players = new ArrayList<>();
	private Player currentPlayer;
	
	private Deck deck;
	private DiscardPile discardPile;

	public void startGame() {
		// Create a new deck
		deck = new Deck();		
		// Create new discard pile
		discardPile = new DiscardPile();

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
		
		nextTurn();
	}
	
	private void nextTurn() {
		currentPlayer = players.get(0);		
		Collections.rotate(players, -1);
		
		System.out.println(currentPlayer.getName());
		
		if(deck.isEmpty()) {
			// Create a new deck from the discard pile
			deck = new Deck(discardPile.getCards());
			
			// Clear the discard pile
			discardPile.clearDiscardPile();
		}
	}
	
	public void playerDrawCard() {
		// The current player draws a card and ends the turn
		currentPlayer.addCardToHand(deck.drawCard());		
		nextTurn();
	}
	
	public void playCard(Card card) {
		// Remove the played card from the players hand
		currentPlayer.removeCardFromHand(card);
		
		// Add the card to the discard pile
		discardPile.discardCard(card);
		
		// TODO Check if the card has any special abilites
		
		nextTurn();
	}
}
