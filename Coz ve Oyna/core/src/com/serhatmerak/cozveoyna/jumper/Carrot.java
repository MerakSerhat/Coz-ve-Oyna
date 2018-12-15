package com.serhatmerak.cozveoyna.jumper;

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

/**
 * Created by Serhat Merak on 14.02.2018.
 */

public class Carrot {
    private World world;
    private Vector2 pos;
    private Texture carrot;

    public Fixture fixture;

    public Carrot(World world, Vector2 pos){
        this.pos = pos;
        this.world = world;

        carrot = Assests.getInstance().assetManager.get(Assests.carrot);
        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x / GameInfo.PPM,pos.y / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((carrot.getWidth() / 2f ) / GameInfo.PPM,
                (carrot.getHeight() / 2f ) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Carrot");
        fixture.setSensor(true);
        shape.dispose();
    }

    public void draw(SpriteBatch batch){
        batch.draw(carrot,( fixture.getBody().getPosition().x * GameInfo.PPM ) - carrot.getWidth() / 2,
                (fixture.getBody().getPosition().y * GameInfo.PPM) - carrot.getHeight() / 2);
    }
}
