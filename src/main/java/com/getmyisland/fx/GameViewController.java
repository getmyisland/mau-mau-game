package com.getmyisland.fx;

import com.getmyisland.core.GameManager;

public class GameViewController {
	private final GameManager gameManager = new GameManager();
	
	public void initialize() {
		gameManager.StartGame();
	}
}
