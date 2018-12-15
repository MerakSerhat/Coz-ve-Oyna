package com.serhatmerak.cozveoyna.jumper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.serhatmerak.cozveoyna.GameMain;
import com.serhatmerak.cozveoyna.helpers.AllScreens;
import com.serhatmerak.cozveoyna.helpers.Assests;
import com.serhatmerak.cozveoyna.helpers.CustomScreen;
import com.serhatmerak.cozveoyna.helpers.GameInfo;
import com.serhatmerak.cozveoyna.helpers.GameManager;
import com.serhatmerak.cozveoyna.helpers.ScreenAnimation;
import com.serhatmerak.cozveoyna.huds.GamePlayHuds;
import com.serhatmerak.cozveoyna.screens.GameSelectScreen;
import com.serhatmerak.cozveoyna.screens.MainMenuScreen;

/**
 * Created by Serhat Merak on 11.02.2018.
 */

public class JumperScreen extends CustomScreen implements ContactListener,InputProcessor {

    private GameMain game;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera box2dCamera;
    private OrthographicCamera inputCamera;

    private Texture bg;
    private Bunny bunny;
    private GrassController grassController;

    private BitmapFont font;

    private Texture carrot;
    private ScreenAnimation screenAnimation;
    private boolean changeScreen = false;

    private float elapsedTime = 0;
    private int numsIndex = 2;


    private int skor = 0;

    private enum State{
        Running,
        Starting,
        Paused,
        GameOver
    }

    private State state = State.Starting;
    private GamePlayHuds huds;



    public JumperScreen(GameMain game){
        this.game = game;

        bg = Assests.getInstance().assetManager.get(Assests.jumper_bg);
        carrot = Assests.getInstance().assetManager.get(Assests.carrot);
        font = Assests.getInstance().assetManager.get(Assests.fntBlow);
        font.setColor(Color.valueOf("#e86a17"));

        world = new World(new Vector2(0,-40f),true);
        world.setContactListener(this);

        box2DDebugRenderer = new Box2DDebugRenderer();
        box2dCamera = new OrthographicCamera();
        box2dCamera.setToOrtho(false,GameInfo.WIDTH / GameInfo.PPM,
                GameInfo.HEIGHT / GameInfo.PPM);

        inputCamera = new OrthographicCamera();
        inputCamera.setToOrtho(false,GameInfo.WIDTH,GameInfo.HEIGHT);


        createGroundBody();
        bunny = new Bunny(world);
        grassController = new GrassController(world);

        screenAnimation = new ScreenAnimation(game);

        huds = new GamePlayHuds(batch,new FitViewport(GameInfo.WIDTH,GameInfo.HEIGHT,inputCamera));
        addListeners();

        if(GameManager.getInstance().gameData.isMusicOpen())
            Assests.getInstance().assetManager.get(Assests.mscMusic, Music.class).play();

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(huds.stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(state == State.Running)
            updateGame();

        drawGame();



        if(changeScreen) {
            if (Assests.getInstance().assetManager.get(Assests.mscMusic, Music.class).isPlaying())
                Assests.getInstance().assetManager.get(Assests.mscMusic, Music.class).stop();
            screenAnimation.changeScreen(batch);
        }


    }


    private void createGroundBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        Body groundBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameInfo.WIDTH / GameInfo.PPM,0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = groundBody.createFixture(fixtureDef);
        fixture.setUserData("Ground");
        shape.dispose();
    }
    private void restartGame() {
        state = State.Starting;
        camera.position.set(new Vector3(GameInfo.WIDTH / 2,GameInfo.HEIGHT / 2,0));
        box2dCamera.position.set(camera.position.x / GameInfo.PPM,camera.position.y / GameInfo.PPM,0);
        camera.update();
        box2dCamera.update();

        skor = 0;

        bunny.restart();
        grassController.restart();
    }


    private void updateGame() {

        if(bunny.maxY > 400)
            camera.position.set(new Vector3(camera.position.x,bunny.maxY,0));
        box2dCamera.position.set(camera.position.x / GameInfo.PPM,camera.position.y / GameInfo.PPM,0);
        camera.update();
        box2dCamera.update();
        world.step(Gdx.graphics.getDeltaTime(),6,2);
        grassController.update(bunny.body.getPosition().y * GameInfo.PPM);
        bunny.update();
        checkBunnyOutOfBounds();
    }

    private void checkBunnyOutOfBounds() {
        if(bunny.body.getPosition().y * GameInfo.PPM + 100 < camera.position.y - GameInfo.HEIGHT / 2){
            state = State.GameOver;
            System.out.println(skor + " " + GameManager.getInstance().gameData.getHgscr_jumper() );
            if(skor > GameManager.getInstance().gameData.getHgscr_jumper()) {
                GameManager.getInstance().gameData.setHgscr_jumper(skor);
                GameManager.getInstance().saveData();
            }

            huds.gameOver(skor,GameManager.getInstance().gameData.getHgscr_jumper());
        }
    }

