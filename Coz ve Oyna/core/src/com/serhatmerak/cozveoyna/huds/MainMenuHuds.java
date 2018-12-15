package com.serhatmerak.cozveoyna.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.ButtonHoverAnimation;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;

/**
 * Created by Serhat Merak on 23.02.2018.
 */

public class MainMenuHuds {
    public Stage stage;
    public TextButton btnOyna,btnTest;
    public TextButton.TextButtonStyle styleNormal,styleLocked;

    public ImageButton btnAudio,btnMusic/*,btnHelp,btnLeadboard*/;

    ImageButton.ImageButtonStyle musicStyle,audioStyle;

    private final int btnWidht = 200,btnHeight = 100;

    public Dialog dialog1;

    public MainMenuHuds(SpriteBatch batch, Viewport viewport){
        stage = new Stage(viewport,batch);

        createImages();
        createButtons();
        createDialogs();

        Gdx.input.setInputProcessor(stage);
    }

    private void createDialogs() {
        dialog1 = createDialog("1 hakkınızı kullanarak\n oyuna girmek istiyor\n musunuz ?");
    }

    private void createImages() {



        //TODO:Veritabanından çek
        int lifeCount = GameManager.getInstance().gameData.getHak();

        Texture txtLifes = Assests.getInstance().assetManager.get(Assests.lifes);
        float width = lifeCount * txtLifes.getWidth() + (lifeCount - 1) * 20;

        for(int i = 0; i< lifeCount;i++){
            //Sallanma animasyonu
            SequenceAction sequenceAction = new SequenceAction();
            sequenceAction.addAction(Actions.repeat(30,Actions.rotateBy(0.5f)));
            sequenceAction.addAction(Actions.repeat(60,Actions.rotateBy(-0.5f)));
            sequenceAction.addAction(Actions.repeat(30,Actions.rotateBy(0.5f)));

            ParallelAction parallelAction = new ParallelAction();
            parallelAction.addAction(Actions.forever(sequenceAction));

            Image lifes = new Image(new SpriteDrawable(new Sprite(txtLifes)));
            lifes.setPosition((GameInfo.WIDTH / 2 - width / 2) + i * (txtLifes.getWidth() + 20),GameInfo.HEIGHT - 100);
            lifes.setOriginX(lifes.getWidth() / 2);
            stage.addActor(lifes);
            lifes.addAction(parallelAction);
        }
    }

    private void createButtons() {
        styleNormal = new TextButton.TextButtonStyle();
        styleNormal.font = Assests.getInstance().assetManager.get(Assests.fntBold);
        styleNormal.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.btnNormal, Texture.class)));
        styleNormal.over = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.btnHover,Texture.class))) ;

        styleLocked = new TextButton.TextButtonStyle();
        styleLocked.font = Assests.getInstance().assetManager.get(Assests.fntBold);
        styleLocked.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.btnLocked,Texture.class)));

        btnOyna = new TextButton("Oyna",styleNormal);
        btnTest = new TextButton("Test",styleNormal);

        btnOyna.setBounds(GameInfo.WIDTH / 2 - btnWidht / 2,GameInfo.HEIGHT / 2 - 130,btnWidht,btnHeight);
        btnTest.setBounds(GameInfo.WIDTH / 2 - btnWidht / 2,GameInfo.HEIGHT / 2 - 250,btnWidht,btnHeight);

        stage.addActor(btnOyna);
        stage.addActor(btnTest);

        audioStyle = new ImageButton.ImageButtonStyle();
        changeAudioButton();
        btnAudio = new ImageButton(audioStyle);

        musicStyle = new ImageButton.ImageButtonStyle();
        changeMusicButton();
        btnMusic = new ImageButton(musicStyle);

//        ImageButton.ImageButtonStyle helpStyle = new ImageButton.ImageButtonStyle();
//        helpStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.help,Texture.class)));
//        btnHelp = new ImageButton(helpStyle);
//
//        ImageButton.ImageButtonStyle leadboardStyle = new ImageButton.ImageButtonStyle();
//        leadboardStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.leadboard,Texture.class)));
//        btnLeadboard = new ImageButton(leadboardStyle);

        btnAudio.setPosition(GameInfo.WIDTH - 64,16,Align.bottomRight);
        btnMusic.setPosition(64,16,Align.bottomLeft);
//        btnHelp.setPosition(16,16,Align.bottomLeft);
//        btnLeadboard.setPosition(96,16,Align.bottomLeft);

        stage.addActor(btnAudio);
        stage.addActor(btnMusic);
//        stage.addActor(btnHelp);
//        stage.addActor(btnLeadboard);

//        btnHelp.setTransform(true);
//        btnLeadboard.setTransform(true);
        btnMusic.setTransform(true);
        btnAudio.setTransform(true);

//        btnHelp.setOrigin(Align.center);
//        btnLeadboard.setOrigin(Align.center);
        btnMusic.setOrigin(Align.center);
        btnAudio.setOrigin(Align.center);

//        btnHelp.addListener(new ButtonHoverAnimation(btnHelp));
//        btnLeadboard.addListener(new ButtonHoverAnimation(btnLeadboard));
        btnMusic.addListener(new ButtonHoverAnimation(btnMusic));
        btnAudio.addListener(new ButtonHoverAnimation(btnAudio));

    }

    public void changeAudioButton(){
        if(GameManager.getInstance().gameData.isSoundOpen())
            audioStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.audioOn,Texture.class)));
        else
            audioStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.audioOff,Texture.class)));
    }

    public void changeMusicButton(){
        if(GameManager.getInstance().gameData.isMusicOpen())
            musicStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.musicOn,Texture.class)));
        else
            musicStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.musicOff,Texture.class)));
    }



    private Dialog createDialog(String msg){
        Window.WindowStyle ws = new Window.WindowStyle();
        ws.titleFont = Assests.getInstance().assetManager.get(Assests.fntBold);

        Dialog dialog = new Dialog("",ws);
        dialog.setModal(true);
        dialog.setBackground(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.alertBG, Texture.class))));
        dialog.getContentTable().row().colspan(1).center();
        Label.LabelStyle ls = new Label.LabelStyle();
        ls.font = Assests.getInstance().assetManager.get(Assests.fntTest);
        ls.fontColor = Color.valueOf("#12dedd");


        Label label = new Label(msg,ls);
        label.setAlignment(Align.center);

        dialog.getContentTable().add(label).pad(50);


        dialog.button(new ImageButton(new SpriteDrawable(new Sprite(
                Assests.getInstance().assetManager.get(Assests.yes,Texture.class))))).padBottom(-20).left();
        dialog.button(new ImageButton(new SpriteDrawable(new Sprite(
                Assests.getInstance().assetManager.get(Assests.no,Texture.class)))));

        dialog.pack();


        return dialog;
    }

}
