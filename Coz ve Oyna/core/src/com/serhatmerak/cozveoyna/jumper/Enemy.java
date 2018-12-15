package com.serhatmerak.cozveoyna.jumper;

/**
 * Created by Serhat Merak on 15.02.2018.
 */

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


public class Enemy {
    private World world;
    private Vector2 pos;
    private Texture enemy;

    public Fixture fixture;

    private float speed = 0.1f;

    public Enemy(World world, Vector2 pos) {
        this.pos = pos;
        this.world = world;

        pos.x = MathUtils.random(80,380);

        enemy = Assests.getInstance().assetManager.get(Assests.fly_enemy);
        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x / GameInfo.PPM, pos.y / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((enemy.getWidth() / 2f) / GameInfo.PPM,
                (enemy.getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Enemy");
        fixture.setSensor(true);
        shape.dispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(enemy, (fixture.getBody().getPosition().x * GameInfo.PPM) - enemy.getWidth() / 2,
                (fixture.getBody().getPosition().y * GameInfo.PPM) - enemy.getHeight() / 2);
    }

    public void update(){
        fixture.getBody().setTransform(fixture.getBody().getPosition().x + speed,
                fixture.getBody().getPosition().y,0);

        if(fixture.getBody().getPosition().x <= 3)
            speed *= -1;
        else if(fixture.getBody().getPosition().x >= (GameInfo.WIDTH - 60) / GameInfo.PPM)
            speed *= -1;
    }

}

