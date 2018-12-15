package com.serhatmerak.cozveoyna.sorular;

/**
 * Created by Serhat Merak on 11.02.2018.
 */

public class Soru {

    private String soru;
    private String[] options;
    private int dogru;

    public enum Type{
        TEXT,
        IMAGE
    }

    private Type type;


    public int getDogru() {
        return dogru;
    }

    public void setDogru(int dogru) {
        this.dogru = dogru;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public void setSoru(String soru) {
        this.soru = soru;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }

    public String getSoru() {
        return soru;
    }
}
