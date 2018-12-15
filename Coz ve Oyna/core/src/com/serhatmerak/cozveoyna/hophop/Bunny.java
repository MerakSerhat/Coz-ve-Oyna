package com.serhatmerak.cozveoyna.hophop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;

/**
 * Created by Serhat Merak on 18.02.2018.
 */

public class Bunny {
    private World world;
    public Body body;
    private Texture bunny_sit,bunny_jump;

    private boolean holding = false;
    public float holdTime = 0;
    public float lastHoldTime = 0;
    public final float maxHoldTime = 1.2f;
    public final float minHoldTime = 0.2f;

    private float deltaHoldingTime = Gdx.graphics.getDeltaTime();

    public enum State{
        SITTING,
        JUMPING
    }

    public State state = State.SITTING;

    public Bunny(World world){
        this.world = world;
        if(GameManager.getInstance().gameData.isErkek()) {
            bunny_sit = Assests.getInstance().assetManager.get(Assests.bunny_ready);
            bunny_jump = Assests.getInstance().assetManager.get(Assests.bunny_jump);
        }else {
            bunny_sit = Assests.getInstance().assetManager.get(Assests.bunny2_ready);
            bunny_jump = Assests.getInstance().assetManager.get(Assests.bunny2_jump);
        }

        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((100) / GameInfo.PPM,400 / GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(((bunny_jump.getWidth() - 20 ) / 2f ) / GameInfo.PPM,1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Bunny");
        shape.dispose();
    }

    public void update(){
        if(holding)
            holdTime += deltaHoldingTime / 2;

        if(holdTime > maxHoldTime)
            deltaHoldingTime *= -1;


    }

    public void startClicked(){
        if(state == State.SITTING)
            holding = true;
    }

    public void endClicked(){
        if(state == State.SITTING) {
            holding = false;
            if(holdTime > minHoldTime) {
                if(GameManager.getInstance().gameData.isSoundOpen())
                    Assests.getInstance().assetManager.get(Assests.sndHighjump, Sound.class).play();
                body.setLinearVelocity(5+ holdTime * 5, 17 + holdTime * 17);
                state = State.JUMPING;
            }
            lastHoldTime = holdTime;
            holdTime = 0;
            deltaHoldingTime = Gdx.graphics.getDeltaTime();
        }
    }

    public void draw(SpriteBatch batch){
        switch (state){
            case JUMPING:
                batch.draw(bunny_jump, (body.getPosition().x * GameInfo.PPM) - bunny_sit.getWidth() / 2,
                        (body.getPosition().y * GameInfo.PPM) - bunny_sit.getHeight() / 2 + 15);
                break;
            case SITTING:
                batch.draw(bunny_sit, (body.getPosition().x * GameInfo.PPM) - bunny_sit.getWidth() / 2,
                        (body.getPosition().y * GameInfo.PPM) - bunny_sit.getHeight() / 2 + 15
                        ,bunny_jump.getWidth(),bunny_jump.getHeight() - (holdTime > minHoldTime?holdTime * 5 : 0));

                Gdx.graphics.getDeltaTime();
        }
    }

    public void restart(){
        body.setTransform((100) / GameInfo.PPM,400 / GameInfo.PPM,0);
        body.setLinearVelocity(0,0);
        holdTime = 0;
        lastHoldTime = 0;
        holding = false;
        state = State.SITTING;

    }
}
