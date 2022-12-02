package com.getmyisland.core.game;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {
	private final List<Card> cards = new ArrayList<>();
	
	public DiscardPile() {
		cards.clear();
	}

	public void discardCard(Card cardToDiscard) {
		cards.add(cardToDiscard);
	}
	
	public void clearDiscardPile() {
		cards.clear();
	}
	
	/**
	 * @return the cards
	 */
	public List<Card> getCards() {
		return cards;
	}
}
