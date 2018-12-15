package com.serhatmerak.cozveoyna.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.AllScreens;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.ButtonHoverAnimation;
import com.serhatmerak.cozveoyna.helpers.CustomScreen;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.ScreenAnimation;

/**
 * Created by Serhat Merak on 4.03.2018.
 */

public class TestSelectScreen extends CustomScreen {

    private GameMain game;
    private Stage stage;
    private Table table;
    private Tree tree;
    private Texture bg;
    private ScreenAnimation screenAnimation;
    private boolean changeScreen = false;


    public TestSelectScreen(GameMain game){
       this.game = game;
       stage = new Stage(viewport,batch);

       bg = Assests.getInstance().assetManager.get(Assests.testSelectBG);

       createLabelAndButtons();
       createTreeButtonsAndListeners();

       Gdx.input.setInputProcessor(stage);
       Gdx.input.setCatchBackKey(true);

       screenAnimation = new ScreenAnimation(game);

    }

    private void createTreeButtonsAndListeners() {
        TextureAtlas textureAtlas = Assests.getInstance().assetManager.get(Assests.treeUi);


        Tree.TreeStyle treeStyle = new Tree.TreeStyle();
        treeStyle.plus = new SpriteDrawable(new Sprite(textureAtlas.getRegions().get(3)));
        treeStyle.minus = new SpriteDrawable(new Sprite(textureAtlas.getRegions().get(2)));
        tree = new Tree(treeStyle);
        table.add(tree).fill().padLeft(50).padTop(200).expand();


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = Assests.getInstance().assetManager.get(Assests.fntArial);
        textButtonStyle.up = new SpriteDrawable(new Sprite(textureAtlas.getRegions().get(0)));
        textButtonStyle.over = new SpriteDrawable(new Sprite(textureAtlas.getRegions().get(1)));


        final Tree.Node Matematik = new Tree.Node(new TextButton("Matematik",textButtonStyle ));
        final Tree.Node Turkce = new Tree.Node(new TextButton("Türkçe", textButtonStyle));
        final Tree.Node Fen = new Tree.Node(new TextButton("Fen", textButtonStyle));
        final Tree.Node Sosyal = new Tree.Node(new TextButton("Sosyal", textButtonStyle));
        final Tree.Node Mat_Test1 = new Tree.Node(new TextButton("Test1", textButtonStyle));
        final Tree.Node Mat_Test2 = new Tree.Node(new TextButton("Test2", textButtonStyle));
        tree.add(Matematik);
        tree.add(Turkce);
        tree.add(Fen);
        tree.add(Sosyal);
        Matematik.add(Mat_Test1);
        Matematik.add(Mat_Test2);





        Matematik.getActor().addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                if(Matematik.isExpanded())
                    Matematik.setExpanded(false);
                else
                    Matematik.setExpanded(true);
            }
        });

        Mat_Test1.getActor().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TestScreen(game,Assests.getInstance().test1));
            }
        });

        Mat_Test2.getActor().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TestScreen(game,Assests.getInstance().test2));
            }
        });

        Turkce.getActor().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(Assests.getInstance().test1.size + " " + Assests.getInstance().test2.size);
            }
        });


    }



    private void createLabelAndButtons() {

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assests.getInstance().assetManager.get(Assests.fntCooper);
        labelStyle.fontColor = Color.BLACK;

        Label label = new Label("Bir Test Seç",labelStyle);
        label.setAlignment(Align.center);
        label.setPosition(GameInfo.WIDTH / 2,GameInfo.HEIGHT - 100,Align.center);

        stage.addActor(label);

        ImageButton btnHome = new ImageButton(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.home,Texture.class))));
        btnHome.setPosition(GameInfo.WIDTH - 96,16);
        btnHome.setOrigin(Align.center);
        btnHome.setTransform(true);
        stage.addActor(btnHome);

        btnHome.addListener(new ButtonHoverAnimation(btnHome));
        btnHome.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.MainMenu);
                changeScreen = true;
            }
        });

    }




    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg,0,0);
        batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            screenAnimation.setNextScreen(AllScreens.MainMenu);
            changeScreen = true;
        }



        stage.act();
        stage.draw();

        if(changeScreen)
            screenAnimation.changeScreen(batch);
    }
}
