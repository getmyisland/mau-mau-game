package com.getmyisland.core.game;

import java.util.ArrayList;
import java.util.List;

public class DiscardPile {
	/** The {@link Card}s on the discard pile */
	private final List<Card> cards = new ArrayList<>();
	
	public DiscardPile(Card card) {
		cards.clear();
		cards.add(card);
	}

	/**
	 * The discarded {@link Card}.
	 * 
	 * @param cardToDiscard the {@link Card} that should be discarded
	 */
	public void discardCard(Card cardToDiscard) {
		cards.add(cardToDiscard);
	}
	
	/**
	 * Returns the top {@link Card} of the {@link DiscardPile}
	 * 
	 * @return the top {@link Card}
	 */
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
