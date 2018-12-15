package com.serhatmerak.cozveoyna.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.serhatmerak.cozveoyna.GameMain;

/**
 * Created by Serhat Merak on 5.03.2018.
 */

public class GameManager {

    static final GameManager ourInstance = new GameManager();
    public static GameManager getInstance() {
        return ourInstance;
    }
    private GameManager() {}

    public GameData gameData;
    public GameMain game;
    private Json json = new Json();
    private FileHandle fileHandle = Gdx.files.local("bin/GameData.serhatmerak");

    public void initializeGameData() {
        if (!fileHandle.exists()) {
            gameData = new GameData();
            //TODO:2 yap
            gameData.setHak(5);
            gameData.setFirstHopHop(true);
            gameData.setFirstJumper(true);
            gameData.setFirstMenu(true);
            gameData.setHgscr_hophop(0);
            gameData.setHgscr_jumper(0);
            gameData.setMusicOpen(true);
            gameData.setSoundOpen(true);
            gameData.setErkek(true);


            saveData();
        } else {
            loadData();
        }
    }

    public void loadData() {
        gameData = json.fromJson(GameData.class, Base64Coder.decodeString(fileHandle.readString()));
    }

    public void saveData() {
        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)), false);
        }
    }



}
