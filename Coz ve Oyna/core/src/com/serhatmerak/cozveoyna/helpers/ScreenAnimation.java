package com.serhatmerak.cozveoyna.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.hophop.HopHopScreen;
import com.serhatmerak.cozveoyna.jumper.JumperScreen;
import com.serhatmerak.cozveoyna.screens.GameSelectScreen;
import com.serhatmerak.cozveoyna.screens.MainMenuScreen;
import com.serhatmerak.cozveoyna.screens.TestScreen;
import com.serhatmerak.cozveoyna.screens.TestSelectScreen;

/**
 * Created by Serhat Merak on 1.03.2018.
 */

public class ScreenAnimation {

    private GameMain game;
    private AllScreens nextScreen;
    private Sprite pix;
    private float alpha;
    private boolean ran = false;
    public boolean opened = false;
    public boolean pixDrawed = false;


    public ScreenAnimation(GameMain game){
        this.game = game;
        if(Assests.getInstance().assetManager.isLoaded(Assests.pixWhite))
            pix = new Sprite(Assests.getInstance().assetManager.get(Assests.pixWhite,Texture.class));
        else
            pix = new Sprite(new Texture(Assests.pixWhite));

        pix.setBounds(0,0,GameInfo.WIDTH,GameInfo.HEIGHT);
    }





    public void setNextScreen(AllScreens nextScreen) {
        this.nextScreen = nextScreen;
    }

    public void changeScreen(SpriteBatch batch){
        if(!ran){
            pix.setColor(new Color(1,1,1,0));
            alpha = 0;
            ran = true;
        }

        alpha += 0.1f;
        pix.setColor(1,1,1,alpha);

        batch.begin();
            pix.draw(batch);
        batch.end();

        if(alpha >=  1f) {
            switch (nextScreen){
                case MainMenu:
                    game.setScreen(new MainMenuScreen(game));
                    break;
                case GameSelectScreen:
                    game.setScreen(new GameSelectScreen(game));
                    break;
                case TestSelectScreen:
                    game.setScreen(new TestSelectScreen(game));
                    break;
                case JumperScreen:
                    game.setScreen(new JumperScreen(game));
                    break;
                case HopHopScreen:
                    game.setScreen(new HopHopScreen(game));
                    break;
            }

        }
    }

    public void openScreen(SpriteBatch batch){
        if(!ran){
            pix.setColor(new Color(1,1,1,1));
            alpha = 1f;
            ran = true;
        }

        alpha -= 0.1f;
        pix.setColor(1,1,1,alpha);

        batch.begin();
        pix.draw(batch);
        batch.end();

        if(alpha <=  0f) {
            ran = false;
            opened = true;
        }
    }
}
