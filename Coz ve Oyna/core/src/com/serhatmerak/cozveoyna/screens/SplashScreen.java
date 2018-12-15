package com.serhatmerak.cozveoyna.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.AllScreens;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.CustomScreen;
import com.serhatmerak.cozveoyna.helpers.ScreenAnimation;

/**
 * Created by Serhat Merak on 11.02.2018.
 */

public class SplashScreen extends CustomScreen {
    private GameMain game;
    private Texture bg;
    private float elapsedTime;
    private boolean assestsLoaded = false;
    private ScreenAnimation screenAnimation;



    public SplashScreen(GameMain game){
        this.game = game;
        bg = new Texture("first bg.png");
        bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        screenAnimation = new ScreenAnimation(game);
        screenAnimation.setNextScreen(AllScreens.MainMenu);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Assests.getInstance().assetManager.update();

        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
            batch.draw(bg,0,0);
        batch.end();

        if(Assests.getInstance().assetManager.isLoaded(Assests.pixWhite)){
            Assests.getInstance().setTextureFilters();
            assestsLoaded = true;
        }

        if(assestsLoaded && elapsedTime>2.5f)
            screenAnimation.changeScreen(batch);
    }
}
