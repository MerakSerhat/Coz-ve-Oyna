package com.serhatmerak.cozveoyna.hophop;

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
 * Created by Serhat Merak on 18.02.2018.
 */

public class Grass {
    public boolean firstGrass;
    public boolean drawCarrot = true;
    public Fixture fixture;
    public Vector2 pos;

    private World world;
    private Body body;
    private Texture grass;
    public Carrot carrot;

    public Grass(World world,Vector2 pos,boolean firstGrass){
        this.world = world;
        this.pos = pos;
        this.firstGrass = firstGrass;

        grass = Assests.getInstance().assetManager.get(Assests.ground_grass);

        if(!firstGrass)
            carrot = new Carrot(world,new Vector2(pos.x,pos.y + 30));
        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(pos.x / GameInfo.PPM,pos.y / GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((grass.getWidth() - 20) / GameInfo.PPM / 2f,grass.getHeight() / GameInfo.PPM / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Grass");
        fixture.setFriction(fixture.getFriction() + 0.3f);


        shape.dispose();
    }

    public void update() {

    }

    public void draw(SpriteBatch batch){
        batch.draw(grass,(body.getPosition().x * GameInfo.PPM ) - grass.getWidth() / 2
                ,(body.getPosition().y * GameInfo.PPM) - (grass.getHeight() / 2));

        if(!firstGrass && drawCarrot)
            carrot.draw(batch);
    }

}

