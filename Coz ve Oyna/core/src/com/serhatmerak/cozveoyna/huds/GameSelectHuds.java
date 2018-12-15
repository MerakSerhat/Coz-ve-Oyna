package com.serhatmerak.cozveoyna.huds;

/**
 * Created by Serhat Merak on 25.02.2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.ButtonHoverAnimation;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;
import com.serhatmerak.cozveoyna.hophop.HopHopScreen;
import com.serhatmerak.cozveoyna.jumper.JumperScreen;

/**
 * Created by Serhat Merak on 23.02.2018.
 */

public class GameSelectHuds {
    public Stage stage;
    public ImageButton btnAudio,btnMusic,btnHome;
    public Image pix;
    private Label lblGameSelect,lblHopHop,lblJumper;
    private Array<Image> haklar;
    private ImageButton.ImageButtonStyle audioStyle,musicStyle;
    public ImageButton prJumper,prHopHop;
    public Dialog dlgYetersizHak;
    public boolean changeScreen = false;
    public boolean dialogOpen = false;

    public GameSelectHuds(SpriteBatch batch, Viewport viewport){
        stage = new Stage(viewport,batch);
        haklar = new Array<Image>();

        createImages();
        createButtons();
        createLabels();
        createDialogs();

        Gdx.input.setInputProcessor(stage);
    }

    private void createDialogs() {
        pix = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.pixBlack,Texture.class))));
        pix.setColor(0,0,0,0);
        pix.setBounds(0,0,GameInfo.WIDTH,GameInfo.HEIGHT);

        dlgYetersizHak = createDialog("Oyun hakkınız kalmadı! \n\n 1 Oyun hakkı için test \n çözmek ister misin ?");
    }

    private void createLabels() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assests.getInstance().assetManager.get(Assests.fntCooper);
        labelStyle.fontColor = Color.valueOf("#184051");

        lblGameSelect = new Label("Oyun Seç",labelStyle);
        lblGameSelect.setPosition(GameInfo.WIDTH / 2 , GameInfo.HEIGHT - 180,Align.center);

        stage.addActor(lblGameSelect);


        Label.LabelStyle lblGameNameStyle = new Label.LabelStyle();
        lblGameNameStyle.font = Assests.getInstance().assetManager.get(Assests.fntBold);
        lblGameNameStyle.fontColor = Color.valueOf("#184051");

        lblHopHop = new Label("Hop Hop\nTavşan",lblGameNameStyle);
        lblHopHop.setWrap(true);
        lblHopHop.setWidth(200);
        lblHopHop.setAlignment(Align.center);
        lblHopHop.setPosition(260,205);
        stage.addActor(lblHopHop);

        lblJumper = new Label("Zıp Zıp\nTavşan",lblGameNameStyle);
        lblJumper.setWrap(true);
        lblJumper.setWidth(200);
        lblJumper.setAlignment(Align.center);
        lblJumper.setPosition(20,205);
        stage.addActor(lblJumper);

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
            haklar.add(lifes);
            lifes.addAction(parallelAction);
        }

        ImageButton.ImageButtonStyle hopSty = new ImageButton.ImageButtonStyle();
        hopSty.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.hophopPreview,Texture.class)));
        prHopHop = new ImageButton(hopSty);
        ImageButton.ImageButtonStyle zipSty = new ImageButton.ImageButtonStyle();
        zipSty.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.jumperPreview,Texture.class)));
        prJumper = new ImageButton(zipSty);

        prJumper.setPosition(20,300);
        prHopHop.setPosition(260,300);

        stage.addActor(prHopHop);
        stage.addActor(prJumper);
    }

    private void createButtons() {

        audioStyle = new ImageButton.ImageButtonStyle();
        changeAudioButton();
        btnAudio = new ImageButton(audioStyle);

        musicStyle = new ImageButton.ImageButtonStyle();
        changeMusicButton();
        btnMusic = new ImageButton(musicStyle);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.home,Texture.class)));
        btnHome = new ImageButton(homeStyle);


        btnAudio.setPosition(GameInfo.WIDTH - 16,16,Align.bottomRight);
        btnMusic.setPosition(GameInfo.WIDTH - 96,16,Align.bottomRight);
        btnHome.setPosition(48,16,Align.bottomLeft);

        stage.addActor(btnAudio);
        stage.addActor(btnMusic);
        stage.addActor(btnHome);

        btnHome.setTransform(true);
        btnAudio.setTransform(true);
        btnMusic.setTransform(true);

        btnHome.setOrigin(Align.center);
        btnAudio.setOrigin(Align.center);
        btnMusic.setOrigin(Align.center);

        btnHome.addListener(new ButtonHoverAnimation(btnHome));
        btnAudio.addListener(new ButtonHoverAnimation(btnAudio));
        btnMusic.addListener(new ButtonHoverAnimation(btnMusic));



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

    public void changeScreen(){
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.repeat(80,Actions.moveBy(0,2)));
        RunnableAction runnableAction = new RunnableAction();
        runnableAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                changeScreen = true;
            }
        });
        sequenceAction.addAction(runnableAction);
        haklar.get(0).addAction(sequenceAction);

    }

    public void showDialog(Dialog dialog){

        dialogOpen = true;
        stage.addActor(pix);
        pix.addAction(Actions.fadeIn(0.4f));
        dialog.show(stage);
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
