package com.getmyisland.core.game;

public class Card {
	public enum Suit {
		CLUB,
		SPADE,
		HEART,
		DIAMOND
	}
	
	public enum Value {
		SIX,
		SEVEN, // Draw 2
		EIGHT, // Skip next player
		NINE,
		TEN,
		JACK, // Wish for a suit
		QUEEN,
		KING,
		ASS // You can play another card
	}
	
	private Suit suit;	
	private final Value value;
	
	public Card(final Suit suit, final Value value) {
		this.suit = suit;
		this.value = value;
	}
	
	public String getFormattedText() {
		return this.suit + "\n" + this.value;
	}
	
	public void wishForSuit(final Suit suit) {
		this.suit = suit;
	}

	/**
	 * @return the suit
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}
}
