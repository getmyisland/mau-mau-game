package com.getmyisland.core.game;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {
	private final List<Card> cards = new ArrayList<>();
	
	public DiscardPile(Card card) {
		cards.clear();
		cards.add(card);
	}

	public void discardCard(Card cardToDiscard) {
		cards.add(cardToDiscard);
	}
	
	public Card getTopCard() {
		return cards.get(cards.size() - 1);
	}
	
	/**
	 * @return the cards
	 */
	public List<Card> getCards() {
		return cards;
	}
}
