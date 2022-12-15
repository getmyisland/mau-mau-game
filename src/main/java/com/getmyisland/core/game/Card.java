package com.getmyisland.core.game;

/**
 * Card class that holds information like {@link Suit} and {@link Value}
 * 
 * @author MFI
 *
 */
public class Card {
	/**
	 * The possible suits of the {@link Card}
	 * 
	 * @author MFI
	 *
	 */
	public enum Suit {
		CLUB,
		SPADE,
		HEART,
		DIAMOND
	}
	
	/**
	 * The possible values of the {@link Card}
	 * 
	 * @author MFI
	 *
	 */
	public enum Value {
		SIX,
		SEVEN, // Draw 2
		EIGHT, // Skip next player
		NINE,
		TEN,
		JACK, // Wish for a suit
		QUEEN,
		KING,
		ACE // You can play another card
	}
	
	/** The {@link Suit} of the {@link Card} */
	private Suit suit;	
	/** The {@link Value} of the {@link Card} */
	private final Value value;
	
	public Card(final Suit suit, final Value value) {
		this.suit = suit;
		this.value = value;
	}
	
	/**
	 * Returns the text in a formatted format.
	 * 
	 * @return the formatted text
	 */
	public String getFormattedText() {
		return this.suit + "\n" + this.value;
	}
	
	/**
	 * Changes the suit of a jack {@link Card}.
	 * 
	 * @param suit the new {@link #suit}
	 */
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
