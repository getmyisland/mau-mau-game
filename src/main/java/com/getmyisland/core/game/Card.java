package com.getmyisland.core.game;

/**
 * Card class that is supposed to represent a Schafkopf Tarock card
 */
public class Card {
	public enum Color {
		KREUZ, // Eichel
		PIK, // Laub
		HERZ,
		KARO // Schellen
	}
	
	public enum Value {
		SECHS,
		SIEBEN,
		ACHT,
		NEUN,
		ZEHN,
		BUBE,
		DAME,
		KOENIG,
		ASS
	}
	
	private final Color color;
	
	private final Value value;
	
	public Card(final Color color, final Value value) {
		this.color = color;
		this.value = value;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the value
	 */
	public Value getValue() {
		return value;
	}
}
