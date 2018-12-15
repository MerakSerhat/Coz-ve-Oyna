package com.serhatmerak.cozveoyna.helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.cozveoyna.sorular.Soru;

import javax.xml.soap.Text;

/**
 * Created by Serhat Merak on 11.02.2018.
 */

public class Assests {
    static final Assests ourInstance = new Assests();
    public static Assests getInstance() {
        return ourInstance;
    }
    private Assests() {}

    private final String characters = "abcçdefgğhıijklmnoööprsştuüvyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ:,?.=!0123456789+\" / -";
    public static final String bunny_hurt = "jumper/bunny_hurt.png";
    public static final String bunny_jump = "jumper/bunny_jump.png";
    public static final String bunny_ready = "jumper/bunny_ready.png";
    public static final String bunny2_hurt = "jumper/bunny2_hurt.png";
    public static final String bunny2_jump = "jumper/bunny2_jump.png";
    public static final String bunny2_ready = "jumper/bunny2_ready.png";
    public static final String fly_enemy = "jumper/fly_enemy.png";
    public static final String ground_grass = "jumper/ground_grass.png";
    public static final String ground_grass_broken = "jumper/ground_grass_broken.png";
    public static final String jumper_bg = "jumper/jumper_bg.png";
    public static final String carrot = "jumper/carrot.png";
    public static final String olcek ="jumper/olcek.png";
    public static final String spring = "jumper/spring.pack";
    public static final String bunny = "jumper/bunnyE.png";
    public static final String bunny2 = "jumper/bunnyK.png";


    public static final String fntBlow = "Fonts/blow.ttf";
    public static final String fntBold = "Fonts/bold.ttf";
    public static final String fntCooper = "Fonts/cooper.ttf";
    public static final String fntCooper2 = "Fonts/cooper2.ttf";
    public static final String fntTest = "Fonts/test.ttf";
    public static final String fntArial = "Fonts/arial.ttf";

    public static final String btnNormal = "huds/ButtonNormal.png";
    public static final String btnHover = "huds/ButtonHover.png";
    public static final String btnLocked = "huds/ButtonLocked.png";
    public static final String lifes = "huds/lifes.png";
    public static final String audioOn = "huds/audioOn.png";
    public static final String audioOff = "huds/audioOff.png";
    public static final String musicOn = "huds/musicOn.png";
    public static final String musicOff = "huds/musicOff.png";
    public static final String help = "huds/help.png";
    public static final String leadboard = "huds/leaderboard.png";
    public static final String home = "huds/home.png";
    public static final String pause = "huds/pause.png";
    public static final String resumeNormal = "huds/resumeNormal.png";
    public static final String resumeHover = "huds/resumeHover.png";
    public static final String homeNormal = "huds/homeNormal.png";
    public static final String homeHover = "huds/homeHover.png";
    public static final String gameOverPanel = "huds/gameOverPanel.png";
    public static final String restartNormal = "huds/restartNormal.png";
    public static final String restartHover = "huds/restartHover.png";
    public static final String backNormal = "huds/backNormal.png";
    public static final String backHover = "huds/backHover.png";
    public static final String backPurple = "huds/backPurple.png";
    public static final String treeUi = "huds/treeUi.atlas";
    public static final String testSon = "huds/testSon.png";
    public static final String light = "huds/light.png";
    public static final String emojies = "huds/emojies.pack";
    public static final String alertBG = "huds/alert BG.png";
    public static final String yes = "huds/yes.png";
    public static final String no = "huds/no.png";
    public static final String nums = "huds/nums.pack";

    public static final  String menuBG = "Menu bg.png";
    public static final  String gameSelectBG = "GameSelect bg.png";
    public static final  String testSelectBG = "testSelect BG.png";
    public static final  String pixWhite = "pixes/pixWhite.png";
    public static final  String pixBlack = "pixes/pixBlack.png";

    public static final String jumperPreview = "preview/jumperPreview.png";
    public static final String hophopPreview = "preview/hophopPreview.png";

