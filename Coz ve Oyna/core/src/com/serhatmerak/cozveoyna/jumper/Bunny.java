package com.serhatmerak.cozveoyna.jumper;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
 * Created by Serhat Merak on 12.02.2018.
 */

public class Bunny {
    public enum State{
        JUMPING,
        FALLING,
        DEAD
    };


    public float maxY;
    public State state = State.FALLING;
    public Body body;

    private World world;
    private Texture bunny_fall,bunny_hurt;
    private Sprite bunny_jump;
    private boolean bunnyLookingToRight = true;
    Application.ApplicationType applicationType = Gdx.app.getType();

    public enum Collision{
        Null,
        Bunny_groundGrass,
        Bunny_groundGrassBroken,
        Bunny_Spinner
    }

    public Collision collision = Collision.Null;
    public Fixture fixtureToBeRemoved;
    public Fixture springFixture;



    public Bunny(World world){
        this.world = world;
        if(GameManager.getInstance().gameData.isErkek()) {
            bunny_fall = Assests.getInstance().assetManager.get(Assests.bunny_ready);
            bunny_jump = new Sprite(Assests.getInstance().assetManager.get(Assests.bunny_jump, Texture.class));
            bunny_hurt = Assests.getInstance().assetManager.get(Assests.bunny_hurt);
        }else {
            bunny_fall = Assests.getInstance().assetManager.get(Assests.bunny2_ready);
            bunny_jump = new Sprite(Assests.getInstance().assetManager.get(Assests.bunny2_jump, Texture.class));
            bunny_hurt = Assests.getInstance().assetManager.get(Assests.bunny2_hurt);
        }

        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((GameInfo.WIDTH / 2) / GameInfo.PPM,200 / GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bunny_jump.getWidth() / 2f ) / GameInfo.PPM,1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Bunny");
        shape.dispose();
    }

    public void update(){

        if(state != State.DEAD) {
            if (body.getLinearVelocity().y <= 0)
                state = State.FALLING;
            else
                state = State.JUMPING;


            //Çarpışmaları kontrol edici
            if (collision == Collision.Bunny_groundGrass || collision == Collision.Bunny_groundGrassBroken) {
                if (state == State.FALLING) {
                    if(GameManager.getInstance().gameData.isSoundOpen())
                        Assests.getInstance().assetManager.get(Assests.sndJump, Sound.class).play();
                    jump(30);

                    if (collision == Collision.Bunny_groundGrassBroken) {
                        fixtureToBeRemoved.setUserData("Remove");
                    }
                }

                collision = Collision.Null;
            } else if (collision == Collision.Bunny_Spinner) {
                if(GameManager.getInstance().gameData.isSoundOpen())
                    Assests.getInstance().assetManager.get(Assests.sndHighjump, Sound.class).play();

                jump(62);
                springFixture.setUserData("Remove");



                collision = Collision.Null;
            }

            //Desktop kontroller
            if (applicationType == Application.ApplicationType.Desktop) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
                    body.setLinearVelocity(18, body.getLinearVelocity().y);
                else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
                    body.setLinearVelocity(-18, body.getLinearVelocity().y);
            } else {
                //Android Controller
                body.setLinearVelocity((Gdx.input.getAccelerometerX()) * -7, body.getLinearVelocity().y);
            }


            //Yanlardan çıkınca diğer tarafta ışınlanma
            if (body.getPosition().x < 0)
                body.setTransform(GameInfo.WIDTH / GameInfo.PPM, body.getPosition().y, 0);
            if (body.getPosition().x > GameInfo.WIDTH / GameInfo.PPM)
                body.setTransform(0, body.getPosition().y, 0);

            //Bunny hızını sabitlemek
            if (body.getLinearVelocity().y < -25)
                body.setLinearVelocity(body.getLinearVelocity().x, -25);
            if (body.getLinearVelocity().x > 35)
                body.setLinearVelocity(35, body.getLinearVelocity().y);
            if (body.getLinearVelocity().x < -35)
                body.setLinearVelocity(-35, body.getLinearVelocity().y);


            //maksimum yüksekliğe almak
            if (body.getPosition().y * GameInfo.PPM > maxY)
                maxY = body.getPosition().y * GameInfo.PPM;
        }
    }

    private void jump(float velocity) {
        body.setLinearVelocity(body.getLinearVelocity().x,velocity);
    }


    public void draw(SpriteBatch batch){
        switch (state){
            case FALLING:
            case JUMPING:{

                if(body.getLinearVelocity().x > 0){
                    if(!bunnyLookingToRight){
                        bunny_jump.flip(true,false);
                        bunnyLookingToRight = true;
                    }
                }else if(body.getLinearVelocity().x <0) {
                    if(bunnyLookingToRight){
                        bunny_jump.flip(true,false);
                        bunnyLookingToRight = false;
                    }
                }else {
                    batch.draw(bunny_fall, (body.getPosition().x * GameInfo.PPM) - bunny_fall.getWidth() / 2,
                            (body.getPosition().y * GameInfo.PPM) - bunny_fall.getHeight() / 2 + 10);
                    return;
                }

                batch.draw(bunny_jump, (body.getPosition().x * GameInfo.PPM) - bunny_fall.getWidth() / 2,
                        (body.getPosition().y * GameInfo.PPM) - bunny_fall.getHeight() / 2 + 10);

            }break;
            case DEAD:
                batch.draw(bunny_hurt,(body.getPosition().x * GameInfo.PPM ) - bunny_fall.getWidth() / 2 ,
                        (body.getPosition().y  * GameInfo.PPM ) - bunny_fall.getHeight() / 2 + 10);
                break;

        }
    }

    public void dead(){
        if(GameManager.getInstance().gameData.isSoundOpen())
            Assests.getInstance().assetManager.get(Assests.sndHit, Sound.class).play();
        state = State.DEAD;
        body.setLinearVelocity(0,0);
    }


    public void restart(){
        collision = Collision.Null;
        maxY = 0;
        state = State.FALLING;
        bunnyLookingToRight = true;
        if(bunny_jump.isFlipX())
            bunny_jump.flip(true,false);
        body.setLinearVelocity(0,0);
        body.setTransform((GameInfo.WIDTH / 2) / GameInfo.PPM,200 / GameInfo.PPM,0);
    }

}
