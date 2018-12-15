package com.serhatmerak.cozveoyna.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.Srt;
import com.serhatmerak.cozveoyna.helpers.AllScreens;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.CustomScreen;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;
import com.serhatmerak.cozveoyna.helpers.ScreenAnimation;
import com.serhatmerak.cozveoyna.huds.MainMenuHuds;

/**
 * Created by Serhat Merak on 23.02.2018.
 */

public class MainMenuScreen extends CustomScreen implements InputProcessor {

    private GameMain game;
    private MainMenuHuds menuHuds;
    private Texture tavsan,bg;
    private Rectangle bunnyShape;

    private float elapsedTime = 0;
    private Array<FallingCarrot> carrots;

    private ScreenAnimation screenAnimation;
    private boolean changeScreen;

    public MainMenuScreen(GameMain game){
        this.game = game;
        menuHuds = new MainMenuHuds(batch,viewport);
        addButtonListeners();
        bg = Assests.getInstance().assetManager.get(Assests.menuBG);

        carrots = new Array<FallingCarrot>();
        if(GameManager.getInstance().gameData.isErkek())
            tavsan = Assests.getInstance().assetManager.get(Assests.bunny);
        else
            tavsan = Assests.getInstance().assetManager.get(Assests.bunny2);

        bunnyShape = new Rectangle();
        bunnyShape.set(GameInfo.WIDTH / 2 - tavsan.getWidth() / 2,GameInfo.HEIGHT / 2 + 25,
                tavsan.getWidth(),tavsan.getHeight());

        screenAnimation = new ScreenAnimation(game);


        Gdx.input.setCatchBackKey(true);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(menuHuds.stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        Srt srt = new Srt();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(screenAnimation.pixDrawed) {
            showBackgroundAnim();
            batch.draw(tavsan, GameInfo.WIDTH / 2 - tavsan.getWidth() / 2, GameInfo.HEIGHT / 2 + 25);
        }
        batch.end();
        if(screenAnimation.pixDrawed) {
            menuHuds.stage.act();
            menuHuds.stage.draw();
            batch.setColor(Color.WHITE);
        }

        if(Gdx.input.justTouched() && bunnyShape.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),
                0)).x,camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)){
            if(GameManager.getInstance().gameData.isErkek()){
                tavsan = Assests.getInstance().assetManager.get(Assests.bunny2);
                GameManager.getInstance().gameData.setErkek(false);
                GameManager.getInstance().saveData();
            }else {
                tavsan = Assests.getInstance().assetManager.get(Assests.bunny);
                GameManager.getInstance().gameData.setErkek(true);
                GameManager.getInstance().saveData();
            }
        }

        if(!screenAnimation.opened) {
            screenAnimation.openScreen(batch);
            screenAnimation.pixDrawed = true;
        }

        if(changeScreen){
            screenAnimation.changeScreen(batch);
        }

    }

    private void addButtonListeners() {
        menuHuds.btnOyna.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.GameSelectScreen);
                changeScreen = true;
            }
        });

        menuHuds.btnTest.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.TestSelectScreen);
                changeScreen = true;
            }
        });

        menuHuds.btnAudio.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(GameManager.getInstance().gameData.isSoundOpen())
                    GameManager.getInstance().gameData.setSoundOpen(false);
                else
                    GameManager.getInstance().gameData.setSoundOpen(true);

                GameManager.getInstance().saveData();

                menuHuds.changeAudioButton();
            }
        });

        menuHuds.btnMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(GameManager.getInstance().gameData.isMusicOpen())
                    GameManager.getInstance().gameData.setMusicOpen(false);
                else
                    GameManager.getInstance().gameData.setMusicOpen(true);

                GameManager.getInstance().saveData();

                menuHuds.changeMusicButton();
            }
        });
    }

    private void showBackgroundAnim(){
        batch.draw(bg,0,0);
        elapsedTime +=Gdx.graphics.getDeltaTime();

        if(carrots.size < 20 && elapsedTime > 0.2f) {
            carrots.add(new FallingCarrot());
            elapsedTime = 0;
        }

        for(FallingCarrot fc:carrots){
            fc.draw(batch);
            fc.setPosition(fc.getX(),fc.getY() - fc.speed);
            fc.rotate(fc.speed / 10);

            if(fc.getY() < -100){
                fc.restart();
            }
        }


    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    class FallingCarrot extends Sprite{

        public float speed;

        private Vector2 currentSize;
        private final float changeSize = 25f;

        public FallingCarrot(){
            super(Assests.getInstance().assetManager.get(Assests.carrot,Texture.class));
            currentSize = new Vector2();
            currentSize.x = getWidth();
            currentSize.y = getHeight();

            speed = MathUtils.random(2,5);
            setPosition(MathUtils.random(0, GameInfo.WIDTH - 50), GameInfo.HEIGHT + 10);
            setBounds(MathUtils.random(0, GameInfo.WIDTH - 50), GameInfo.HEIGHT + 10,
                    getWidth() + 5 * (changeSize / (5 * speed)),getHeight() + 5 * (changeSize / (5 * speed)));
            setColor(1, 1, 1, 0.5f);
        }

        public void restart() {
            speed = MathUtils.random(2,5);
            setPosition(MathUtils.random(0, GameInfo.WIDTH - 50), GameInfo.HEIGHT + 10);
            setBounds(MathUtils.random(0, GameInfo.WIDTH - 50), GameInfo.HEIGHT + 10,
                    currentSize.x + 5 * (changeSize / (5 * speed)),currentSize.y + 5 * (changeSize / (5 * speed)));
        }
    }
}
