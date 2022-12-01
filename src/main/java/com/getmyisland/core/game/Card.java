package com.getmyisland.core.game;

/**
 * Card class that is supposed to represent a Schafkopf Tarock card
 */
public class Card {
	public enum Color {
		Kreuz, // Eichel
		Pik, // Laub
		Herz,
		Karo // Schellen
	}
	
	public enum Value {
		Sechs,
		Sieben,
		Acht,
		Neun,
		Zehn,
		Bube,
		Dame,
		Koenig,
		Ass
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
