package com.serhatmerak.cozveoyna.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.AllScreens;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.CustomScreen;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;
import com.serhatmerak.cozveoyna.helpers.ScreenAnimation;
import com.serhatmerak.cozveoyna.hophop.HopHopScreen;
import com.serhatmerak.cozveoyna.huds.GameSelectHuds;
import com.serhatmerak.cozveoyna.jumper.JumperScreen;

import javax.xml.soap.Text;

/**
 * Created by Serhat Merak on 20.02.2018.
 */

public class GameSelectScreen extends CustomScreen {
    private GameMain game;
    private BitmapFont font;
    private Texture bg;
    private GameSelectHuds gameSelectHuds;
    private ScreenAnimation screenAnimation;
    private boolean clicked = false;

    private Rectangle hophopRect,zipzipRect;


    public GameSelectScreen(GameMain game){
        this.game = game;
        font = Assests.getInstance().assetManager.get(Assests.fntBlow);
        bg = Assests.getInstance().assetManager.get(Assests.gameSelectBG);
        gameSelectHuds = new GameSelectHuds(batch,viewport);

        hophopRect = new Rectangle();
        hophopRect.set(260,300,200,300);

        zipzipRect = new Rectangle();
        zipzipRect.set(20,300,200,300);

        setButtonListeners();

        screenAnimation = new ScreenAnimation(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(screenAnimation.pixDrawed)
            batch.draw(bg,0,0);
        batch.end();

        if(screenAnimation.pixDrawed) {
            gameSelectHuds.stage.act();
            gameSelectHuds.stage.draw();
            batch.setColor(Color.WHITE);
        }

//        if(Gdx.input.justTouched() && !gameSelectHuds.dialogOpen){
//            if(zipzipRect.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x,
//                    camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)) {
//
//                if(!clicked) {
//                    if (checkHak()) {
//                        gameSelectHuds.changeScreen();
//                        screenAnimation.setNextScreen(AllScreens.JumperScreen);
//                        clicked = true;
//                    } else
//                        gameSelectHuds.showDialog(gameSelectHuds.dlgYetersizHak);
//                }
//            } else if(hophopRect.contains(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x,
//                    camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y)){
//                if(!clicked) {
//                    if (checkHak()) {
//                        gameSelectHuds.changeScreen();
//                        screenAnimation.setNextScreen(AllScreens.HopHopScreen);
//                        clicked = true;
//                    } else
//                        gameSelectHuds.showDialog(gameSelectHuds.dlgYetersizHak);
//                }
//
//            }

//      }

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            screenAnimation.setNextScreen(AllScreens.MainMenu);
            gameSelectHuds.changeScreen = true;

        }

        if(!screenAnimation.opened) {
            screenAnimation.openScreen(batch);
            screenAnimation.pixDrawed = true;
        }

        if(gameSelectHuds.changeScreen)
            screenAnimation.changeScreen(batch);
    }

    private boolean checkHak() {
        if(GameManager.getInstance().gameData.getHak() > 0 ){
            GameManager.getInstance().gameData.setHak(GameManager.getInstance().gameData.getHak() - 1);
            GameManager.getInstance().saveData();
            return true;
        }
        return false;

    }

    private void setButtonListeners() {
        gameSelectHuds.btnHome.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.MainMenu);
                gameSelectHuds.changeScreen = true;
            }
        });

        gameSelectHuds.btnAudio.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(GameManager.getInstance().gameData.isSoundOpen())
                    GameManager.getInstance().gameData.setSoundOpen(false);
                else
                    GameManager.getInstance().gameData.setSoundOpen(true);

                GameManager.getInstance().saveData();

                gameSelectHuds.changeAudioButton();
            }
        });

        gameSelectHuds.btnMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(GameManager.getInstance().gameData.isMusicOpen())
                    GameManager.getInstance().gameData.setMusicOpen(false);
                else
                    GameManager.getInstance().gameData.setMusicOpen(true);

                GameManager.getInstance().saveData();

                gameSelectHuds.changeMusicButton();
            }
        });

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                gameSelectHuds.dialogOpen = false;
            }
        };

    //yes
        gameSelectHuds.dlgYetersizHak.getButtonTable().getChildren().get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.TestSelectScreen);
                gameSelectHuds.changeScreen = true;
                gameSelectHuds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.run(runnable),Actions.removeActor()));
            }
        });
    //no
        gameSelectHuds.dlgYetersizHak.getButtonTable().getChildren().get(1).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSelectHuds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.run(runnable),Actions.removeActor()));
            }
        });

        gameSelectHuds.prHopHop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!clicked) {
                    if (checkHak()) {
                        gameSelectHuds.changeScreen();
                        screenAnimation.setNextScreen(AllScreens.HopHopScreen);
                        clicked = true;
                    } else
                        gameSelectHuds.showDialog(gameSelectHuds.dlgYetersizHak);
                }

            }
        });


        gameSelectHuds.prJumper.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if(!clicked) {
                    if (checkHak()) {
                        gameSelectHuds.changeScreen();
                        screenAnimation.setNextScreen(AllScreens.JumperScreen);
                        clicked = true;
                    } else
                        gameSelectHuds.showDialog(gameSelectHuds.dlgYetersizHak);
                }
            }
        });




    }
}
