package com.shane.tapit;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
		setScreen(new StartScreen(this));
	}
}
