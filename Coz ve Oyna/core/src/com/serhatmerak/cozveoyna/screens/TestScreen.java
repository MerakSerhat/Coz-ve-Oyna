package com.serhatmerak.cozveoyna.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.AllScreens;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.CustomScreen;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;
import com.serhatmerak.cozveoyna.helpers.ScreenAnimation;
import com.serhatmerak.cozveoyna.huds.TestHuds;
import com.serhatmerak.cozveoyna.sorular.Soru;

import javax.xml.soap.Text;

/**
 * Created by Serhat Merak on 27.02.2018.
 */

public class TestScreen extends CustomScreen {
    private GameMain game;
    TestHuds huds;
    private Array<Soru> test;
    private int questionIndex = 0;
    private ScreenAnimation screenAnimation;
    private boolean optionSelected = false;
    private boolean changeScreen = false;
    private float elapsedTime = 0;
    private Array<FallingObjects> fallingObjects;
    private TextureAtlas emojies;
    private TextureAtlas.AtlasRegion emoji;



    public TestScreen(GameMain game,Array<Soru> test){
        this.game = game;

        viewport = new FitViewport(450,GameInfo.HEIGHT,camera);
        resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        huds = new TestHuds(batch,viewport);
        this.test = test;

        huds.setTexts(test.get(questionIndex).getOptions(),test.get(questionIndex).getSoru(),questionIndex + 1,test.size);

        emojies = Assests.getInstance().assetManager.get(Assests.emojies,TextureAtlas.class);
        fallingObjects = new Array<FallingObjects>();
        setOptionsListener();
        screenAnimation = new ScreenAnimation(game);
    }

    private void setOptionsListener() {
        huds.optionA.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!huds.buttonClicked) {
                    huds.buttonClicked = true;
                    optionSelected(0, huds.optionA);
                }

            }
        });

        huds.optionB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!huds.buttonClicked) {
                    huds.buttonClicked = true;
                    optionSelected(1, huds.optionB);
                }
            }
        });

        huds.optionC.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!huds.buttonClicked) {
                    huds.buttonClicked = true;
                    optionSelected(2, huds.optionC);
                }
            }
        });

        huds.optionD.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!huds.buttonClicked) {
                    huds.buttonClicked = true;
                    optionSelected(3, huds.optionD);
                }
            }
        });

        huds.btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                huds.showDialog(huds.dlgExit);
            }
        });

        huds.btnHome.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen = true;
                screenAnimation.setNextScreen(AllScreens.MainMenu);
            }
        });

        //yes
        huds.dlgExit.getButtonTable().getChildren().get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.MainMenu);
                changeScreen = true;
                huds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.removeActor()));
            }
        });
        //no
        huds.dlgExit.getButtonTable().getChildren().get(1).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                huds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.removeActor()));
            }
        });
    }

    private void optionSelected(int optionIndex,TextButton actor){
        //Süreyi durdur;
        if(!optionSelected) {
            actor.setStyle(huds.styleSelect);

            huds.showAction(actor,optionIndex,test.get(questionIndex).getDogru(),this);
            optionSelected = true;
        }


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.4f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(questionIndex == test.size)
            showBackgroundAnim();


        huds.stage.act();
        huds.stage.draw();




        if(Gdx.input.isKeyPressed(Input.Keys.BACK))
            game.setScreen(new MainMenuScreen(game));

        if(changeScreen)
            screenAnimation.changeScreen(batch);

    }

    public void nextQuestion() {
        questionIndex++;

        //TODO:Delete this



            huds.optionA.setStyle(huds.styleNormal);
            huds.optionB.setStyle(huds.styleNormal);
            huds.optionC.setStyle(huds.styleNormal);
            huds.optionD.setStyle(huds.styleNormal);

            if(questionIndex != test.size)
                huds.setTexts(test.get(questionIndex).getOptions(), test.get(questionIndex).getSoru(), questionIndex + 1, test.size);
            else {

                if(huds.skor > test.size - 2)
                    emoji = emojies.getRegions().get(0);
                else if(huds.skor >= test.size / 2 )
                    emoji = emojies.getRegions().get(1);
                else
                    emoji = emojies.getRegions().get(2);

                huds.finishTest(test.size);
            }


            optionSelected = false;


        //süreyi sıfırla
    }

    private void showBackgroundAnim(){
        elapsedTime +=Gdx.graphics.getDeltaTime();

        if(fallingObjects.size < 20 && elapsedTime > 0.2f) {
            fallingObjects.add(new FallingObjects(emoji));
            elapsedTime = 0;
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(FallingObjects fallingObject:fallingObjects){
            fallingObject.draw(batch);
            fallingObject.setPosition(fallingObject.getX(),fallingObject.getY() - fallingObject.speed);
            fallingObject.rotate(fallingObject.speed / 10);

            if(fallingObject.getY() < -100){
                fallingObject.restart();
            }
        }
        batch.end();


    }

    class FallingObjects extends Sprite {

        public float speed;

        private Vector2 currentSize;
        private final float changeSize = 25f;

        public FallingObjects(TextureAtlas.AtlasRegion texture){
            super(texture);
            currentSize = new Vector2();
            currentSize.x = getWidth();
            currentSize.y = getHeight();

            speed = MathUtils.random(2,5);
            setPosition(MathUtils.random(0, 450 - 50), GameInfo.HEIGHT + 50);
            setBounds(MathUtils.random(0, 450 - 50), GameInfo.HEIGHT + 50,
                    getWidth() + 5 * (changeSize / (5 * speed)),getHeight() + 5 * (changeSize / (5 * speed)));
            setColor(1, 1, 1, 0.2f);
        }

        public void restart() {
            speed = MathUtils.random(2,5);
            setPosition(MathUtils.random(0, 450 - 50), GameInfo.HEIGHT + 50);
            setBounds(MathUtils.random(0, 450 - 50), GameInfo.HEIGHT + 50,
                    currentSize.x + 5 * (changeSize / (5 * speed)),currentSize.y + 5 * (changeSize / (5 * speed)));
        }
    }
}
