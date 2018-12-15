package com.serhatmerak.cozveoyna.hophop;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.GameManager;

/**
 * Created by Serhat Merak on 18.02.2018.
 */

public class GrassController {
    private World world;
    private float grassX = 350;
    private Array<Grass> grasses;

    public GrassController(World world){
        this.world = world;
        grasses = new Array<Grass>();
        fillGrass();
    }

    private void fillGrass() {
        for(int i=0;i<10;i++){
            grasses.add(new Grass(world,new Vector2(grassX, MathUtils.random(300,650)),false));
            grassX += 250;
        }
    }

    public void update(float camX){
        if(grasses.get(grasses.size - 3).pos.x < camX )
            fillGrass();

        for (Grass grass:grasses){
            if (!grass.firstGrass && grass.drawCarrot && grass.carrot.fixture.getUserData().equals("Remove")){
                if(GameManager.getInstance().gameData.isSoundOpen())
                    Assests.getInstance().assetManager.get(Assests.sndCollect, Sound.class).play();
                world.destroyBody(grass.carrot.fixture.getBody());
                grass.drawCarrot = false;
            }
        }
    }

    public void draw(SpriteBatch batch){
        for (Grass grass :grasses) {
            grass.draw(batch);
        }
    }

    public void restart(){
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for(Body body:bodies){
            if(!body.getFixtureList().get(0).getUserData().equals("Bunny") &&
                    !body.getFixtureList().get(0).getUserData().equals("FirstGrass"))
                world.destroyBody(body);
        }
        grassX = 350;
        grasses.clear();
        fillGrass();
    }
}
