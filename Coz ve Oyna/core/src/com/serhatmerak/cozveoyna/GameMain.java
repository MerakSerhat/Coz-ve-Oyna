package com.serhatmerak.cozveoyna;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.GameManager;
import com.serhatmerak.cozveoyna.jumper.JumperScreen;
import com.serhatmerak.cozveoyna.screens.SplashScreen;
import com.serhatmerak.cozveoyna.sorular.FileReader;

public class GameMain extends Game {

	@Override
	public void create () {
		Assests.getInstance().create();
		FileReader fileReader = new FileReader();
		fileReader.writeSorular();
		GameManager.getInstance().initializeGameData();

		setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
