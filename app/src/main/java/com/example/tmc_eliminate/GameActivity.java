package com.example.tmc_eliminate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tmc_eliminate.Utility.Utility;


public class GameActivity extends AppCompatActivity {
    private long totaltime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        Utility.clear();
        //获取难度
        int difficulty=getIntent().getIntExtra("difficulty",1);
        Utility.difficulty=difficulty;
        //build
        Utility.build_level(GameActivity.this,(RelativeLayout) findViewById(R.id.RL_game));


        //游戏页面左上角返回（复返）
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicServer.sound_play(GameActivity.this,R.raw.sound_clickbutton);
                finish();
            }
        });
        //游戏页面右上角道具
        findViewById(R.id.dj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicServer.sound_play(GameActivity.this,R.raw.sound_clickbutton);
                Utility.openPropMenu(GameActivity.this,(RelativeLayout) findViewById(R.id.RL_game));
            }
        });



        //隐藏标题栏
        this.getSupportActionBar().hide();
    }

    @Override
    public void onResume(){
        super.onResume();
        Utility.animation(findViewById(R.id.cardslot_blue),-974,26,36,36,500);
        Utility.animation(findViewById(R.id.cardslot_red),1030,30,55,55,500);
        Utility.animation(findViewById(R.id.cardslot),28,28,3020,2020,500);
        Utility.t_animation(findViewById(R.id.delet_time),530,530,-300,119,500);

        Utility.t_animation(findViewById(R.id.time),515,515,-389,30,500);

        Utility.blue=findViewById(R.id.cardslot_blue);
        Utility.red=findViewById(R.id.cardslot_red);
        Utility.TV_delet_time=findViewById(R.id.delet_time);


        //background music
        MusicServer.BGM_play(GameActivity.this,R.raw.bgm);

        //game time
        ((Chronometer)findViewById(R.id.time)).setBase(SystemClock.elapsedRealtime()-totaltime);
        ((Chronometer)findViewById(R.id.time)).start();

        Utility.game_time= ((Chronometer)findViewById(R.id.time));
    }



    @Override
    public void onPause(){
        super.onPause();

        //stop music
        MusicServer.BGM_stop(GameActivity.this);

        //game time
        ((Chronometer)findViewById(R.id.time)).stop();
        totaltime=SystemClock.elapsedRealtime()- ((Chronometer)findViewById(R.id.time)).getBase();

    }




}
