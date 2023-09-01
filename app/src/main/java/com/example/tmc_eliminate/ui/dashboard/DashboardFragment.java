package com.example.tmc_eliminate.ui.dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmc_eliminate.GameActivity;
import com.example.tmc_eliminate.MusicServer;
import com.example.tmc_eliminate.R;
import com.example.tmc_eliminate.Utility.LoginUtility;
import com.example.tmc_eliminate.Utility.Utility;
import com.example.tmc_eliminate.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //ranking
        binding.rankingDark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.rankingDark.setAlpha(0f);
                    MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                    Utility.openRanking(getActivity(),(RelativeLayout)getActivity().findViewById(R.id.fragment_menu));
                }else binding.rankingDark.setAlpha(1f);
                return true;
            }
        });
        //user
        binding.userDark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.userDark.setAlpha(0f);
                    MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                    Utility.openUser(getActivity(),(RelativeLayout)getActivity().findViewById(R.id.fragment_menu));
                }else binding.userDark.setAlpha(1f);
                return true;
            }
        });





        //play
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                Utility.animation(binding.play,270,270,1790,2390,400);
                Utility.animation(binding.levelEasy,-1460,270,1092,1092,400);
                Utility.animation(binding.levelHard,-1460,270,1557,1557,400);
                Utility.animation(binding.levelMiddle,2000,270,1325,1325,400);
                Utility.animation(binding.cancel,2000,270,1790,1790,400);

                Utility.animation(binding.ranking,270,270,1050,750,400);
                Utility.animation(binding.user,270,270,1250,900,400);
                Utility.animation(binding.rankingDark,270,270,1050,750,400);
                Utility.animation(binding.userDark,270,270,1250,900,400);
            }
        });
        //cancel
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                Utility.animation(binding.levelEasy,270,-1460,1092,1092,400);
                Utility.animation(binding.levelHard,270,-1460,1557,1557,400);
                Utility.animation(binding.levelMiddle,270,2000,1325,1325,400);
                Utility.animation(binding.cancel,270,2000,1790,1790,400);
                Utility.animation(binding.play,270,270,2390,1790,400);

                Utility.animation(binding.ranking,270,270,750,1050,400);
                Utility.animation(binding.user,270,270,900,1250,400);
                Utility.animation(binding.rankingDark,270,270,750,1050,400);
                Utility.animation(binding.userDark,270,270,900,1250,400);

            }
        });
        //简单难度
        binding.levelEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                Intent intent=new Intent(getActivity(), GameActivity.class);
                intent.putExtra("difficulty",1);
                startActivity(intent);
            }
        });
        //中等难度
        binding.levelMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                Intent intent=new Intent(getActivity(), GameActivity.class);
                intent.putExtra("difficulty",2);
                startActivity(intent);
            }
        });
        //困难难度
        binding.levelHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                Intent intent=new Intent(getActivity(), GameActivity.class);
                intent.putExtra("difficulty",3);
                startActivity(intent);
            }
        });





        return root;
    }






    @Override
    public void onResume(){
        super.onResume();
        Utility.animation(binding.tmce,0,0,-305,295,400);
        Utility.animation(binding.play,270,270,2390,1790,400);

        Utility.animation(binding.ranking,-1460,270,1050,1050,400);
        Utility.animation(binding.user,2000,270,1250,1250,400);
        Utility.animation(binding.rankingDark,-1460,270,1050,1050,400);
        Utility.animation(binding.userDark,2000,270,1250,1250,400);



        SharedPreferences APM = getActivity().getSharedPreferences("account_password_message",MODE_PRIVATE);
        LoginUtility.login_now=APM.getString("last_Login","      未登录");

    }
    @Override
    public void onStop(){
        super.onStop();
        Utility.animation(binding.levelEasy,270,-1460,1092,1092,500);
        Utility.animation(binding.levelHard,270,-1460,1557,1557,500);
        Utility.animation(binding.levelMiddle,270,2000,1325,1325,500);
        Utility.animation(binding.cancel,270,2000,1790,1790,500);
        Utility.animation(binding.play,270,270,2390,1790,500);
        Utility.animation(binding.tmce,0,0,295,-305,400);

        Utility.animation(binding.ranking,270,-1460,1050,1050,400);
        Utility.animation(binding.user,270,2000,1250,1250,400);
        Utility.animation(binding.rankingDark,270,-1460,1050,1050,400);
        Utility.animation(binding.userDark,270,2000,1250,1250,400);
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}