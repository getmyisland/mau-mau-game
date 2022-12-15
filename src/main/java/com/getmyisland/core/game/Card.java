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
		SIEBEN, // Zwei-Ziehen
		ACHT, // Aussetzen???
		NEUN, // Alleskönner
		ZEHN,
		BUBE, // Wünscher
		DAME,
		KOENIG,
		ASS // Man darf nochmal legen
	}
	
	private Color color;	
	private final Value value;
	
	public Card(final Color color, final Value value) {
		this.color = color;
		this.value = value;
	}
	
	public String getFormattedText() {
		return this.color + "\n" + this.value;
	}
	
	public void changeWishColor(final Color newColor) {
		this.color = newColor;
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
