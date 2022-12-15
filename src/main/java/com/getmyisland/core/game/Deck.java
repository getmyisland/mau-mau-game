package com.getmyisland.core.game;

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
		
		shuffleDeck();
	}
	
	private void shuffleDeck() {
		Collections.shuffle(cards);
	}
	
	public Card drawCard() {
		if(cards == null || cards.isEmpty()) {
			return null;
		}
		
		// Return the first card
		Card card = cards.get(0);
		cards.remove(0);
		
		return card;
	}
	
	public boolean isEmpty() {
		if(cards == null) {
			return true;
		}
		
		return cards.isEmpty();
	}
}
