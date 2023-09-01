package com.example.tmc_eliminate.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.tmc_eliminate.MyDataBaseHelper;
import com.example.tmc_eliminate.User;

import java.util.List;
import java.util.Random;

public class LoginUtility {
    //登录方式  0 密码登录  1 验证码登录
    public static int loginState = 0;
    private static MyDataBaseHelper MDBH;
    public static String login_now="      未登录";

    //enrol
    public static Boolean Enrol(Context context, EditText account, EditText password) throws Exception{
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=getUserFromEditText(account,password);
        if(MDBH.insert(userToCV(user))==-1)throw new Exception("该手机号已被注册！");
        return true;
    }
    //login
    public static Boolean Login(Context context, SharedPreferences APM, SharedPreferences.Editor editor, EditText account, EditText password) throws Exception{
        MDBH=MyDataBaseHelper.getInstance(context,1);

        if(!userExistsOrNot(context,account.getText().toString()))throw new Exception("该手机号未注册");
        User user=MDBH.query(account.getText().toString());
        if(user.getPassword().equals(password.getText().toString())){

            //记录last_Login
            editor.putString("last_Login",account.getText().toString());
            LoginUtility.login_now=account.getText().toString();
            editor.apply();
            return true;
        }
        return false;


    }



    //get User from EditText
    public static User getUserFromEditText(EditText account,EditText password) throws Exception{
        if(account.length()!=11)throw new Exception("请输入11位手机号！");
        if(password.length()<8)throw new Exception("密码长度不得小于8位！");
        return new User(account.getText().toString(),password.getText().toString(),0,0,0,0,0,0,0);
    }

    //user exists or not
    public static boolean userExistsOrNot(Context context,String account){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=MDBH.query(account);
        return user.getAccount().equals("null")?false:true;
    }

    //success count plus
    public static void successCountPlus(Context context,int diff){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=MDBH.query(login_now);
        //user.setCount(user.getCount()+1);
        if(diff==1)user.setEcount_s(user.getEcount_s()+1);
        if(diff==2)user.setMcount_s(user.getMcount_s()+1);
        if(diff==3)user.setDcount_s(user.getDcount_s()+1);
        MDBH.update(userToCV(user),"account="+user.getAccount());
    }
    //fail count plus
    public static void failCountPlus(Context context,int diff){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=MDBH.query(login_now);
        //user.setCount(user.getCount()+1);
        if(diff==1)user.setEcount_f(user.getEcount_f()+1);
        if(diff==2)user.setMcount_f(user.getMcount_f()+1);
        if(diff==3)user.setDcount_f(user.getDcount_f()+1);
        MDBH.update(userToCV(user),"account="+user.getAccount());
    }
    //fail count sub
    public static void failCountSub(Context context,int diff){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=MDBH.query(login_now);
        //user.setCount(user.getCount()+1);
        if(diff==1)user.setEcount_f(user.getEcount_f()-1);
        if(diff==2)user.setMcount_f(user.getMcount_f()-1);
        if(diff==3)user.setDcount_f(user.getDcount_f()-1);
        MDBH.update(userToCV(user),"account="+user.getAccount());
    }
    //get success/fail count
    public static int[] getSorFCount(Context context){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=MDBH.query(login_now);
        int[] arr=new int[6];
        arr[0]=user.getEcount_s();
        arr[1]=user.getMcount_s();
        arr[2]=user.getDcount_s();
        arr[3]=user.getEcount_f();
        arr[4]=user.getMcount_f();
        arr[5]=user.getDcount_f();
        return arr;
    }
    //set min game time
    public static void setMinTime(Context context,long time){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=MDBH.query(login_now);
        long t=time/1000;
        if(user.getMin_time()==0||t<user.getMin_time())user.setMin_time((int)t);
        MDBH.update(userToCV(user),"account="+user.getAccount());
    }
    //get user min game time
    public static String getUserMinTime(Context context){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        User user=MDBH.query(login_now);
        long t=user.getMin_time();
        if(t==0)return " 暂无 ";
        long m=t/60;
        long s=t%60;
        if(m<10&&s>=10)return "0"+t/60+":"+t%60;
        else if(m>=10&&s<10)return t/60+":0"+t%60;
        else if(m<10&&s<10)return "0"+t/60+":0"+t%60;
        else return t/60+":"+t%60;

    }


    //get ranking list
    public static User[] getRankingList(Context context){
        MDBH=MyDataBaseHelper.getInstance(context,1);
        List<User> arr=MDBH.getUserRecordList();

        for(int i=0;i<arr.size();i++){
            if(arr.get(i).getMin_time()==0){
                arr.remove(i);
                i--;
            }
        }

        User userArray[]=new User[arr.size()];
        for(int i=0;i<arr.size();i++)userArray[i]=arr.get(i);

        for (int i=0;i<userArray.length-1;i++) {
            for (int j = userArray.length - 1; j > i; j--) {
                if (userArray[j].getMin_time() < userArray[j - 1].getMin_time()) {
                    User t = userArray[j];
                    userArray[j] = userArray[j - 1];
                    userArray[j - 1] = t;
                }
            }
        }
        return userArray;
    }

    //User to ContentValues
    public static ContentValues userToCV(User user){
        ContentValues cv=new ContentValues();
        cv.put("account",user.getAccount());
        cv.put("password",user.getPassword());
        cv.put("ecounts",user.getEcount_s());
        cv.put("mcounts",user.getMcount_s());
        cv.put("dcounts",user.getDcount_s());
        cv.put("ecountf",user.getEcount_f());
        cv.put("mcountf",user.getMcount_f());
        cv.put("dcountf",user.getDcount_f());
        cv.put("time",user.getMin_time());
        return cv;
    }
    //clear password after startActivity
    public static void clearPassword(EditText ET_account,EditText ET_password){
        ET_password.setText("");
        ET_account.clearFocus();
        ET_password.clearFocus();
    }
    //toast
    public static void toast(Context context, String text){
        Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,500);
        toast.show();
    }

}
