package com.serhatmerak.cozveoyna.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Serhat Merak on 11.02.2018.
 */

public class CustomScreen implements Screen {

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public Viewport viewport;

    public CustomScreen(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,GameInfo.WIDTH  ,GameInfo.HEIGHT);
        viewport = new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,camera);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched())
            if(GameManager.getInstance().gameData.isSoundOpen())
                Assests.getInstance().assetManager.get(Assests.sndClick, Sound.class).play();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
