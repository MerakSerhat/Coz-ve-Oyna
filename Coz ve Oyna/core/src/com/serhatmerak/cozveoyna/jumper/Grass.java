package com.serhatmerak.cozveoyna.jumper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.GameInfo;

/**
 * Created by Serhat Merak on 12.02.2018.
 */

public class Grass {
    public boolean isMoveable;
    public boolean isBroken;
    public boolean isHaveCarrot;
    public boolean isHaveSpring;
    public Fixture fixture;
    public Vector2 pos;
    public Carrot carrot;
    public Spring spring;

    private World world;
    private Body body;
    private Texture brokenGrass,grass;

    private float speed = 0.1f;

    public Grass(World world,Vector2 pos, boolean isMoveable, boolean isBroken){
        this.world = world;
        this.isBroken = isBroken;
        this.isMoveable = isMoveable;
        isHaveCarrot = MathUtils.randomBoolean();
        this.pos = pos;

        grass = Assests.getInstance().assetManager.get(Assests.ground_grass);
        brokenGrass = Assests.getInstance().assetManager.get(Assests.ground_grass_broken);

        if(isHaveCarrot)
            carrot = new Carrot(world,new Vector2(pos.x,pos.y + 26f));
        else {
            //havuç yoksa springin 1/8 oranında olmasını sağlama
            boolean b1 = MathUtils.randomBoolean();
            boolean b2 = MathUtils.randomBoolean();

            if(b1 && b2 && !isBroken){
                isHaveSpring = true;
                spring = new Spring(world,new Vector2(pos.x,pos.y + 26f));
            }
        }

        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();

        if(isMoveable)
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        else
            bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(pos.x / GameInfo.PPM,pos.y / GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(grass.getWidth() / GameInfo.PPM / 2f,0.1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        if(isBroken)
            fixture.setUserData("brokenGrass");
        else
            fixture.setUserData("Grass");

        fixture.setSensor(true);

        shape.dispose();
    }

    public void update() {
        if(isMoveable){
            body.setTransform(body.getPosition().x + speed,body.getPosition().y,0);

            if(body.getPosition().x <= 3)
                speed *= -1;
            else if(body.getPosition().x >= (GameInfo.WIDTH - 60) / GameInfo.PPM)
                speed *= -1;

            if(isHaveCarrot){
                carrot.fixture.getBody().setTransform((body.getPosition().x),
                        (body.getPosition().y + 26f / GameInfo.PPM),0);
            }

            if (isHaveSpring){
                spring.fixture.getBody().setTransform((body.getPosition().x),
                        (body.getPosition().y + 26f / GameInfo.PPM),0);
            }


        }
    }

    public void draw(SpriteBatch batch){
        batch.draw(isBroken? brokenGrass:grass,(body.getPosition().x * GameInfo.PPM ) - grass.getWidth() / 2
                ,(body.getPosition().y * GameInfo.PPM) - (grass.getHeight() / 2 + 10));

        if (isHaveCarrot)
            carrot.draw(batch);

        if(isHaveSpring)
            spring.draw(batch);
    }

}
