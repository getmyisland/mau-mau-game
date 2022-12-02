package com.getmyisland.fx;

import com.getmyisland.core.GameManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * TODO Label for current and next player
 * TODO Button to go back to config menu
 * TODO Show Popup when next player and hide cards while popup is up
 * 
 * @author MFI
 *
 */
public class GameViewController {
	private final GameManager gameManager = new GameManager();
	
	@FXML
	private Button btnDrawCard;
	
	public void initialize() {
		gameManager.startGame();
	}
	
	@FXML
	public void onBtnDrawCardPressed() {
		gameManager.playerDrawCard();
	}
}
