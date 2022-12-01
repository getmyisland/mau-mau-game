package com.getmyisland.core.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	/** Holds all cards */
	private List<Card> deck = new ArrayList<Card>();
	
	public Deck() {
		PopulateDeck();
	}
	
	/**
	 * Populates the deck with new {@link Card}s<br>
	 * Each card is only present once
	 */
	private void PopulateDeck() {
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j <= 8; j++) {
				// Create new card and add it to the list
				deck.add(new Card(Card.Color.values()[i], Card.Value.values()[j]));
			}
		}
		
		ShuffleDeck();
	}
	
	public void ShuffleDeck() {
		Collections.shuffle(deck);
	}
}
