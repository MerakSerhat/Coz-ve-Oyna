package com.serhatmerak.cozveoyna.jumper;

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
 * Created by Serhat Merak on 12.02.2018.
 */

public class GrassController {
    private World world;
    private int posY = 120;
    private int skor;

    private Array<Grass> grasses;
    private Array<Enemy> enemies;

    public GrassController(World world){
        this.world = world;
        grasses = new Array<Grass>();
        enemies = new Array<Enemy>();
        fillGrasses();
    }

    private void fillGrasses() {
        for (int i = 0; i < 20; i++) {
            grasses.add(new Grass(world, new Vector2(MathUtils.random(60, 420), posY),
                    MathUtils.randomBoolean(), MathUtils.randomBoolean()));
            posY += 180;
        }

        //ilk iki taşın hiç bir zaman kırık olmaması için
        if(grasses.size == 20) {
            grasses.get(0).isBroken = false;
            grasses.get(0).fixture.setUserData("Grass");
            grasses.get(1).isBroken = false;
            grasses.get(1).fixture.setUserData("Grass");
        }

        //Başlangıç dışında bir yaratık oluşturmak için
        filEnemies();


    }

    private void filEnemies() {

        //Olabildiğince art arda gelecek düşmanı önlemek için
        Grass grass = grasses.get(MathUtils.random(9, grasses.size - 4));
        //Eğer toprak hareketliyse hareketsiz toprak bulana kadar devam
        //Eğer hareketsiz toprak bulamazsa sonsuz döngüye girmemesi için
        // en fazla 6 denemeye kadar izin veriliyor
        if (grass.isMoveable) {
            int i = 0;
            while (10 > i && grass.isMoveable) {
                i++;
                grass = grasses.get(MathUtils.random(9, grasses.size - 4));
            }
        }

        if (!grass.isMoveable)
            enemies.add(new Enemy(world, new Vector2(0, grass.pos.y + 90)));
    }




    public void update(float bunnyY){
        for (Grass grass:grasses) {
            grass.update();

            //Kırkık taşları sil
            if(grass.fixture.getUserData().equals("Remove")){
                world.destroyBody(grass.fixture.getBody());
                if(grass.isHaveCarrot)
                    world.destroyBody(grass.carrot.fixture.getBody());


                grasses.removeValue(grass,false);
                break;
            }

            //Yenilen havuçları sil
            if(grass.isHaveCarrot && grass.carrot.fixture.getUserData().equals("Remove")){
                if(GameManager.getInstance().gameData.isSoundOpen())
                    Assests.getInstance().assetManager.get(Assests.sndCollect, Sound.class).play();
                world.destroyBody(grass.carrot.fixture.getBody());
                grass.isHaveCarrot = false;
                grass.carrot = null;
            }
        }

        for (Enemy enemy:enemies){
            enemy.update();
        }

        //Taş döngüsünü tekrarla
        if(grasses.get(grasses.size - 1).pos.y - bunnyY < 800){
            fillGrasses();
        }

        //Arkada kalan taşları sil
        if(grasses.get(0).pos.y < bunnyY - 400){
            world.destroyBody(grasses.get(0).fixture.getBody());
            grasses.removeValue(grasses.get(0),false);
        }
    }

    public void draw(SpriteBatch batch){
        for (Grass grass:grasses) {
            grass.draw(batch);
        }
        for (Enemy enemy:enemies){
            enemy.draw(batch);
        }
    }

    public void setSkor(int skor) {
        this.skor = skor;
    }

    public void restart(){
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body:bodies){
            if(!body.getFixtureList().get(0).getUserData().equals("Bunny") &&
                    !body.getFixtureList().get(0).getUserData().equals("Ground")){
                world.destroyBody(body);
            }
        }
        grasses.clear();
        enemies.clear();

        posY = 150;
        fillGrasses();
    }

}
