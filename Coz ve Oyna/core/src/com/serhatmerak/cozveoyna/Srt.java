package com.serhatmerak.cozveoyna;

/**
 * Created by Serhat Merak on 19.07.2018.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;



/**
 * Created by Serhat Merak on 19.07.2018.
 */

public class Srt {
    FileHandle fileHandle = Gdx.files.local("srt/got-6-7.srt");
    int sec = +4;
    int min = -2;


    public Srt(){
        create();
    }

    private void create() {
        String text = fileHandle.readString();
        String wordsArray[] = text.split("\\r?\\n");

        String txt = "";
        for (String line:wordsArray) {
            if(line.contains("-->")){
                int firstH = Integer.valueOf(line.substring(0,2));
                int firstM = Integer.valueOf(line.substring(3,5));
                int firstS = Integer.valueOf(line.substring(6,8));
                int firstMs = Integer.valueOf(line.substring(9,12));

                int lastH = Integer.valueOf(line.substring(17,19));
                int lastM = Integer.valueOf(line.substring(20,22));
                int lastS = Integer.valueOf(line.substring(23,25));
                int lastMs = Integer.valueOf(line.substring(26,29));

                firstS += sec;
                lastS += sec;
                firstM += min;
                lastM += min;


                if(firstS > 59){
                    firstS -= 60;
                    firstM++;
                }
                if(firstS < 0) {
                    firstS += 60;
                    firstM--;
                }
                if(firstM < 0) {
                    firstM += 60;
                    firstH--;
                }
                if(firstM > 59){
                    firstM -= 60;
                    firstH++;
                }


                if(lastS > 59){
                    lastS -= 60;
                    lastM++;
                }
                if(lastS < 0) {
                    lastS += 60;
                    lastM--;
                }
                if(lastM < 0) {
                    lastM += 60;
                    lastH--;
                }
                if(lastM > 59){
                    lastM -= 60;
                    lastH++;
                }


                String lineTxt = "";
                if(firstH == 0)
                    lineTxt = "00:";
                else
                    lineTxt ="01:";

                if(firstM < 10)
                    lineTxt +="0";
                lineTxt += firstM + ":";

                if(firstS < 10)
                    lineTxt +="0";
                lineTxt += firstS;

                lineTxt += line.substring(8,17);

                if(lastH == 0)
                    lineTxt += "00:";
                else
                    lineTxt +="01:";

                if(lastM < 10)
                    lineTxt +="0";
                lineTxt += lastM + ":";

                if(lastS < 10)
                    lineTxt +="0";
                lineTxt += lastS + ",";

                lineTxt += line.substring(26,29);


                txt += lineTxt +"\n";

            }else {
                txt += line+"\n";
            }
        }

        fileHandle.writeString(txt,false);

    }
}
