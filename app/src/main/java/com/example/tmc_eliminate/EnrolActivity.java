package com.example.tmc_eliminate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tmc_eliminate.Utility.LoginUtility;

public class EnrolActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);


        //隐藏标题栏
        this.getSupportActionBar().hide();


        ImageView IV_Back = findViewById(R.id.enrol_back);
        EditText ET_Tel = findViewById(R.id.tel);
        EditText ET_Password = findViewById(R.id.password);
        Button B_Enrol = findViewById(R.id.enrol);
        TextView TV_Already = findViewById(R.id.already);

        //返回图标
        IV_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //已有账号
        TV_Already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //注册
        B_Enrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    LoginUtility.Enrol(EnrolActivity.this,ET_Tel,ET_Password);
                    LoginUtility.toast(EnrolActivity.this, "注册成功！");
                    finish();
                }catch(Exception e){
                    LoginUtility.toast(EnrolActivity.this, e.getMessage());
                }
            }
        });


    }

}
