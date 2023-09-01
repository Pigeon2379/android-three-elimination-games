package com.example.tmc_eliminate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tmc_eliminate.Utility.LoginUtility;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginmain);

        //隐藏标题栏
        this.getSupportActionBar().hide();


        ImageView IV_Visible = findViewById(R.id.visible);  //密码可见图标
        ImageView IV_Invisible = findViewById(R.id.invisible);  //密码不可见图标
        Button B_Login = findViewById(R.id.login);  //登录按钮

        EditText ET_account = findViewById(R.id.ET_account);
        TextView T_password = findViewById(R.id.T_password);
        EditText ET_password = findViewById(R.id.ET_password);

        SharedPreferences APM = getSharedPreferences("account_password_message",MODE_PRIVATE);
        SharedPreferences.Editor editor = APM.edit();


        //tel框文本内容改变监听
        ET_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            //tel框内容改变后清空密码
            @Override
            public void afterTextChanged(Editable editable) {
                ET_password.setText("");
                if(ET_account.getText().toString().length()==11){
                    ET_account.clearFocus();
                    ET_password.requestFocus();
                }
            }
        });
        //password框文本内容改变监听
        ET_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(LoginUtility.loginState==1&&ET_password.getText().toString().length()==6){
                    ET_password.clearFocus();
                    //收起软键盘
                    InputMethodManager imm=(InputMethodManager)LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                }
                if(LoginUtility.loginState==1&&ET_password.getText().toString().length()>6){
                    ET_password.setText(ET_password.getText().toString().substring(0,6));
                }
            }
        });

        //密码可见
        IV_Visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IV_Visible.setVisibility(View.INVISIBLE);
                IV_Invisible.setVisibility(View.VISIBLE);
                ET_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                //光标移至末尾
                ET_password.setSelection(ET_password.getText().length());
            }
        });
        //密码不可见
        IV_Invisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IV_Invisible.setVisibility(View.INVISIBLE);
                IV_Visible.setVisibility(View.VISIBLE);
                ET_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                //光标移至末尾
                ET_password.setSelection(ET_password.getText().length());
            }
        });


        //登录
        B_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (LoginUtility.Login(LoginActivity.this,APM,editor,ET_account,ET_password)) {
                        LoginUtility.toast(LoginActivity.this, "登录成功！");
                        //startActivity(new Intent(LoginMainActivity.this, MainActivity.class));
                        finish();
                        //页面跳转后清空密码
                        LoginUtility.clearPassword(ET_account,ET_password);
                    } else {
                        LoginUtility.toast(LoginActivity.this, "密码错误");
                    }
                }catch(Exception e){
                    LoginUtility.toast(LoginActivity.this, e.getMessage());
                }
            }
        });
        //注册
        findViewById(R.id.enrol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,EnrolActivity.class));
                //页面跳转后清空密码
                LoginUtility.clearPassword(ET_account,ET_password);
            }
        });
        //返回
        findViewById(R.id.login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        EditText ET_account=findViewById(R.id.ET_account);
        EditText ET_password =findViewById(R.id.ET_password);

        SharedPreferences APM = getSharedPreferences("account_password_message",MODE_PRIVATE);
        SharedPreferences.Editor editor = APM.edit();

        LoginUtility.loginState = 0;
        //上次登录信息
        //String last_Login = APM.getString("last_Login","");
        //String last_Login =LoginUtility.login_now;

//        if(!last_Login.equals("null")){
//            ET_account.setText(last_Login);
//            if (APM.getBoolean("isChecked", false)) {
//                LoginUtility.autoFill(LoginActivity.this,ET_account,ET_password);
//            }
//        }


    }


}
