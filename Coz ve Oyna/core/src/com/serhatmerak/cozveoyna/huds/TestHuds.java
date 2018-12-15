package com.serhatmerak.cozveoyna.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.ButtonHoverAnimation;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;
import com.serhatmerak.cozveoyna.screens.TestScreen;

/**
 * Created by Serhat Merak on 28.02.2018.
 */

public class TestHuds {
    public Stage stage;
    public TextButton optionA,optionB,optionC,optionD;
    public TextButton.TextButtonStyle styleNormal,styleSelect,styleTrue,styleFalse;
    public ImageButton btnBack,btnHome;
    public Label lblSoru,lblSrouId;
    private Image sonuc,lifes;
    private Label lblTrue,lblFalse,testFinished,lblYetersiz;
    public int skor = 0;
    public boolean buttonClicked = false;
    public Dialog dlgExit;
    public Image pix;



    public TestHuds(SpriteBatch batch, Viewport viewport){
        stage = new Stage(viewport,batch);

        createTextHuds();
        createImageHuds();
        createDialogs();


        Gdx.input.setInputProcessor(stage);
    }

    private void createDialogs() {
        pix = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.pixBlack,Texture.class))));
        pix.setColor(0,0,0,0);
        pix.setBounds(0,0,450,GameInfo.HEIGHT);

        dlgExit = createDialog("Testten çıkmak istediğine \n emin misin ? \n\n Eğer çıkarsan bu seferlik \n oyun hakkı almayacaksın.");
    }

    public void showAction(final TextButton button, final int optionIndex, final int trueIndex, final TestScreen testScreen) {
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.delay(1f));
        RunnableAction runnableAction = new RunnableAction();
        runnableAction.setRunnable(new Runnable() {
            @Override
            public void run() {

                if(optionIndex != trueIndex){
                    if(GameManager.getInstance().gameData.isSoundOpen())
                        Assests.getInstance().assetManager.get(Assests.sndWrong, Sound.class).play();

                    button.setStyle(styleFalse);

                    switch (trueIndex){
                        case 0:
                            optionA.setStyle(styleTrue);
                            break;
                        case 1:
                            optionB.setStyle(styleTrue);
                            break;
                        case 2:
                            optionC.setStyle(styleTrue);
                            break;
                        case 3:
                            optionD.setStyle(styleTrue);
                            break;
                    }
                }else {
                    if(GameManager.getInstance().gameData.isSoundOpen())
                        Assests.getInstance().assetManager.get(Assests.sndCorrect, Sound.class).play();
                    button.setStyle(styleTrue);
                    skor ++;
                }
            }
        });

        sequenceAction.addAction(runnableAction);
        sequenceAction.addAction(Actions.delay(1f));
        RunnableAction runnableAction2 = new RunnableAction();
        runnableAction2.setRunnable(new Runnable() {
            @Override
            public void run() {
                testScreen.nextQuestion();
            }
        });
        sequenceAction.addAction(runnableAction2);
        button.addAction(sequenceAction);
    }

    private void createImageHuds() {
        sonuc = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.testSon,Texture.class))));
        sonuc.setPosition(450 / 2 , GameInfo.HEIGHT / 2 - 830,Align.center);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Assests.getInstance().assetManager.get(Assests.fntCooper);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = Assests.getInstance().assetManager.get(Assests.fntCooper2);

        lblTrue = new Label("",labelStyle);
        lblFalse = new Label("",labelStyle);
        testFinished = new Label("Test \nTamamlandı",labelStyle2);
        testFinished.setAlignment(Align.center);


        lblTrue.setPosition(450 / 2 + 25,480 / 2 - 620);
        lblFalse.setPosition(450 / 2 + 25,480 / 2 - 720);
        testFinished.setPosition(450 / 2,-885,Align.center);

        //Sallanma animasyonu
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.repeat(30,Actions.rotateBy(0.5f)));
        sequenceAction.addAction(Actions.repeat(60,Actions.rotateBy(-0.5f)));
        sequenceAction.addAction(Actions.repeat(30,Actions.rotateBy(0.5f)));

        ParallelAction parallelAction = new ParallelAction();
        parallelAction.addAction(Actions.forever(sequenceAction));

        Texture txtLifes = Assests.getInstance().assetManager.get(Assests.lifes);
        lifes = new Image(new SpriteDrawable(new Sprite(txtLifes)));
        lifes.setPosition(450 / 2,-210,Align.center);
        lifes.setOriginX(lifes.getWidth() / 2);
        lifes.addAction(parallelAction);

        ImageButton.ImageButtonStyle is = new ImageButton.ImageButtonStyle();
        is.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.backNormal,Texture.class)));
        is.over = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.backHover,Texture.class)));

        btnHome = new ImageButton(is);
        btnHome.setBounds(450 / 2 - 50,32 - 800,100,100);

        Label.LabelStyle yetersizStyle = new Label.LabelStyle();
        yetersizStyle.font = Assests.getInstance().assetManager.get(Assests.fntArial);
        yetersizStyle.fontColor = Color.RED;

        lblYetersiz = new Label("Yeterince Soruya Doğru \nCevap Veremedin",yetersizStyle);
        lblYetersiz.setPosition(450 / 2,-210,Align.center);
        lblYetersiz.setAlignment(Align.center);

    }

    private void createTextHuds() {
        styleNormal = new TextButton.TextButtonStyle();
        styleSelect = new TextButton.TextButtonStyle();
        styleFalse = new TextButton.TextButtonStyle();
        styleTrue = new TextButton.TextButtonStyle();

        styleNormal.font = Assests.getInstance().assetManager.get(Assests.fntTest);
        styleSelect.font = Assests.getInstance().assetManager.get(Assests.fntTest);
        styleTrue.font = Assests.getInstance().assetManager.get(Assests.fntTest);
        styleFalse.font = Assests.getInstance().assetManager.get(Assests.fntTest);

        styleNormal.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.btnPurple,Texture.class)));
        styleSelect.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.btnBlue,Texture.class)));
        styleTrue.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.btnGreen,Texture.class)));
        styleFalse.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.btnRed,Texture.class)));

        optionA = new TextButton("",styleNormal);
        optionB = new TextButton("",styleNormal);
        optionC = new TextButton("",styleNormal);
        optionD = new TextButton("",styleNormal);

        optionA.setPosition(450 / 2,GameInfo.HEIGHT / 2 - 40,Align.center);
        optionB.setPosition(450 / 2,GameInfo.HEIGHT / 2 - 120,Align.center);
        optionC.setPosition(450 / 2,GameInfo.HEIGHT / 2 - 200,Align.center);
        optionD.setPosition(450 / 2,GameInfo.HEIGHT / 2 - 280,Align.center);

        stage.addActor(optionA);
        stage.addActor(optionB);
        stage.addActor(optionC);
        stage.addActor(optionD);

        Label.LabelStyle lblSoruStyle = new Label.LabelStyle();
        lblSoruStyle.font = Assests.getInstance().assetManager.get(Assests.fntArial, BitmapFont.class);

        lblSoru = new Label("",lblSoruStyle);
        lblSoru.setPosition(25 , GameInfo.HEIGHT / 2 + 180,Align.center);
        lblSoru.setWrap(true);
        lblSoru.setWidth(400);
        lblSoru.setAlignment(Align.center);

        stage.addActor(lblSoru);

        Label.LabelStyle lblSoruIdStyle = new Label.LabelStyle();
        lblSoruIdStyle.font = Assests.getInstance().assetManager.get(Assests.fntBold, BitmapFont.class);
        lblSoruIdStyle.fontColor = Color.valueOf("#8930dd");

        lblSrouId = new Label("",lblSoruIdStyle);
        lblSrouId.setPosition(450 - 150 , GameInfo.HEIGHT - 60,Align.topRight);

        stage.addActor(lblSrouId);

        ImageButton.ImageButtonStyle backStyle = new ImageButton.ImageButtonStyle();
        backStyle.up = new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.backPurple,Texture.class)));

        btnBack = new ImageButton(backStyle);
        btnBack.setBounds(50,GameInfo.HEIGHT - 94,64,64);
        btnBack.setTransform(true);
        btnBack.setOrigin(Align.center);

        btnBack.addListener(new ButtonHoverAnimation(btnBack));

        stage.addActor(btnBack);

        SequenceAction soruIdAction = new SequenceAction();
        soruIdAction.addAction(Actions.moveTo(lblSrouId.getX() + 200,lblSrouId.getY()));
        soruIdAction.addAction(Actions.repeat(25,Actions.moveBy(-8,0)));
        lblSrouId.addAction(soruIdAction);

        SequenceAction backAction = new SequenceAction();
        backAction.addAction(Actions.moveTo(btnBack.getX() - 200,btnBack.getY()));
        backAction.addAction(Actions.repeat(25,Actions.moveBy(8,0)));
        btnBack.addAction(backAction);

    }

    public void setTexts(String[] options,String soru,int soruId,int countSoru){



        boolean first = (soruId == 1);
        ActionIn(optionA,options[0],first);
        ActionIn(optionB,options[1],first);
        ActionIn(optionC,options[2],first);
        ActionIn(optionD,options[3],first);
        ActionIn(lblSoru,soru,first);


        lblSrouId.setText(soruId + " / " + countSoru);



    }

    private void ActionIn(final TextButton button, final String text, boolean first) {
        SequenceAction sequenceAction = new SequenceAction();

        if (!first)
            sequenceAction.addAction(Actions.repeat(25, Actions.moveBy(450 / 25f, 0)));


        sequenceAction.addAction(Actions.moveTo(button.getX() - 450, button.getY()));
        RunnableAction runnableAction = new RunnableAction();
        runnableAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                    button.setText(text);
             }
            });
        sequenceAction.addAction(runnableAction);
        sequenceAction.addAction(Actions.repeat(25, Actions.moveBy(450 / 25f, 0)));

        RunnableAction runnableAction2 = new RunnableAction();
        runnableAction2.setRunnable(new Runnable() {
            @Override
            public void run() {
                buttonClicked = false;
            }
        });
        sequenceAction.addAction(runnableAction2);

        button.addAction(sequenceAction);

    }

    private void ActionIn(final Label label, final String text, boolean first){
        SequenceAction sequenceAction = new SequenceAction();

        if(!first)
            sequenceAction.addAction(Actions.repeat(25,Actions.moveBy(450 / 25f,0)));


        sequenceAction.addAction(Actions.moveTo(label.getX() - 450, label.getY()));
        RunnableAction runnableAction = new RunnableAction();
        runnableAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                label.setText(text);
            }
        });
        sequenceAction.addAction(runnableAction);
        sequenceAction.addAction(Actions.repeat(25, Actions.moveBy(450 / 25f, 0)));

        label.addAction(sequenceAction);
    }


    public void finishTest(int countTest) {
        lblSoru.addAction(Actions.repeat(50,Actions.moveBy(0,450 / -25f)));
        optionA.addAction(Actions.repeat(40,Actions.moveBy(0,450 / -25f)));
        optionB.addAction(Actions.repeat(30,Actions.moveBy(0,450 / -25f)));
        optionC.addAction(Actions.repeat(20,Actions.moveBy(0,450 / -25f)));
        optionD.addAction(Actions.repeat(10,Actions.moveBy(0,450 / -25f)));
        lblSrouId.addAction(Actions.repeat(25, Actions.moveBy(450 / 25f,0)));
        btnBack.addAction(Actions.repeat(25, Actions.moveBy(450 / -25f,0)));

        lblFalse.setText("[RED]"+ (countTest - skor));
        lblTrue.setText("[#00aa00]"+ skor);

        if(skor >= countTest / 2) {
            Image i = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.light, Texture.class))));
            i.setPosition(450 / 2, -190, Align.center);
            i.setOriginX(i.getWidth() / 2);
            i.addAction(new SequenceAction(Actions.repeat(100, Actions.moveBy(0, 8)),
                    Actions.forever(new SequenceAction(Actions.repeat(10, Actions.rotateBy(1)),
                            Actions.repeat(20, Actions.rotateBy(-1)),
                            Actions.repeat(10, Actions.rotateBy(1))))));

            Image j = new Image(new SpriteDrawable(new Sprite(Assests.getInstance().assetManager.get(Assests.light, Texture.class))));
            j.setPosition(450 / 2, -190, Align.center);
            j.setOriginX(i.getWidth() / 2);
            j.addAction(new SequenceAction(Actions.repeat(100, Actions.moveBy(0, 8)),
                    Actions.forever(new SequenceAction(Actions.repeat(10, Actions.rotateBy(-1)),
                            Actions.repeat(20, Actions.rotateBy(1)),
                            Actions.repeat(10, Actions.rotateBy(-1))))));
            stage.addActor(i);
            stage.addActor(j);
            stage.addActor(lifes);
            lifes.addAction(Actions.repeat(100,Actions.moveBy(0,8)));

            if(GameManager.getInstance().gameData.getHak() < 5)
                GameManager.getInstance().gameData.setHak(GameManager.getInstance().gameData.getHak() + 1);

        }else {
            stage.addActor(lblYetersiz);
            lblYetersiz.addAction(Actions.repeat(100,Actions.moveBy(0,8)));
        }
        stage.addActor(sonuc);
        stage.addActor(lblFalse);
        stage.addActor(lblTrue);
        stage.addActor(testFinished);
        stage.addActor(btnHome);

        sonuc.addAction(Actions.repeat(100,Actions.moveBy(0,8)));
        btnHome.addAction(Actions.repeat(100,Actions.moveBy(0,8)));
        lblFalse.addAction(Actions.repeat(100,Actions.moveBy(0,8)));
        lblTrue.addAction(Actions.repeat(100,Actions.moveBy(0,8)));
        testFinished.addAction(new SequenceAction(Actions.repeat(100,Actions.moveBy(0,16)),
                Actions.forever(new SequenceAction(Actions.repeat(20,Actions.moveBy(0.5f,0)),
                        Actions.repeat(40,Actions.moveBy(-0.5f,0)),
                            Actions.repeat(20,Actions.moveBy(0.5f,0))))));

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
