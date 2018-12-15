package com.serhatmerak.cozveoyna.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.CustomScreen;
import com.serhatmerak.cozveoyna.helpers.GameInfo;

/**
 * Created by Serhat Merak on 25.02.2018.
 */

public class GameFotoScreen extends CustomScreen {

    Texture bg;
    Texture bunnyJump;
    Texture grass;
    Texture carrot;
    Texture enemy;
    Texture olcek;
    Texture pix;
    TextureAtlas.AtlasRegion spinner;

    public GameFotoScreen(){
        bg = Assests.getInstance().assetManager.get(Assests.jumper_bg);
        bunnyJump = Assests.getInstance().assetManager.get(Assests.bunny_jump);
        grass = Assests.getInstance().assetManager.get(Assests.ground_grass);
        TextureAtlas textureAtlas = Assests.getInstance().assetManager.get(Assests.spring);
        spinner = textureAtlas.getRegions().get(2);
        carrot = Assests.getInstance().assetManager.get(Assests.carrot);
        enemy = Assests.getInstance().assetManager.get(Assests.fly_enemy);
        olcek = Assests.getInstance().assetManager.get(Assests.olcek);
        pix = Assests.getInstance().assetManager.get(Assests.pixWhite);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        /*
        batch.draw(bg,0,0);
        batch.draw(grass,100,150);
        batch.draw(bunnyJump,150,250);
        batch.draw(spinner,100 + grass.getWidth() / 2 - spinner.getRegionWidth() / 2,150 + grass.getHeight());
        batch.draw(grass,250,350);
        batch.draw(carrot,250 + grass.getWidth() / 2 - carrot.getWidth() / 2,360 + grass.getHeight());
        batch.draw(enemy,300,230);
        */

        batch.draw(bg,0,0);
        batch.draw(grass,80,150);
        batch.draw(bunnyJump,120,250);
        batch.draw(grass,280,300);
        batch.draw(carrot,280 + grass.getWidth() / 2 - carrot.getWidth() / 2,310 + grass.getHeight());
        batch.draw(olcek, GameInfo.WIDTH / 2 - olcek.getWidth() / 2,60);
        batch.draw(pix,(GameInfo.WIDTH / 2 - olcek.getWidth() / 2 ) + 4,64,160,25);
        batch.end();
    }
}
