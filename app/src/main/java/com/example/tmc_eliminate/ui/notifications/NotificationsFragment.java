package com.example.tmc_eliminate.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmc_eliminate.GameActivity;
import com.example.tmc_eliminate.MusicServer;
import com.example.tmc_eliminate.R;
import com.example.tmc_eliminate.Utility.LoginUtility;
import com.example.tmc_eliminate.Utility.Utility;
import com.example.tmc_eliminate.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        SharedPreferences APM = getActivity().getSharedPreferences("TMCE",MODE_PRIVATE);
        SharedPreferences.Editor editor = APM.edit();


        //switch_1
        binding.switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicServer.getBGM_flag()) {
                    binding.switch1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_false));
                    MusicServer.setBGM_flag(false);
                    MusicServer.BGM_stop(getActivity());
                }else{
                    binding.switch1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_true));
                    MusicServer.setBGM_flag(true);
                    MusicServer.BGM_play(getActivity(),R.raw.bgm);
                }
                editor.putBoolean("bgm_flag",MusicServer.getBGM_flag());
                editor.putBoolean("sound_flag",MusicServer.getSound_flag());
                editor.apply();
            }
        });
        //switch_2
        binding.switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MusicServer.getSound_flag()) {
                    binding.switch2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_false));
                    MusicServer.setSound_flag(false);
                }else{
                    binding.switch2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_true));
                    MusicServer.setSound_flag(true);
                }
                editor.putBoolean("bgm_flag",MusicServer.getBGM_flag());
                editor.putBoolean("sound_flag",MusicServer.getSound_flag());
                editor.apply();
            }
        });


        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences APM = getActivity().getSharedPreferences("TMCE",MODE_PRIVATE);

        if(LoginUtility.login_now=="      未登录")Utility.toast(getActivity(),"暂未登录，无法显示统计信息",0,0);


        if(MusicServer.getBGM_flag())binding.switch1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_true));
        else  binding.switch1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_false));
        if(MusicServer.getSound_flag())binding.switch2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_true));
        else  binding.switch2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.setup_switch_false));

//        binding.easySuccess.setText(APM.getInt("easy_success",0)+"");
//        binding.easyFail.setText(APM.getInt("easy_fail",0)+"");
//        binding.middleSuccess.setText(APM.getInt("middle_success",0)+"");
//        binding.middleFail.setText(APM.getInt("middle_fail",0)+"");
//        binding.hardSuccess.setText(APM.getInt("hard_success",0)+"");
//        binding.hardFail.setText(APM.getInt("hard_fail",0)+"");
        binding.easySuccess.setText(LoginUtility.getSorFCount(getActivity())[0] +"");
        binding.easyFail.setText(LoginUtility.getSorFCount(getActivity())[3]+"");
        binding.middleSuccess.setText(LoginUtility.getSorFCount(getActivity())[1]+"");
        binding.middleFail.setText(LoginUtility.getSorFCount(getActivity())[4]+"");
        binding.hardSuccess.setText(LoginUtility.getSorFCount(getActivity())[2]+"");
        binding.hardFail.setText(LoginUtility.getSorFCount(getActivity())[5]+"");


        Utility.animation(binding.bg1,70,70,-984,16,300);
        Utility.animation(binding.bg2,70,70,2095,1095,300);
        Utility.t_animation(binding.easySuccess,540,540,-370,630,300);
        Utility.t_animation(binding.easyFail,805,805,-370,630,300);
        Utility.t_animation(binding.middleSuccess,540,540,-245,755,300);
        Utility.t_animation(binding.middleFail,805,805,-245,755,300);
        Utility.t_animation(binding.hardSuccess,540,540,-120,880,300);
        Utility.t_animation(binding.hardFail,805,805,-120,880,300);
        Utility.animation(binding.switch1,760,760,2585,1585,300);
        Utility.animation(binding.switch2,760,760,2725,1725,300);
    }


    @Override
    public void onStop(){
        super.onStop();

        Utility.animation(binding.bg1,70,70,16,-984,300);
        Utility.animation(binding.bg2,70,70,1095,2095,300);
        Utility.t_animation(binding.easySuccess,540,540,630,-370,300);
        Utility.t_animation(binding.easyFail,805,805,630,-370,300);
        Utility.t_animation(binding.middleSuccess,540,540,755,-245,300);
        Utility.t_animation(binding.middleFail,805,805,755,-245,300);
        Utility.t_animation(binding.hardSuccess,540,540,880,-120,300);
        Utility.t_animation(binding.hardFail,805,805,880,-120,300);
        Utility.animation(binding.switch1,760,760,1585,2585,300);
        Utility.animation(binding.switch2,760,760,1725,2725,300);

    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}