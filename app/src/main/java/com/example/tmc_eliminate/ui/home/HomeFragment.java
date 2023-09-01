package com.example.tmc_eliminate.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmc_eliminate.MusicServer;
import com.example.tmc_eliminate.R;
import com.example.tmc_eliminate.Utility.Utility;
import com.example.tmc_eliminate.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //kc
        binding.kc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.kc.setAlpha(0f);
                    MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                    binding.bg.setImageDrawable(getActivity().getDrawable(R.drawable.frag_kc));
                }else binding.kc.setAlpha(1f);
                return true;
            }
        });
        //dj
        binding.dj.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.dj.setAlpha(0f);
                    MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                    binding.bg.setImageDrawable(getActivity().getDrawable(R.drawable.frag_dj));
                }else binding.dj.setAlpha(1f);
                return true;
            }
        });
        //pd
        binding.pd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.pd.setAlpha(0f);
                    MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                    binding.bg.setImageDrawable(getActivity().getDrawable(R.drawable.frag_pd));
                }else binding.pd.setAlpha(1f);
                return true;
            }
        });
        //lw
        binding.lw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.lw.setAlpha(0f);
                    MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                    binding.bg.setImageDrawable(getActivity().getDrawable(R.drawable.frag_lw));
                }else binding.lw.setAlpha(1f);
                return true;
            }
        });
        //js
        binding.js.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    binding.js.setAlpha(0f);
                    MusicServer.sound_play(getActivity(),R.raw.sound_clickbutton);
                    binding.bg.setImageDrawable(getActivity().getDrawable(R.drawable.frag_js));
                }else binding.js.setAlpha(1f);
                return true;
            }
        });




        return root;
    }


    @Override
    public void onResume(){
        super.onResume();
        Utility.animation(binding.bLw,436,436,-385,215,500);

        Utility.animation(binding.bKc,-348,252,50,50,550);
        Utility.animation(binding.bDj,1220,620,50,50,550);

        Utility.animation(binding.bJs,1403,803,215,215,550);
        Utility.animation(binding.bPd,-531,69,215,215,550);


        Utility.animation(binding.lw,436,436,-385,215,500);
        Utility.animation(binding.kc,-348,252,50,50,550);
        Utility.animation(binding.dj,1220,620,50,50,550);
        Utility.animation(binding.js,1403,803,215,215,550);
        Utility.animation(binding.pd,-531,69,215,215,550);


        Utility.animation(binding.bg,70,70,1830,133,300);
    }

    @Override
    public void onStop(){
        super.onStop();
        Utility.animation(binding.bLw,436,436,215,-385,500);

        Utility.animation(binding.bKc,252,-348,50,50,550);
        Utility.animation(binding.bDj,620,1220,50,50,550);

        Utility.animation(binding.bJs,803,1403,215,215,550);
        Utility.animation(binding.bPd,69,-531,215,215,550);


        Utility.animation(binding.lw,436,436,215,-385,500);
        Utility.animation(binding.kc,252,-348,50,50,550);
        Utility.animation(binding.dj,620,1220,50,50,550);
        Utility.animation(binding.js,803,1403,215,215,550);
        Utility.animation(binding.pd,69,-531,215,215,550);


        Utility.animation(binding.bg,70,70,133,1830,300);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}