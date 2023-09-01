package com.example.tmc_eliminate;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicServer {
    private static MediaPlayer BGM=null;
    private static MediaPlayer sound_effect=null;
    private static Boolean BGM_flag=true;
    private static Boolean sound_flag=true;

    //play BGM
    public static void BGM_play(Context context,int resource){
        if(!BGM_flag)return ;
        BGM_stop(context);
        BGM=MediaPlayer.create(context,resource);
        BGM.setLooping(true);
        BGM.start();
    }

    //stop BGM
    public static void BGM_stop(Context context){
        if(BGM!=null){
            BGM.stop();
            BGM.release();
            BGM=null;
        }
    }

    //play sound effect
    public static void sound_play(Context context,int resource){
        if(!sound_flag)return ;
        if(sound_effect!=null)sound_effect.release();
        sound_effect=MediaPlayer.create(context,resource);
        sound_effect.start();
    }

    public static void sound_play_card(Context context,int resource){
        if(!sound_flag)return ;
        sound_effect=MediaPlayer.create(context,resource);
        sound_effect.start();
    }

    public static Boolean getBGM_flag() {
        return BGM_flag;
    }
    public static void setBGM_flag(Boolean BGM_flag) {
        MusicServer.BGM_flag = BGM_flag;
    }
    public static Boolean getSound_flag() {
        return sound_flag;
    }
    public static void setSound_flag(Boolean sound_flag) {
        MusicServer.sound_flag = sound_flag;
    }
}
