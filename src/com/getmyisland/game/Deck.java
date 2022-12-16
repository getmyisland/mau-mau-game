package com.getmyisland.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	/** Holds all cards */
	private List<Card> cards = new ArrayList<>();
	
	public Deck() {
		populateDeck();
	}
	
	public Deck(List<Card> cards) {
		this.cards = cards;
	}
	
	/**
	 * Populates the deck with new {@link Card}s<br>
	 * Each card is only present once
	 */
	private void populateDeck() {
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j <= 8; j++) {
				// Create new card and add it to the list
				cards.add(new Card(Card.Suit.values()[i], Card.Value.values()[j]));
			}
		}
		
		// Shuffle the deck after
		shuffleDeck();
	}
	
	/**
	 * Shuffles the deck.
	 */
	private void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
	/**
	 * Draws the top {@link Card} from the {@link Deck}
	 * 
	 * @return the top {@link Card}
	 */
	public Card drawCard() {
		if(cards == null || cards.isEmpty()) {
			return null;
		}
		
		// Return the first card
		Card card = cards.get(0);
		cards.remove(0);
		
		return card;
	}
	
	/**
	 * Checks if the deck is empty.
	 * 
	 * @return true if deck is empty
	 */
	public boolean isEmpty() {
		if(cards == null) {
			return true;
		}
		
		return cards.isEmpty();
	}
}