    private void drawGame() {

        if(state == State.GameOver)
            batch.setColor(Color.RED);
        else
            batch.setColor(Color.WHITE);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        {
            batch.draw(bg, 0, camera.position.y - GameInfo.HEIGHT / 2);
            grassController.draw(batch);
            bunny.draw(batch);
        }
        if(state != State.GameOver) {
            batch.setProjectionMatrix(inputCamera.combined);
            {
                batch.draw(carrot, 36, GameInfo.HEIGHT - 70);
                font.draw(batch, skor + "", 90, GameInfo.HEIGHT - 44);
            }
        }
        if(state == State.Starting)
            startingAnimation();
        batch.end();


        huds.stage.act();
        huds.stage.draw();
    }

    private void addListeners() {
        huds.btnPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                state = State.Paused;
                huds.pause();
            }
        });

        huds.btnResume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                state = State.Starting;
                huds.resume();
            }
        });

        huds.btnHome.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                huds.showDialog(huds.dlgExit);
            }
        });

        huds.btnRestart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(GameManager.getInstance().gameData.getHak() > 0)
                    huds.showDialog(huds.dlgRestart);
                else
                    huds.showDialog(huds.dlgYetersiz);
            }
        });

        huds.btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.MainMenu);
                changeScreen = true;
            }
        });

        //yes
        huds.dlgExit.getButtonTable().getChildren().get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.MainMenu);
                changeScreen = true;
                huds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.removeActor()));
            }
        });
        huds.dlgRestart.getButtonTable().getChildren().get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.getInstance().gameData.setHak(GameManager.getInstance().gameData.getHak() - 1);
                GameManager.getInstance().saveData();
                restartGame();
                huds.restart();
            }
        });
        huds.dlgYetersiz.getButtonTable().getChildren().get(0).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenAnimation.setNextScreen(AllScreens.TestSelectScreen);
                changeScreen = true;
            }
        });


        //no
        huds.dlgExit.getButtonTable().getChildren().get(1).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                huds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.removeActor()));
            }
        });
        //no
        huds.dlgYetersiz.getButtonTable().getChildren().get(1).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                huds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.removeActor()));
            }
        });
        //no
        huds.dlgRestart.getButtonTable().getChildren().get(1).addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                huds.pix.addAction(new SequenceAction(Actions.fadeOut(0.4f),Actions.removeActor()));
            }
        });
    }

    private void startingAnimation() {
        TextureAtlas nums = Assests.getInstance().assetManager.get(Assests.nums);
        batch.draw(nums.getRegions().get(numsIndex),GameInfo.WIDTH / 2 -
                nums.getRegions().get(numsIndex).getRegionWidth() / 2,
                GameInfo.HEIGHT / 2 - nums.getRegions().get(numsIndex).getRegionHeight() / 2);

        elapsedTime += Gdx.graphics.getDeltaTime();
        if(elapsedTime > 1f){
            numsIndex--;
            elapsedTime = 0;
            if(numsIndex == -1){
                state = State.Running;
                elapsedTime = 0;
                numsIndex = 2;
            }
        }
    }



    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();

        if(isConnact("Bunny","Ground",A,B)){
            bunny.collision = Bunny.Collision.Bunny_groundGrass;
        }

        if(isConnact("Bunny","Grass",A,B)){
            bunny.collision = Bunny.Collision.Bunny_groundGrass;
        }

        if(isConnact("Bunny","brokenGrass",A,B)){
            bunny.collision = Bunny.Collision.Bunny_groundGrassBroken;

            if(A.getUserData().equals("brokenGrass"))
                bunny.fixtureToBeRemoved = A;
            else
                bunny.fixtureToBeRemoved = B;
        }

        if(isConnact("Bunny","Carrot",A,B)){
            if(A.getUserData().equals("Carrot"))
                A.setUserData("Remove");
            else
                B.setUserData("Remove");

            skor++;
            grassController.setSkor(skor);
        }

        if(isConnact("Bunny","Spring",A,B)){
            bunny.collision = Bunny.Collision.Bunny_Spinner;

            if(A.getUserData().equals("Spring"))
                bunny.springFixture = A;
            else
                bunny.springFixture = B;
        }

        if(isConnact("Bunny","Enemy",A,B)){
           bunny.dead();
        }
    }
    @Override
    public void endContact(Contact contact) {}
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    private boolean isConnact(String id1,String id2,Fixture A , Fixture B){
        return  ((A.getUserData().equals(id1) && B.getUserData().equals(id2)) ||
                (B.getUserData().equals(id1) && A.getUserData().equals(id2)) );
    }



    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            if(state != State.Paused){
                state = State.Paused;
                huds.pause();
            }else {
                state = State.Running;
                huds.resume();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
