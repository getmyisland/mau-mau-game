package com.getmyisland.core.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	/** The name of the {@link Player} */
	private final String name;
	
	/** The {@link Player}s {@link Card}s on the hand */
	private List<Card> hand = new ArrayList<>();
	
	public Player(final String name) {
		this.name = name;
	}

	/**
	 * Adds a card to the player hand.
	 * 
	 * @param card to add
	 */
	public void addCardToHand(Card card) {
		hand.add(card);
	}
	
	/**
	 * Removes a card from the player hand
	 * 
	 * @param card to remove
	 */
	public void removeCardFromHand(Card card) {
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
