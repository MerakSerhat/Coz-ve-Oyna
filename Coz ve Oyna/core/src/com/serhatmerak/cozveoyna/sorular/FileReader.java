package com.serhatmerak.cozveoyna.sorular;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.serhatmerak.cozveoyna.helpers.Assests;

/**
 * Created by Serhat Merak on 11.02.2018.
 */

public class FileReader {
    Array<Soru> sorular;
    private int testId;

    public FileReader(){
        sorular = new Array<Soru>();
        readFile();
    }


    private void readFile() {
        FileHandle handle = Gdx.files.internal("Sorular.txt");
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");
        //Her açma tagında bunlar sıfırlanıcak
        Array<Soru> test = new Array<Soru>();
        Soru soru = new Soru();
        String options[] = new String[4];


        System.out.println(wordsArray[0].length());
        System.out.println(wordsArray[0]);



        for (String line: wordsArray){
            line = line.replace("ï»¿{","");
            line = line.replace("Ä±","ı");
            line = line.replace("ÅŸ","ş");
            line = line.replace("â€œ","\"");
            line = line.replace("Ã¼","ü");
            line = line.replace("Ã¶","ö");
            line = line.replace("ÄŸ","ğ");
            line = line.replace("â€™","'");
            line = line.replace("Ã§","ç");

            System.out.println((wordsArray[0].substring(0,1)));



            if(line.length() > 1 && (line.substring(0,2).equals("{") || line.substring(0,1).equals("{"))){
                testId = Integer.valueOf(line.substring(1,2));
                test.clear();
                sorular.clear();
            }else if (line.substring(0,1).equals("(")){
                soru = new Soru();
                options = new String[4];
            }else if (line.substring(0,1).equals("@")){
                soru.setType(Soru.Type.IMAGE);
                soru.setSoru(line.substring(2,line.length() - 1));
            }else if (line.substring(0,1).equals("$")){
                soru.setType(Soru.Type.TEXT);
                soru.setSoru(line.substring(2,line.length()));
            }else if (line.substring(0,1).equals("A")){
                options[0] = line.substring(2,line.length());
            }else if (line.substring(0,1).equals("B")){
                options[1] = line.substring(2,line.length());
            }else if (line.substring(0,1).equals("C")){
                options[2] = line.substring(2,line.length());
            }else if (line.substring(0,1).equals("D")){
                options[3] = line.substring(2,line.length());
            }else if (line.substring(0,1).equals("=")){
                if(line.substring(1,2).equals("A"))
                    soru.setDogru(0);
                else if(line.substring(1,2).equals("B"))
                    soru.setDogru(1);
                else if(line.substring(1,2).equals("C"))
                    soru.setDogru(2);
                else if(line.substring(1,2).equals("D"))
                    soru.setDogru(3);
            }else if (line.substring(0,1).equals(")")) {
                if(soru.getType() == Soru.Type.TEXT){
                    soru.setOptions(options);
                }

                sorular.add(soru);
            }else if (line.substring(0,1).equals("}")){
                switch (testId){
                    case 1: {
                        Assests.getInstance().test1.addAll(sorular);
                        System.out.println(Assests.getInstance().test1.size);
                    }break;
                    case 2: {
                        Assests.getInstance().test2.addAll(sorular);
                        System.out.println(Assests.getInstance().test2.size);
                    }break;
                }
            }
        }
    }

    public Array<Soru> getSorular() {
        return sorular;
    }

    public void writeSorular(){
/*
        for (Soru soru:sorular) {
            if(soru.getType() == Soru.Type.TEXT){
                System.out.println(soru.getSoru() + " " + soru.getOptions()[0] + " "+ soru.getOptions()[1] + " "+ soru.getOptions()[2] + " "+ soru.getOptions()[3] + " " + soru.getDogru());
            }
        }
*/
    }
}
