package com.getmyisland.fx;

import java.util.ArrayList;
import java.util.List;

import com.getmyisland.core.GameManager;
import com.getmyisland.core.game.Card;
import com.getmyisland.core.game.Player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * TODO Label for current player
 * TODO Button to go back to config menu
 * TODO Wünscher Popup
 * 
 * @author MFI
 *
 */
public class GameViewController {
	private final GameManager gameManager = new GameManager(this);
	
	private List<Button> cardButtons = new ArrayList<>();
	
	@FXML
	private Button btnCard01;
	
	@FXML
	private Button btnCard02;
	
	@FXML
	private Button btnCard03;
	
	@FXML
	private Button btnCard04;
	
	@FXML
	private Button btnCard05;
	
	@FXML
	private Button btnCard06;
	
	@FXML
	private Button btnCard07;
	
	@FXML
	private Button btnCard08;
	
	@FXML
	private Button btnCard09;
	
	@FXML
	private Button btnCard10;
	
	@FXML
	private Button btnCard11;
	
	@FXML
	private Button btnDrawCard;
	
	@FXML
	private Button btnDeckCard;
	
	@FXML
	private Button btnDiscardPile;
	
	public void initialize() {
		// Populate Card Buttons
		cardButtons.add(btnCard01);
		cardButtons.add(btnCard02);
		cardButtons.add(btnCard03);
		cardButtons.add(btnCard04);
		cardButtons.add(btnCard05);
		cardButtons.add(btnCard06);
		cardButtons.add(btnCard07);
		cardButtons.add(btnCard08);
		cardButtons.add(btnCard09);
		cardButtons.add(btnCard10);
		cardButtons.add(btnCard11);
		
		gameManager.startGame();
	}
	
	public void populateCardButtons(Player currentPlayer) {
		if(currentPlayer == null || currentPlayer.getHand().isEmpty()) {
			return;
		}
		
		if(cardButtons == null || cardButtons.isEmpty()) {
			return;
		}
		
		for(int i = 0; i < cardButtons.size(); i++) {
			if(cardButtons.get(i) == null) {
				continue;
			}
			
			if(i < currentPlayer.getHand().size()) {
				// Get the card
				final Card card = currentPlayer.getHand().get(i);
				
				cardButtons.get(i).setText(card.getFormattedText());
				cardButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						gameManager.playCard(card);
					}					
				});
			}
			else {
				cardButtons.get(i).setText("");
				cardButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// Do nothing
					}					
				});
			}
		}
	}
	
	public void clearCardButtons() {
		if(cardButtons == null || cardButtons.isEmpty()) {
			return;
		}
		
		for(Button button : cardButtons) {
			if(button == null) {
				continue;
			}
			
			button.setText("");
		}
	}
	
	public void displayTopDiscardPileCard(Card card) {
		if(btnDiscardPile == null) {
			return;
		}
		
		btnDiscardPile.setText(card.getFormattedText());
	}
	
	@FXML
	public void onBtnDrawCardPressed() {
		gameManager.playerDrawCard();
	}
}
