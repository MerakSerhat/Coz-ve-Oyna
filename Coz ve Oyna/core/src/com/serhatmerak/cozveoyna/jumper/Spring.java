package com.serhatmerak.cozveoyna.jumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
 * Created by Serhat Merak on 17.02.2018.
 */

public class Spring {
    private World world;
    private TextureAtlas atlas;
    private Animation<TextureRegion> springAnim;
    private Vector2 pos;
    private float elapsedTime = 0;
    private boolean contacted = false;

    public Fixture fixture;
    public boolean bodyDeleted = false;

    public Spring(World world, Vector2 pos){
        this.pos = pos;
        this.world = world;
        atlas = Assests.getInstance().assetManager.get(Assests.spring);
        springAnim = new Animation<TextureRegion>(0.1f,atlas.getRegions(), Animation.PlayMode.NORMAL);

        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(pos.x / GameInfo.PPM,pos.y / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Spring");
        fixture.setSensor(true);
        shape.dispose();
    }


    public void draw(SpriteBatch batch){
        if(fixture.getUserData() == null)
            contacted = true;

        if(contacted) elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(springAnim.getKeyFrame(elapsedTime),( fixture.getBody().getPosition().x * GameInfo.PPM ) - 22,
                (fixture.getBody().getPosition().y * GameInfo.PPM) - 25);
    }
}
