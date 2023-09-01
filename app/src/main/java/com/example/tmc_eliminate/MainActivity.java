package com.example.tmc_eliminate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tmc_eliminate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_App);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        //隐藏标题栏
        this.getSupportActionBar().hide();
    }


    @Override
    public void onResume(){
        super.onResume();

        SharedPreferences APM = getSharedPreferences("TMCE",MODE_PRIVATE);
        MusicServer.setBGM_flag(APM.getBoolean("bgm_flag",true));
        MusicServer.setSound_flag(APM.getBoolean("sound_flag",true));

        //background music
        MusicServer.BGM_play(MainActivity.this,R.raw.bgm);

    }

    @Override
    public void onPause(){
        super.onPause();

        //stop music
        MusicServer.BGM_stop(MainActivity.this);

    }


}