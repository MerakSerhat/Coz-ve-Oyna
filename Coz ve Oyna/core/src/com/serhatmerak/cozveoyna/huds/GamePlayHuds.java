package com.serhatmerak.cozveoyna.huds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.GameInfo;

/**
 * Created by Serhat Merak on 26.02.2018.
 */

public class GamePlayHuds {
    public Stage stage;
    private Image darkBG,gameOverPanel;
    public Image pix;
    public ImageButton btnPause,btnResume,btnHome,btnBack,btnRestart;
    private Label pausedLabel,skor,highSkor;
    public Dialog dlgExit,dlgRestart,dlgYetersiz;

    public GamePlayHuds(SpriteBatch batch, Viewport viewport){
        stage = new Stage(viewport,batch);

        createDialogs();
        createPauseActors();

    }

    private void createDialogs() {
        pix = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.pixBlack,Texture.class))));
        pix.setBounds(0,0,GameInfo.WIDTH,GameInfo.HEIGHT);
        pix.setColor(0,0,0,0);
        dlgExit = createDialog("Oyundan çıkmak istediğine\n emin misin ?");
        dlgYetersiz = createDialog("Oyun hakkınız kalmadı! \n\n 1 Oyun hakkı için test \n çözmek ister misin ?");
        dlgRestart = createDialog("1 Oyun hakkı daha harcamak \nistiyor musunuz ?");

    }

    private void createPauseActors() {
        darkBG = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.pixBlack,Texture.class))));
        darkBG.setBounds(0,0,480,800);
        darkBG.setColor(1,1,1,0.8f);

        final float size = 64;

        btnPause = new ImageButton(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.pause,Texture.class))));
        btnPause.setBounds(GameInfo.WIDTH - 100 , 700,size,size);

        ImageButton.ImageButtonStyle resumeStyle = new ImageButton.ImageButtonStyle();
        resumeStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.resumeNormal,Texture.class)));
        resumeStyle.over = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.resumeHover,Texture.class)));
        btnResume = new ImageButton(resumeStyle);
        btnResume.setBounds(GameInfo.WIDTH / 2 + 64 , 100,size * 2,size * 2);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.homeNormal,Texture.class)));
        homeStyle.over = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.homeHover,Texture.class)));
        btnHome = new ImageButton(homeStyle);
        btnHome.setBounds(GameInfo.WIDTH  / 2 - 64 - size * 2 , 100,size * 2,size * 2);

        stage.addActor(btnPause);

        Label.LabelStyle pauseLabelStyle = new Label.LabelStyle();
        pauseLabelStyle.font = Assests.getInstance().assetManager.get(Assests.fntCooper);
        pauseLabelStyle.fontColor = Color.valueOf("#9be116");

        pausedLabel = new Label("OYUN\nDURDURULDU",pauseLabelStyle);
        pausedLabel.setWidth(400);
        pausedLabel.setWrap(true);
        pausedLabel.setAlignment(Align.center);
        pausedLabel.setPosition(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2 + 100,Align.center);

        gameOverPanel = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.gameOverPanel,Texture.class))));
        gameOverPanel.setPosition(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2 + 30,Align.center);

        ImageButton.ImageButtonStyle backStyle = new ImageButton.ImageButtonStyle();
        backStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.backNormal,Texture.class)));
        backStyle.over = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.backHover,Texture.class)));
        btnBack = new ImageButton(backStyle);
        btnBack.setBounds(60, 30,size * 2,size * 2);

        ImageButton.ImageButtonStyle restartStyle = new ImageButton.ImageButtonStyle();
        restartStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.restartNormal,Texture.class)));
        restartStyle.over = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.restartHover,Texture.class)));
        btnRestart = new ImageButton(restartStyle);
        btnRestart.setBounds(GameInfo.WIDTH - 60 - size * 2, 30,size * 2,size * 2);

        Label.LabelStyle gameOverLabelStyle = new Label.LabelStyle();
        gameOverLabelStyle.font = Assests.getInstance().assetManager.get(Assests.fntCooper);

        skor = new Label("",gameOverLabelStyle);
        skor.setAlignment(Align.center);
        skor.setPosition(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2 + 90,Align.center);

        highSkor = new Label("",gameOverLabelStyle);
        highSkor.setAlignment(Align.center);
        highSkor.setPosition(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2 - 75,Align.center);

    }

    public void pause(){
        btnPause.remove();
        stage.addActor(darkBG);
        stage.addActor(btnHome);
        stage.addActor(btnResume);
        stage.addActor(pausedLabel);
    }

    public void resume(){
        darkBG.remove();
        btnHome.remove();
        btnResume.remove();
        pausedLabel.remove();
        stage.addActor(btnPause);
    }

    public void gameOver(int puan,int highskor){
        btnPause.remove();
        gameOverPanel.setPosition(gameOverPanel.getX(),gameOverPanel.getY() + 480);
        gameOverPanel.addAction(Actions.repeat(60,Actions.moveBy(0,-8)));
        skor.setPosition(skor.getX(),skor.getY() + 480);
        skor.addAction(Actions.repeat(60,Actions.moveBy(0,-8)));
        highSkor.setPosition(highSkor.getX(),highSkor.getY() + 480);
        highSkor.addAction(Actions.repeat(60,Actions.moveBy(0,-8)));
        btnBack.setPosition(btnBack.getX() - 480,btnBack.getY());
        btnBack.addAction(Actions.repeat(60,Actions.moveBy(8,0)));
        btnRestart.setPosition(btnRestart.getX() + 480,btnBack.getY());
        btnRestart.addAction(Actions.repeat(60,Actions.moveBy(-8,0)));
        stage.addActor(gameOverPanel);
        stage.addActor(btnBack);
        stage.addActor(btnRestart);
        skor.setText(puan + "");
        highSkor.setText(highskor + "");
        stage.addActor(skor);
        stage.addActor(highSkor);
    }

    public void restart() {
        stage.clear();
        stage.addActor(btnPause);
    }


    public void showDialog(Dialog dialog){

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
