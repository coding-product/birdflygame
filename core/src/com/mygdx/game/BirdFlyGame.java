package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BirdFlyGame extends Game {

	GameScreen ThisGameScreen;
	MainMenuScreen ThisMainMenuScreen;

	final int VIEW_WIDTH = 960;
	final int VIEW_HEIGHT = 600;

	@Override
	public void create ()
	{

		// игровой экран
		ThisGameScreen = new GameScreen(this);
		// экран меню с кнопками
		ThisMainMenuScreen = new MainMenuScreen(this);
		Gdx.input.setCatchBackKey(true);
		this.setScreen(ThisMainMenuScreen);
	}

	public void render ()
	{
		super.render();
	}

}