    public static final String btnBlue = "huds/test/btnBlue.png";
    public static final String btnRed = "huds/test/btnRed.png";
    public static final String btnPurple = "huds/test/btnPurple.png";
    public static final String btnGreen = "huds/test/btnGreen.png";
    public static final String testPanel = "huds/test/testPanel.png";

    public static final String sndClick = "sounds/click.wav";
    public static final String sndCollect = "sounds/collect.wav";
    public static final String sndWrong = "sounds/wrong.mp3";
    public static final String sndCorrect = "sounds/correct.wav";
    public static final String sndHit = "sounds/hit.wav";
    public static final String sndJump = "sounds/jump.wav";
    public static final String sndHighjump = "sounds/highjump.wav";
    public static final String mscMusic = "sounds/music.mp3";

    public Array<Soru> test1;
    public Array<Soru> test2;
    public AssetManager assetManager;

    public void create(){
        assetManager = new AssetManager();
        loadJumperAssests();
        loadFonts();

        test1 = new Array<Soru>();
        test2 = new Array<Soru>();

        assetManager.load(pixWhite,Texture.class);
    }

    private void loadFonts() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class,new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class,".ttf",new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter btnFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        btnFont.fontFileName = fntBlow;
        btnFont.fontParameters.size = 40;
        btnFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        btnFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        btnFont.fontParameters.characters = characters;
        assetManager.load(fntBlow, BitmapFont.class, btnFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter boldFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        boldFont.fontFileName = fntBold;
        boldFont.fontParameters.size = 40;
        boldFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        boldFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        boldFont.fontParameters.characters = characters;
        assetManager.load(fntBold, BitmapFont.class, boldFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter cooperFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        cooperFont.fontFileName = fntCooper;
        cooperFont.fontParameters.size = 48;
        cooperFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        cooperFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        cooperFont.fontParameters.characters = characters;
        assetManager.load(fntCooper, BitmapFont.class, cooperFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter testFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        testFont.fontFileName = fntTest;
        testFont.fontParameters.size = 28;
        testFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        testFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        testFont.fontParameters.characters = characters;
        assetManager.load(fntTest, BitmapFont.class, testFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter arialFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        arialFont.fontFileName = fntArial;
        arialFont.fontParameters.size = 22;
        //arialFont.fontParameters.minFilter = Texture.TextureFilter.Linear;
        arialFont.fontParameters.magFilter = Texture.TextureFilter.Linear;
        arialFont.fontParameters.characters = characters;
        assetManager.load(fntArial, BitmapFont.class, arialFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter cooper2Font = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        cooper2Font.fontFileName = fntCooper;
        cooper2Font.fontParameters.size = 48;
        cooper2Font.fontParameters.color = Color.YELLOW;
        cooper2Font.fontParameters.borderColor = Color.BLACK;
        cooper2Font.fontParameters.borderWidth = 3;
        cooper2Font.fontParameters.minFilter = Texture.TextureFilter.Linear;
        cooper2Font.fontParameters.magFilter = Texture.TextureFilter.MipMapNearestLinear;
        cooper2Font.fontParameters.characters = characters;
        assetManager.load(fntCooper2, BitmapFont.class, cooper2Font);
    }

    private void loadJumperAssests() {
        assetManager.load(bunny_hurt, Texture.class);
        assetManager.load(bunny_jump, Texture.class);
        assetManager.load(bunny_ready, Texture.class);
        assetManager.load(bunny2_hurt, Texture.class);
        assetManager.load(bunny2_jump, Texture.class);
        assetManager.load(bunny2_ready, Texture.class);
        assetManager.load(fly_enemy, Texture.class);
        assetManager.load(ground_grass, Texture.class);
        assetManager.load(ground_grass_broken, Texture.class);
        assetManager.load(jumper_bg, Texture.class);
        assetManager.load(carrot,Texture.class);
        assetManager.load(spring,TextureAtlas.class);
        assetManager.load(bunny,Texture.class);
        assetManager.load(bunny2,Texture.class);
        assetManager.load(olcek,Texture.class);
        assetManager.load(btnNormal,Texture.class);
        assetManager.load(btnHover,Texture.class);
        assetManager.load(btnLocked,Texture.class);
        assetManager.load(lifes,Texture.class);
        assetManager.load(musicOn,Texture.class);
        assetManager.load(musicOff,Texture.class);
        assetManager.load(audioOn,Texture.class);
        assetManager.load(audioOff,Texture.class);
        assetManager.load(help,Texture.class);
        assetManager.load(leadboard,Texture.class);
        assetManager.load(gameSelectBG,Texture.class);
        assetManager.load(home,Texture.class);
        assetManager.load(hophopPreview,Texture.class);
        assetManager.load(jumperPreview,Texture.class);
        assetManager.load(menuBG,Texture.class);
        assetManager.load(pixBlack,Texture.class);
        assetManager.load(pause,Texture.class);
        assetManager.load(resumeNormal,Texture.class);
        assetManager.load(resumeHover,Texture.class);
        assetManager.load(homeHover,Texture.class);
        assetManager.load(homeNormal,Texture.class);
        assetManager.load(btnBlue,Texture.class);
        assetManager.load(btnRed,Texture.class);
        assetManager.load(btnGreen,Texture.class);
        assetManager.load(btnPurple,Texture.class);
        assetManager.load(testPanel,Texture.class);
        assetManager.load(gameOverPanel,Texture.class);
        assetManager.load(backNormal,Texture.class);
        assetManager.load(backHover,Texture.class);
        assetManager.load(restartNormal,Texture.class);
        assetManager.load(restartHover,Texture.class);
        assetManager.load(backPurple,Texture.class);
        assetManager.load(treeUi,TextureAtlas.class);
        assetManager.load(emojies,TextureAtlas.class);
        assetManager.load(testSelectBG,Texture.class);
        assetManager.load(testSon,Texture.class);
        assetManager.load(light,Texture.class);
        assetManager.load(alertBG,Texture.class);
        assetManager.load(yes,Texture.class);
        assetManager.load(no,Texture.class);
        assetManager.load(sndCollect, Sound.class);
        assetManager.load(sndCorrect, Sound.class);
        assetManager.load(sndWrong, Sound.class);
        assetManager.load(sndHighjump, Sound.class);
        assetManager.load(sndJump, Sound.class);
        assetManager.load(sndHit, Sound.class);
        assetManager.load(sndClick,Sound.class);
        assetManager.load(mscMusic,Music.class);
        assetManager.load(nums,TextureAtlas.class);
    }

    public void setTextureFilters() {
        assetManager.get(lifes,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(btnNormal,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(btnHover,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(btnLocked,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(carrot,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(ground_grass,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(ground_grass_broken,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny2,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny_jump,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny_ready,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny_hurt,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny2_jump,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny2_ready,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(bunny2_hurt,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(olcek,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(help,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(audioOn,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(audioOff,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(musicOn,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(musicOff,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(leadboard,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(home,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(pause,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(homeNormal,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(homeHover,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(resumeHover,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(resumeNormal,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(btnPurple,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(btnRed,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(btnGreen,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(btnBlue,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(gameOverPanel,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(restartNormal,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(restartHover,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(backNormal,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(backHover,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(backPurple,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(testSon,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        for (Texture texture:assetManager.get(Assests.emojies,TextureAtlas.class).getTextures())
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        for (Texture texture:assetManager.get(Assests.nums,TextureAtlas.class).getTextures())
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(alertBG,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(no,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(yes,Texture.class).setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        assetManager.get(Assests.fntArial,BitmapFont.class).getData().markupEnabled = true;
        assetManager.get(Assests.fntCooper,BitmapFont.class).getData().markupEnabled = true;

        assetManager.get(Assests.mscMusic,Music.class).setVolume(0.2f);
    }
}

