package com.serhatmerak.cozveoyna.helpers;

/**
 * Created by Serhat Merak on 5.03.2018.
 */

public class GameData {

    private int hak;
    private int hgscr_jumper;
    private int hgscr_hophop;

    private boolean isMusicOpen;
    private boolean isSoundOpen;

    private boolean firstMenu;
    private boolean firstJumper;
    private boolean firstHopHop;
    private boolean erkek;

    public int getHak() {
        return hak;
    }

    public int getHgscr_hophop() {
        return hgscr_hophop;
    }

    public int getHgscr_jumper() {
        return hgscr_jumper;
    }

    public boolean isFirstHopHop() {
        return firstHopHop;
    }

    public boolean isFirstJumper() {
        return firstJumper;
    }

    public boolean isFirstMenu() {
        return firstMenu;
    }

    public boolean isMusicOpen() {
        return isMusicOpen;
    }

    public boolean isSoundOpen() {
        return isSoundOpen;
    }

    public void setFirstHopHop(boolean firstHopHop) {
        this.firstHopHop = firstHopHop;
    }

    public void setFirstJumper(boolean firstJumper) {
        this.firstJumper = firstJumper;
    }

    public void setFirstMenu(boolean firstMenu) {
        this.firstMenu = firstMenu;
    }

    public void setHak(int hak) {
        this.hak = hak;
    }

    public void setHgscr_hophop(int hgscr_hophop) {
        this.hgscr_hophop = hgscr_hophop;
    }

    public void setHgscr_jumper(int hgscr_jumper) {
        this.hgscr_jumper = hgscr_jumper;
    }

    public void setMusicOpen(boolean musicOpen) {
        isMusicOpen = musicOpen;
    }

    public void setSoundOpen(boolean soundOpen) {
        isSoundOpen = soundOpen;
    }

    public boolean isErkek() {
        return erkek;
    }

    public void setErkek(boolean erkek) {
        this.erkek = erkek;
    }


}
