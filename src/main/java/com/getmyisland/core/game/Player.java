package com.getmyisland.core.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private final String name;
	
	private List<Card> hand = new ArrayList<Card>();
	
	public Player(final String name) {
		this.name = name;
	}

	/**
	 * Adds a card to the player hand.
	 * 
	 * @param card to add
	 */
	public void AddCardToHand(Card card) {
		hand.add(card);
	}
	
	/**
	 * Removes a card from the player hand
	 * 
	 * @param card to remove
	 */
	public void RemoveCardFromHand(Card card) {
		hand.remove(card);
	}
	
	/**
	 * @return the hand
	 */
	public List<Card> getHand() {
		return hand;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
