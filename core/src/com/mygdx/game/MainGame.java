package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.extra.AssetMan;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GameWinScreen;
import com.mygdx.game.screens.GetReadyScreen;

public class MainGame extends Game {

	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;
	public GetReadyScreen getReadyScreen;
	public GameWinScreen gameWinScreen;

	public AssetMan assetManager;

	@Override
	public void create() {
		this.assetManager = new AssetMan();

		this.gameScreen = new GameScreen(this);
		this.gameOverScreen = new GameOverScreen(this);
		this.getReadyScreen = new GetReadyScreen(this);
		this.gameWinScreen = new GameWinScreen(this);
		setScreen(this.getReadyScreen);
	}
}
