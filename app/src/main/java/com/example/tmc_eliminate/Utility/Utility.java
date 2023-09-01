package com.example.tmc_eliminate.Utility;

import static android.content.Context.MODE_PRIVATE;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import com.example.tmc_eliminate.Card;
import com.example.tmc_eliminate.CardPile;
import com.example.tmc_eliminate.CardSlot;
import com.example.tmc_eliminate.CardType;
import com.example.tmc_eliminate.GameActivity;
import com.example.tmc_eliminate.LoginActivity;
import com.example.tmc_eliminate.MainActivity;
import com.example.tmc_eliminate.MusicServer;
import com.example.tmc_eliminate.R;
import com.example.tmc_eliminate.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {
    public static int[] prop={1,1,1,1,1};  //道具可使用次数
    private static int[] last={0,0,0};     //上次点击卡牌的坐标，深度
    public static int difficulty=1;        //难度参数 1:easy 2:middle 3:hard

    private static int propMenu=0;         //道具菜单flag
    private static ImageView dark=null;
    private static ImageView reel_1=null;
    private static ImageView reel_2=null;
    private static ImageView prop_1=null;
    private static ImageView prop_2=null;
    private static ImageView prop_3=null;
    private static ImageView prop_4=null;

    public static int deletTime=0;         //三消次数
    public static ImageView blue=null;
    public static ImageView red=null;
    public static TextView TV_delet_time=null;

    private static ImageView bg=null;
    private static ImageView close=null;
    private static ImageView msg=null;
    private static ImageView change_user=null;
    private static ImageView change_user_dark=null;

    public static Chronometer game_time=null;
    private static long totaltime=0;

    private static TextView now_account=null;  //当前登录账户
    private static TextView now_account_mintime=null;   //当前登录账号的最短通关时间


    //build
    public static void build_level(Context context,RelativeLayout layout){
        if(difficulty==1)Utility.level_easy(context,layout);
        else if(difficulty==2)Utility.level_middle(context,layout);
        else if(difficulty==3)Utility.level_hard(context,layout);
    }

    //get time
    public static String getTime(){
        long t=totaltime/1000;
        long m=t/60;
        long s=t%60;
        if(m<10&&s>=10)return "0"+t/60+":"+t%60;
        else if(m>=10&&s<10)return t/60+":0"+t%60;
        else if(m<10&&s<10)return "0"+t/60+":0"+t%60;
        else return t/60+":"+t%60;
    }

    //get user min time
    public static String getUserMinTime(User user){
        long t=user.getMin_time();
        long m=t/60;
        long s=t%60;
        if(m<10&&s>=10)return "0"+t/60+":"+t%60;
        else if(m>=10&&s<10)return t/60+":0"+t%60;
        else if(m<10&&s<10)return "0"+t/60+":0"+t%60;
        else return t/60+":"+t%60;
    }

    //测试关卡
//    public static void level_test(Context context,RelativeLayout layout){
//        Random random=new Random();
//        int[] level_1_type={0,0,0};
//        int[] typeCount={6,6,6};
//        int type;
//        //随机挑选三种不同卡牌
//        while(true){
//            for(int i=0;i<3;i++)level_1_type[i]=random.nextInt(14);
//            if(level_1_type[0]!=level_1_type[1]&&level_1_type[0]!=level_1_type[2]&&level_1_type[1]!=level_1_type[2])break;
//        }
//        //三种卡牌每种6张打乱放入1，2层
//        for(int j=0;j<2;j++){
//            for(int i=0;i<9;i++){
//                while(true){
//                    type=random.nextInt(14);
//                    if(type==level_1_type[0]&&typeCount[0]>0){
//                        typeCount[0]--;
//                        break;
//                    }else if(type==level_1_type[1]&&typeCount[1]>0){
//                        typeCount[1]--;
//                        break;
//                    }else if(type==level_1_type[2]&&typeCount[2]>0){
//                        typeCount[2]--;
//                        break;
//                    }
//                }
//                Utility.createCard(context,layout,30,30,j+1,type);
//            }
//        }
//        randomPlaceOnTheSameFloor((ArrayList) CardPile.lists[1],-1);
//        randomPlaceOnTheSameFloor((ArrayList) CardPile.lists[2],0);
//        //覆盖检测
//        CardPile.dark(context);
//    }

    //打开排行榜
    public static void openRanking(Context context,RelativeLayout layout){
        dark = new ImageView(context);
        dark.setImageDrawable(context.getResources().getDrawable(R.drawable.dark));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(0, 200, 0, 0);
        params.width = 1500;
        params.height = 2000;
        layout.addView(dark, params);
        dark.setAlpha(0);
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        bg= new ImageView(context);
        bg.setImageDrawable(context.getResources().getDrawable(R.drawable.rank_bg));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(50, 250, 0, 0);
        params.width = 1100;
        params.height = 1800;
        layout.addView(bg, params);

        close= new ImageView(context);
        close.setImageDrawable(context.getResources().getDrawable(R.drawable.close));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(900, 420, 0, 0);
        params.width = 100;
        params.height = 100;
        layout.addView(close, params);

        //左上角提示信息
        msg= new ImageView(context);
        msg.setImageDrawable(context.getResources().getDrawable(R.drawable.msg));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(200, 430, 0, 0);
        params.width = 100;
        params.height = 100;
        layout.addView(msg, params);

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast(context,"排行榜仅记录中等难度最快通关排名",0,0);
            }
        });


        List<TextView> tvlist=new ArrayList<TextView>();
        int x,y;
        User arr[]=LoginUtility.getRankingList(context);
        for(int i=0;i<arr.length&&i<10;i++){

            TextView account=new TextView(context);
            TextView time=new TextView(context);
            account.setText(arr[i].getAccount());
            account.setTextSize(17);
            account.setTextColor(context.getResources().getColor(R.color.black));
            time.setText(getUserMinTime(arr[i]));
            time.setTextSize(17);
            time.setTextColor(context.getResources().getColor(R.color.black));
            tvlist.add(account);
            tvlist.add(time);

            x=400;
            y=740+i*117;
            params=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,600);
            params.setMargins(x,y,0,0);
            params.width=1000;
            params.height=1000;
            (layout).addView(account,params);

            x=832;
            params=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,600);
            params.setMargins(x,y,0,0);
            params.width=1000;
            params.height=1000;
            (layout).addView(time,params);

        }




        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dark.setVisibility(View.INVISIBLE);
                bg.setVisibility(View.INVISIBLE);
                close.setVisibility(View.INVISIBLE);
                msg.setVisibility(View.INVISIBLE);

                for(TextView tv:tvlist){
                    tv.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
    //打开个人信息
    public static void openUser(Context context,RelativeLayout layout){
        dark = new ImageView(context);
        dark.setImageDrawable(context.getResources().getDrawable(R.drawable.dark));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(0, 200, 0, 0);
        params.width = 1500;
        params.height = 2000;
        layout.addView(dark, params);
        dark.setAlpha(0);
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        bg= new ImageView(context);
        bg.setImageDrawable(context.getResources().getDrawable(R.drawable.user_bg));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(50, 250, 0, 0);
        params.width = 1100;
        params.height = 1800;
        layout.addView(bg, params);

        close= new ImageView(context);
        close.setImageDrawable(context.getResources().getDrawable(R.drawable.close));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(900, 850, 0, 0);
        params.width = 100;
        params.height = 100;
        layout.addView(close, params);

        msg= new ImageView(context);
        msg.setImageDrawable(context.getResources().getDrawable(R.drawable.msg));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(200, 850, 0, 0);
        params.width = 100;
        params.height = 100;
        layout.addView(msg, params);

        //用户页面信息
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast(context,"最快通关时间记录用户在\n中等难度的最快通关时间",0,0);
            }
        });

        now_account= new TextView(context);
        now_account.setTextSize(18);
        now_account.setTextColor(context.getResources().getColor(R.color.black));
        now_account.setText(LoginUtility.login_now);
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(670, 1055, 0, 0);
        params.width = 600;
        params.height = 200;
        layout.addView(now_account, params);

        now_account_mintime= new TextView(context);
        now_account_mintime.setTextSize(22);
        now_account_mintime.setTextColor(context.getResources().getColor(R.color.black));
        //now_account_mintime.setText("00:00");
        now_account_mintime.setText(LoginUtility.getUserMinTime(context));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(760, 1175, 0, 0);
        params.width = 600;
        params.height = 200;
        layout.addView(now_account_mintime, params);

        change_user= new ImageView(context);
        change_user.setImageDrawable(context.getResources().getDrawable(R.drawable.change_user));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(310, 1290, 0, 0);
        params.width = 600;
        params.height = 200;
        layout.addView(change_user, params);

        change_user_dark= new ImageView(context);
        change_user_dark.setImageDrawable(context.getResources().getDrawable(R.drawable.frag_menu_dark));
        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
        params.setMargins(310, 1290, 0, 0);
        params.width = 600;
        params.height = 200;
        change_user_dark.setAlpha(0f);
        layout.addView(change_user_dark, params);

        change_user_dark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    change_user_dark.setAlpha(0f);
                    MusicServer.sound_play(context,R.raw.sound_clickbutton);
                    context.startActivity(new Intent(context, LoginActivity.class));
                    close.callOnClick();
                }else change_user_dark.setAlpha(1f);
                return true;
            }
        });

        //关闭用户
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dark.setVisibility(View.INVISIBLE);
                bg.setVisibility(View.INVISIBLE);
                close.setVisibility(View.INVISIBLE);
                msg.setVisibility(View.INVISIBLE);
                change_user.setVisibility(View.INVISIBLE);
                change_user_dark.setVisibility(View.INVISIBLE);
                now_account.setVisibility(View.INVISIBLE);
                now_account_mintime.setVisibility(View.INVISIBLE);
            }
        });

    }



    //简单难度
    public static void level_easy(Context context,RelativeLayout layout) {
        Random random=new Random();
        int depth=random.nextInt(2)+2;  //level_easy 层数

        for(int dp=1;dp<=depth;dp++)randomCardLayout(context,layout,dp, random.nextInt(5)+1);
        //填补缺失卡牌
        fillingMissing(context,layout);
        CardPile.dark(context);
    }
    //中等难度
    public static void level_middle(Context context,RelativeLayout layout) {
        Random random=new Random();
        int depth=random.nextInt(2)+4;  //level_middle 层数

        for(int dp=1;dp<=depth;dp++)randomCardLayout(context,layout,dp, random.nextInt(5)+1);
        //填补缺失卡牌
        fillingMissing(context,layout);
        CardPile.dark(context);
    }
    //困难难度
    public static void level_hard(Context context,RelativeLayout layout) {
        Random random=new Random();
        int depth=random.nextInt(2)+7;  //level_hard 层数

        for(int dp=1;dp<=depth;dp++)randomCardLayout(context,layout,dp, random.nextInt(5)+1);
        //填补缺失卡牌
        fillingMissing(context,layout);
        CardPile.dark(context);
    }


    //随机摆放样式
    public static void randomCardLayout(Context context,RelativeLayout layout,int Depth,int placeType){
        switch(placeType){
            case 1:
                for(int i=0;i<36;i++)Utility.createCard(context,layout,30,30,Depth,-1);
                randomPlaceOnTheSameFloor((ArrayList) CardPile.lists[Depth],1);
                break;
            case 2:
                for(int i=0;i<36;i++)Utility.createCard(context,layout,30,30,Depth,-1);
                randomPlaceOnTheSameFloor((ArrayList) CardPile.lists[Depth],2);
                break;
            case 3:
                for(int i=0;i<36;i++)Utility.createCard(context,layout,30,30,Depth,-1);
                randomPlaceOnTheSameFloor((ArrayList) CardPile.lists[Depth],3);
                break;
            case 4:
                for(int i=0;i<9;i++)Utility.createCard(context,layout,30,30,Depth,-1);
                randomPlaceOnTheSameFloor((ArrayList) CardPile.lists[Depth],4);
                break;
            case 5:
                for(int i=0;i<24;i++)Utility.createCard(context,layout,30,30,Depth,-1);
                randomPlaceOnTheSameFloor((ArrayList) CardPile.lists[Depth],5);
                break;
        }
    }
    //同层卡牌摆放样式
    public static void randomPlaceOnTheSameFloor(ArrayList cardArr,int placeType){
        //Collections.shuffle(cardArr);
        int x,y,i;
        switch(placeType){
            case -1:  //level_test 第一层
                x=300;
                y=810;
                for(i=0;i<9;i++){
                    moveOneCardToXY(((Card)cardArr.get(i)).getImage(),x,y,500);
                    if(x==740){
                        x=80;
                        y+=280;
                    }
                    x+=220;
                }
                break;
            case 0:  //level_test 第二层
                x=300;
                y=880;
                for(i=0;i<6;i++){
                    moveOneCardToXY(((Card)cardArr.get(i)).getImage(),x,y,500);
                    if(x==740){
                        x=80;
                        y+=280;
                    }
                    x+=220;
                }
                moveOneCardToXY(((Card)cardArr.get(i)).getImage(),300,1410,500);
                moveOneCardToXY(((Card)cardArr.get(++i)).getImage(),520,1410,500);
                moveOneCardToXY(((Card)cardArr.get(++i)).getImage(),740,1410,500);
                break;
            case 1:  //方形
                i=0;
                for(int j=0;j<6;j++){
                    for(int k=0;k<6;k++){
                        x=170+k*140;
                        y=650+j*140;
                        moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                    }
                }
                break;
            case 2:  //菱形
                i=0;
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),100,930,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),940,930,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),450,440,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),590,440,500);
                for(int j=0;j<3;j++){  //3
                    x=380+j*140;
                    y=580;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                for(int j=0;j<4;j++){  //4
                    x=310+j*140;
                    y=720;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                for(int j=0;j<5;j++){  //5
                    x=240+j*140;
                    y=860;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),310,1000,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),450,1000,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),590,1000,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),730,1000,500);
                for(int j=0;j<5;j++){  //5
                    x=240+j*140;
                    y=1140;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }

                for(int j=0;j<4;j++){  //4
                    x=310+j*140;
                    y=1280;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                for(int j=0;j<3;j++){ //3
                    x=380+j*140;
                    y=1420;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),450,1560,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),590,1560,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),100,1070,500);
                moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),940,1070,500);
                break;
            case 3:  //四个9x9
                i=0;
                for(int j=0;j<3;j++){  //左上
                    for(int k=0;k<3;k++){
                        x=100+k*140;
                        y=580+j*140;
                        moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                    }
                }
                for(int j=0;j<3;j++){  //右上
                    for(int k=0;k<3;k++){
                        x=660+k*140;
                        y=580+j*140;
                        moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                    }
                }
                for(int j=0;j<3;j++){  //左下
                    for(int k=0;k<3;k++){
                        x=100+k*140;
                        y=1140+j*140;
                        moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                    }
                }
                for(int j=0;j<3;j++){  //右下
                    for(int k=0;k<3;k++){
                        x=660+k*140;
                        y=1140+j*140;
                        moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                    }
                }
                break;
            case 4:  //中间的9x9
                i=0;
                for(int j=0;j<3;j++){  //中间
                    for(int k=0;k<3;k++){
                        x=380+k*140;
                        y=860+j*140;
                        moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                    }
                }
                break;
            case 5: //方框
                i=0;
                for(int j=0;j<7;j++){  //上
                    x=100+j*140;
                    y=560;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                for(int j=0;j<5;j++){  //左
                    x=100;
                    y=700+j*140;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                for(int j=0;j<5;j++){  //右
                    x=940;
                    y=700+j*140;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                for(int j=0;j<7;j++){  //下
                    x=100+j*140;
                    y=1400;
                    moveOneCardToXY(((Card)cardArr.get(i++)).getImage(),x,y,500);
                }
                break;
        }
    }
    //填补缺失卡牌
    public static void fillingMissing(Context context,RelativeLayout layout){
        boolean flag=true;
        int x,x1,y,i,dp=11,type;
        Random random=new Random();
        //int c[]=new int[14];
        //for(i=0;i<c.length;i++)c[i]=cardCount[i];
        int[] c=CardPile.getCardCountArr();
        for(i=0;i<c.length;i++){
            c[i]=(3-c[i]%3)%3;
        }
        i=0;
        while(cardCountTotal(c)>0){
            x=50+i*9;
            x1=1000-i*9;
            y=1730;
            while(true){
                type=random.nextInt(14);
                if(c[type]>0)break;
            }
            c[type]--;
            ImageView image=Utility.createCard(context,layout,30,30,dp,type);

            if(flag)moveOneCardToXY(image,x,y,500);
            else moveOneCardToXY(image,x1,y,500);

            flag=!flag;
            dp++;
            i++;
        }
    }
    //需填补卡牌总数
    public static int cardCountTotal(int[] c){
        int result=0;
        for(int i=0;i<c.length;i++)result+=c[i];
        return result;
    }


    //创建单张卡牌
    public static ImageView createCard(Context context, RelativeLayout layout, int left, int top, int Depth, int type){
        ImageView image=randomCard(context,type);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,600);
        params.setMargins(left,top,0,0);
        params.width=160;
        params.height=160;
        //创建的卡牌添加到卡堆
        CardPile.add(new Card(getImageCardType(image),image,Depth),Depth);
        layout.addView(image,params);


        //image长按事件
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MusicServer.sound_play(context,R.raw.sound_clickbutton);

                ImageView introduce = new ImageView(context);
                if(getImageCardType(image)==CardType.BQ)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.bq_intr));
                else if(getImageCardType(image)==CardType.HFM)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.hfm_intr));
                else if(getImageCardType(image)==CardType.LDY)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.ldy_intr));
                else if(getImageCardType(image)==CardType.LSZ)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.lsz_intr));
                else if(getImageCardType(image)==CardType.SSM)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.ssm_intr));
                else if(getImageCardType(image)==CardType.WWY)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.wwy_intr));
                else if(getImageCardType(image)==CardType.ZZJ)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.zzj_intr));
                else if(getImageCardType(image)==CardType.BCGM)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.bcgm_intr));
                else if(getImageCardType(image)==CardType.JYJ)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.jyj_intr));
                else if(getImageCardType(image)==CardType.NJ)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.nj_intr));
                else if(getImageCardType(image)==CardType.PWL)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.pwl_intr));
                else if(getImageCardType(image)==CardType.QJYF)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.qjyf_intr));
                else if(getImageCardType(image)==CardType.SHL)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.shl_intr));
                else if(getImageCardType(image)==CardType.TR)introduce.setImageDrawable(context.getResources().getDrawable(R.drawable.tr_intr));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                params.setMargins(0, 0, 0, 0);
                params.width = 8000;
                params.height = 8000;
                layout.addView(introduce, params);

                TextView name=new TextView(context);
                name.setTextSize(20);
                if(getImageCardType(image)==CardType.BQ)name.setText("扁鹊");
                else if(getImageCardType(image)==CardType.HFM)name.setText("皇甫谧");
                else if(getImageCardType(image)==CardType.LDY)name.setText("李东垣");
                else if(getImageCardType(image)==CardType.LSZ)name.setText("李时珍");
                else if(getImageCardType(image)==CardType.SSM)name.setText("孙思邈");
                else if(getImageCardType(image)==CardType.WWY)name.setText("王惟一");
                else if(getImageCardType(image)==CardType.ZZJ)name.setText("张仲景");
                else if(getImageCardType(image)==CardType.BCGM)name.setText("《本草纲目》");
                else if(getImageCardType(image)==CardType.JYJ)name.setText("《针灸甲乙经》");
                else if(getImageCardType(image)==CardType.NJ)name.setText("《难经》");
                else if(getImageCardType(image)==CardType.PWL)name.setText("《脾胃论》");
                else if(getImageCardType(image)==CardType.QJYF)name.setText("《千金要方》");
                else if(getImageCardType(image)==CardType.SHL)name.setText("《伤寒杂病论》");
                else if(getImageCardType(image)==CardType.TR){
                    name.setText("《新铸铜人腧穴针灸图经》");
                    name.setTextSize(13);
                }
                name.setTextColor(context.getResources().getColor(R.color.black));
                params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                params.setMargins(470, 900, 0, 0);
                params.width = 8000;
                params.height = 8000;
                layout.addView(name, params);

                TextView text=new TextView(context);
                if(getImageCardType(image)==CardType.BQ)text.setText("见于我国医史上反巫兴医最早\n的倡导者。扁鹊行医的毕生周\n游列国，广泛吸取古代和民间\n医学经验。并最早使用艾灸法\n，是我国针灸疗法的奠基人之\n一。他精湛外科术和“毒酒”麻\n醉剂的应用,是我国外科及麻醉\n术之鼻祖。");
                else if(getImageCardType(image)==CardType.HFM)text.setText("他在总结、吸收古典医学著作\n精华的基础上，对中国古老的\n针灸经络穴位学说进行了科学\n的归类整理，使之发展成为系\n统的科学，矗起了一座千年丰\n碑。奠定了针灸学科理论基础\n，对针灸学以至整个医学事业\n的发展作出了不可磨灭的贡献。");
                else if(getImageCardType(image)==CardType.LDY)text.setText("李东垣《脾胃论》的问世标志\n着脾胃学说的创立，从而提升\n了中医学理论和临床诊断方法\n的指导思想，开辟了中医学认\n识、预防、治疗疾病的新途径\n与方法，在后世医学发展中做\n出了杰出的贡献。");
                else if(getImageCardType(image)==CardType.LSZ)text.setText("李时珍终其一生在医学研究上\n，为了修改明朝以前的关于本\n草记载的错误，和为了完整本\n草的记载，李时珍开始了数十\n载的实地调查。李时珍不仅发\n现了许多以前未被发现的药草\n，还又搞清楚了许多药物的疑\n难之处。");
                else if(getImageCardType(image)==CardType.SSM)text.setText("在药物学方面，他总结了前代\n本草著述，重视“地道”药材，\n强调药物的栽培、采集、炮制\n、管理、贮藏方法。他很讲究\n药物的实际效果，反对滥用贵\n重药品。为了提高药物疗效，\n他提倡自种自采和亲自动手炮\n制。");
                else if(getImageCardType(image)==CardType.WWY)text.setText("王惟一精于针灸，曾奉皇帝命\n令，订正廖误，考订针灸著作\n。他按人形绘制人体正面、侧\n面图，标明腧穴精确位置，搜\n采古今临床经验，汇集诸家针\n灸理论，采用按经络和部位相\n结合的腧穴排列方法，既使人\n了解经络系统，又便于临证取\n穴需要。");
                else if(getImageCardType(image)==CardType.ZZJ)text.setText("张仲景的最大贡献在于创立了\n中医学临床诊疗辨证论治体系\n，他以六经论伤寒、脏腑论杂\n病、三因类病因、辨证寓八纲\n、治则述八法，证因脉治，理\n法方药，融为一体，垂法后世\n。");
                else if(getImageCardType(image)==CardType.BCGM)text.setText("分为52卷，190多万字，收录\n了药物1892种，附有药图110\n0多幅，记载了方剂11096首。\n主要介绍历代诸家本草及中药\n基本理论等内容,本书为本草学\n集大成之作，促进了本草学的\n进一步发展。");
                else if(getImageCardType(image)==CardType.JYJ)text.setText("《针灸甲乙经》类集《素问》\n、《灵枢》之论，总结出诸\n疾之病因、病机、治则、复采\n《明堂》腧穴、针法、灸法、\n乃贯通三部中医经典之理论与\n实践于一书，分12卷，成为最\n早的，最权威的针灸学经典著\n作，简称《甲乙经》");
                else if(getImageCardType(image)==CardType.NJ)text.setText("《难经》是中医现存较早的经\n典著作。《难经》之“难”字，\n有“问难”或“疑难”之义。全书\n共八十一难，采用问答方式，\n探讨和论述了中医的一些理论\n问题，内容包括脉诊、经络、\n脏腑、阴阳、病因、病机、营\n卫、腧穴、针刺、病证等方面。");
                else if(getImageCardType(image)==CardType.PWL)text.setText("《脾胃论》共三卷。卷上为基\n本部分，引用大量《内经》原\n文以阐述其脾胃论的主要观点\n和治疗方药。卷中阐述脾胃病\n的具体论治。卷下详述脾胃病\n与天地阴阳、升降浮沉的密切\n关系，并提出多种治疗方法，\n列方60余首，并附方义及服用\n法。");
                else if(getImageCardType(image)==CardType.QJYF)text.setText("该书第一卷为总论，内容包括\n医德、本草、制药等；再后则\n以临床各科辨证施治为主。书\n中所载医论、医方较系统地总\n结了自《 内经》以后至唐初的\n医学成就，是一部科学价值较\n高的著作。");
                else if(getImageCardType(image)==CardType.SHL)text.setText("《伤寒杂病论》 是一部论述外\n感病与内科杂病为主要内容的\n医学典籍，系统地分析了伤寒\n的原因、症状、发展阶段和处\n理方法，创造性地确立了对伤\n寒病的“六经分类”的辨证施治\n原则，奠定了理、法、方、药\n的理论基础。");
                else if(getImageCardType(image)==CardType.TR)text.setText("该书创制了世界上第一个国家\n级经络腧穴文字标准，与此同\n时，在其主持下，铸成针灸铜\n人两具——世界上最早的国家\n级经络穴位形象化标准，堪称\n为价值连国的“国宝奇珍，医中\n神器。”");
                text.setTextColor(context.getResources().getColor(R.color.black));
                params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                params.setMargins(470, 1000, 0, 0);
                params.width = 8000;
                params.height = 8000;
                layout.addView(text, params);


                //close
                ImageView close = new ImageView(context);
                close.setImageDrawable(context.getResources().getDrawable(R.drawable.close));
                params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                params.setMargins(1000, 900, 0, 0);
                params.width = 80;
                params.height = 80;
                layout.addView(close, params);
                //close点击事件
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        introduce.setVisibility(View.INVISIBLE);
                        name.setVisibility(View.INVISIBLE);
                        text.setVisibility(View.INVISIBLE);
                        close.setVisibility(View.INVISIBLE);
                    }
                });

                //禁止
                introduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //打开介绍后禁止点击卡牌、道具、返回等按钮
                    }
                });


                return true;
            }
        });
        //image点击事件
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!image.isEnabled())return ;   //image enabled is false

                MusicServer.sound_play_card(context,R.raw.sound_click);

                last[0]=CardPile.getCard(image).getLeft();
                last[1]=CardPile.getCard(image).getTop();
                last[2]=CardPile.getCard(image).getDepth();

                int x=48+CardSlot.length()*157;  //根据卡槽内卡牌数确定属性动画坐标

                moveOneCardToXY(image,x,2110,300);  //属性动画，从卡堆移动至卡槽

                try {
                    //从卡堆取出卡牌添加到卡槽
                    CardSlot.add(CardPile.delete(image));
                    if(Utility.deletTime>=10){
                        blue.setImageDrawable(context.getDrawable(R.drawable.cardslot_blue));
                        red.setImageDrawable(context.getDrawable(R.drawable.cardslot_red));
                        TV_delet_time.setText("10/10");
                    }else{
                        blue.setImageDrawable(context.getDrawable(R.drawable.cardslot_blue_grey));
                        red.setImageDrawable(context.getDrawable(R.drawable.cardslot_red_grey));
                        TV_delet_time.setText(deletTime+"/10");
                    }
                    //卡堆覆盖检测
                    CardPile.illume(context);
                    CardPile.dark(context);
                }catch(Exception e) {
                    if(e.getMessage().equals("seven_1")&&CardPile.getSpecialCardslotLength()==0){    //红7
                        moveOutAllCardToTopCardslot(context,layout);
                        moveAwaySurface(context,layout);
                        deletTime=0;
                        TV_delet_time.setText("0/10");
                        blue.setImageDrawable(context.getDrawable(R.drawable.cardslot_blue_grey));
                        red.setImageDrawable(context.getDrawable(R.drawable.cardslot_red_grey));
                    }
                    else if(e.getMessage().equals("seven_2")&&CardPile.getSpecialCardslotLength()==0){    //蓝7
                        moveOutAllCardToTopCardslot(context,layout);
                        moveAwaySurface(context,layout);
                        deletTime=0;
                        TV_delet_time.setText("0/10");
                        blue.setImageDrawable(context.getDrawable(R.drawable.cardslot_blue_grey));
                        red.setImageDrawable(context.getDrawable(R.drawable.cardslot_red_grey));
                    }
                    else {   //卡槽满，游戏失败
                        SharedPreferences APM = context.getSharedPreferences("TMCE",MODE_PRIVATE);
                        SharedPreferences.Editor editor = APM.edit();
                        if(difficulty==1){
                            //editor.putInt("easy_fail", APM.getInt("easy_fail",0)+1);
                            LoginUtility.failCountPlus(context,1);
                        }
                        else if(difficulty==2){
                            //editor.putInt("middle_fail", APM.getInt("middle_fail",0)+1);
                            LoginUtility.failCountPlus(context,2);
                        }
                        else if(difficulty==3){
                            //editor.putInt("hard_fail", APM.getInt("hard_fail",0)+1);
                            LoginUtility.failCountPlus(context,3);
                        }
                        editor.apply();


                        //时停
                        game_time.stop();
                        totaltime=SystemClock.elapsedRealtime()- game_time.getBase();


                        //禁用所有卡牌
                        CardPile.enabledAllCard();


                        //bg
                        ImageView bg = new ImageView(context);
                        bg.setImageDrawable(context.getResources().getDrawable(R.drawable.end_bg));
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                        params.setMargins(50, 130, 0, 0);
                        params.width = 1100;
                        params.height = 2000;
                        layout.addView(bg, params);

                        //diff
                        ImageView diff = new ImageView(context);
                        if(difficulty==1)diff.setImageDrawable(context.getResources().getDrawable(R.drawable.end_difficult_easy));
                        else if(difficulty==2)diff.setImageDrawable(context.getResources().getDrawable(R.drawable.end_difficult_middle));
                        else if(difficulty==3)diff.setImageDrawable(context.getResources().getDrawable(R.drawable.end_difficult_hard));
                        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                        params.setMargins(150, 1500, 0, 0);
                        params.width = 1800;
                        params.height = 2000;
                        layout.addView(diff, params);
                        //fail
                        ImageView fail = new ImageView(context);
                        fail.setImageDrawable(context.getResources().getDrawable(R.drawable.fail));
                        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                        params.setMargins(0, 0, 0, 0);
                        params.width = 670;
                        params.height = 670;
                        layout.addView(fail, params);
                        //复活
                        ImageView relive = new ImageView(context);
                        relive.setImageDrawable(context.getResources().getDrawable(R.drawable.reli));
                        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
                        params.setMargins(300, 1200, 0, 0);
                        params.width = 500;
                        params.height = 200;
                        layout.addView(relive, params);
                        //back
                        ImageView back = new ImageView(context);
                        back.setImageDrawable(context.getResources().getDrawable(R.drawable.back));
                        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
                        params.setMargins(300, 1700, 0, 0);
                        params.width = 500;
                        params.height = 200;
                        layout.addView(back, params);
                        //challenge
                        ImageView challenge = new ImageView(context);
                        challenge.setImageDrawable(context.getResources().getDrawable(R.drawable.challenge));
                        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
                        params.setMargins(300, 1450, 0, 0);
                        params.width = 500;
                        params.height = 200;
                        layout.addView(challenge, params);
                        //time
                        TextView time = new TextView(context);
                        time.setText(getTime());
                        time.setTextSize(20);
                        time.setTextColor(context.getResources().getColor(R.color.black));
                        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
                        params.setMargins(300, 1450, 0, 0);
                        params.width = 500;
                        params.height = 200;
                        layout.addView(time, params);

                        //ban
                        ImageView ban = new ImageView(context);
                        ban.setImageDrawable(context.getResources().getDrawable(R.drawable.ban));
                        params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                        params.setMargins(50, 50, 0, 0);
                        params.width = 1100;
                        params.height = 200;
                        layout.addView(ban, params);
                        ban.setAlpha(0);
                        ban.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                        animation(diff, 75, 75, -850, 650, 1500);
                        animation(fail, 265, 265, -800, 330, 1500);
                        animation(relive, 350, 350, 3000, 1300, 1500);
                        animation(back, 350, 350, 3000, 1700, 1500);
                        animation(challenge, 350, 350, 3000, 1500, 1500);
                        t_animation(time,520, 520, -320, 1180, 1500);

                        if (prop[4] <= 0) {
                            //relive.setEnabled(false);
                            relive.setImageDrawable(context.getResources().getDrawable(R.drawable.reli_dark));
                        }

                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MusicServer.sound_play(context,R.raw.sound_clickbutton);

                                Intent intent = new Intent();
                                intent.setClass(context, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                //禁用
                                challenge.setEnabled(false);
                                back.setEnabled(false);
                                relive.setEnabled(false);
                                time.setEnabled(false);

                            }
                        });
                        challenge.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MusicServer.sound_play(context,R.raw.sound_clickbutton);
                                //重进本页面，实现再次挑战
                                Intent intent = new Intent();
                                intent.setClass(context, GameActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("difficulty",difficulty);
                                context.startActivity(intent);
                                //禁用
                                challenge.setEnabled(false);
                                back.setEnabled(false);
                                relive.setEnabled(false);
                                time.setEnabled(false);

                            }
                        });
                        relive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MusicServer.sound_play(context,R.raw.sound_clickbutton);
                                if (prop[4] <= 0){
                                    toast(context,"复活次数已用尽",0,0);
                                    return;
                                }

                                //时间开始流动！
                                game_time.setBase(SystemClock.elapsedRealtime()-totaltime);
                                game_time.start();

                                //if(difficulty==1)editor.putInt("easy_fail", APM.getInt("easy_fail",0)-1);
                                //else if(difficulty==2)editor.putInt("middle_fail", APM.getInt("middle_fail",0)-1);
                                //else if(difficulty==3)editor.putInt("hard_fail", APM.getInt("hard_fail",0)-1);
                                if(difficulty==1){
                                    //editor.putInt("easy_success", APM.getInt("easy_success",0)+1);
                                    LoginUtility.failCountSub(context,1);
                                }
                                else if(difficulty==2){
                                    //editor.putInt("middle_success", APM.getInt("middle_success",0)+1);
                                    LoginUtility.failCountSub(context,2);
                                }
                                else if(difficulty==3){
                                    //editor.putInt("hard_success", APM.getInt("hard_success",0)+1);
                                    LoginUtility.failCountSub(context,3);
                                }
                                editor.apply();

                                challenge.setEnabled(false);
                                back.setEnabled(false);
                                relive.setEnabled(false);
                                time.setEnabled(false);

                                fail.setVisibility(View.INVISIBLE);
                                back.setVisibility(View.INVISIBLE);
                                challenge.setVisibility(View.INVISIBLE);
                                time.setVisibility(View.INVISIBLE);
                                bg.setVisibility(View.INVISIBLE);
                                diff.setVisibility(View.INVISIBLE);
                                relive.setVisibility(View.INVISIBLE);
                                ban.setVisibility(View.INVISIBLE);

                                CardPile.illume(context);
                                CardPile.dark(context);

                                moveOutThreeCardFromCardSlot(context, layout);
                                prop[4]--;
                            }
                        });
                    }

                }

                if(--CardPile.cardPileCount==0){     //牌堆卡牌为0，游戏成功
                    SharedPreferences APM = context.getSharedPreferences("TMCE",MODE_PRIVATE);
                    SharedPreferences.Editor editor = APM.edit();

                    //时停
                    game_time.stop();
                    totaltime=SystemClock.elapsedRealtime()- game_time.getBase();

                    if(difficulty==1){
                        //editor.putInt("easy_success", APM.getInt("easy_success",0)+1);
                        LoginUtility.successCountPlus(context,1);
                    }
                    else if(difficulty==2){
                        //editor.putInt("middle_success", APM.getInt("middle_success",0)+1);
                        LoginUtility.successCountPlus(context,2);
                        //记录最短通关时间
                        LoginUtility.setMinTime(context,Utility.totaltime);
                    }
                    else if(difficulty==3){
                        //editor.putInt("hard_success", APM.getInt("hard_success",0)+1);
                        LoginUtility.successCountPlus(context,3);
                    }
                    editor.apply();



                    //bg
                    ImageView bg = new ImageView(context);
                    bg.setImageDrawable(context.getResources().getDrawable(R.drawable.end_bg));
                    LinearLayout.LayoutParams  params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                    params.setMargins(50, 130, 0, 0);
                    params.width = 1100;
                    params.height = 2000;
                    layout.addView(bg, params);

                    //ban
                    ImageView ban = new ImageView(context);
                    ban.setImageDrawable(context.getResources().getDrawable(R.drawable.ban));
                    params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                    params.setMargins(50, 50, 0, 0);
                    params.width = 1100;
                    params.height = 200;
                    layout.addView(ban, params);
                    ban.setAlpha(0);
                    ban.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

                    //success
                    ImageView success=new ImageView(context);
                    success.setImageDrawable(context.getResources().getDrawable(R.drawable.success));
                    params=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,600);
                    params.setMargins(150,0,0,0);
                    params.width=670;
                    params.height=670;
                    layout.addView(success,params);
                    animation(success,265,265,-800,330,1500);

                    //diff
                    ImageView diff = new ImageView(context);
                    if(difficulty==1)diff.setImageDrawable(context.getResources().getDrawable(R.drawable.end_difficult_easy));
                    else if(difficulty==2)diff.setImageDrawable(context.getResources().getDrawable(R.drawable.end_difficult_middle));
                    else if(difficulty==3)diff.setImageDrawable(context.getResources().getDrawable(R.drawable.end_difficult_hard));
                    params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
                    params.setMargins(150, 1500, 0, 0);
                    params.width = 1800;
                    params.height = 2000;
                    layout.addView(diff, params);
                    animation(diff, 75, 75, -850, 650, 1500);

                    //challenge
                    ImageView challenge = new ImageView(context);
                    challenge.setImageDrawable(context.getResources().getDrawable(R.drawable.challenge));
                    params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
                    params.setMargins(300, 1450, 0, 0);
                    params.width = 500;
                    params.height = 200;
                    layout.addView(challenge, params);
                    animation(challenge, 350, 350, 3000, 1400, 1500);

                    //time
                    TextView time = new TextView(context);
                    time.setText(getTime());
                    time.setTextSize(20);
                    time.setTextColor(context.getResources().getColor(R.color.black));
                    params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
                    params.setMargins(300, 1450, 0, 0);
                    params.width = 500;
                    params.height = 200;
                    layout.addView(time, params);
                    t_animation(time,520, 520, -320, 1180, 1500);


                    //back
                    ImageView back = new ImageView(context);
                    back.setImageDrawable(context.getResources().getDrawable(R.drawable.success_back));
                    params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100);
                    params.setMargins(300, 1700, 0, 0);
                    params.width = 500;
                    params.height = 200;
                    layout.addView(back, params);
                    animation(back, 350, 350, 3000, 1600, 1500);

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MusicServer.sound_play(context,R.raw.sound_clickbutton);

                            Intent intent=new Intent();
                            intent.setClass(context,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            back.setEnabled(false);
                        }
                    });


                    challenge.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MusicServer.sound_play(context,R.raw.sound_clickbutton);
                            //重进本页面，实现再次挑战
                            Intent intent = new Intent();
                            intent.setClass(context, GameActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("difficulty",difficulty);
                            context.startActivity(intent);
                            //禁用
                            challenge.setEnabled(false);
                            back.setEnabled(false);
                        }
                    });


                }


            }
        });
        return image;
    }
    //image动画
    public static void animation(ImageView img,int vx1,int vx2,int vy1,int vy2,int time){
        ObjectAnimator cXAnimation=ObjectAnimator.ofFloat(img,"X",vx1,vx2);
        ObjectAnimator cYAnimation=ObjectAnimator.ofFloat(img,"Y",vy1,vy2);
        cXAnimation.setDuration(time);
        cYAnimation.setDuration(time);
        cXAnimation.start();
        cYAnimation.start();
    }
    //text动画
    public static void t_animation(TextView text,int vx1,int vx2,int vy1,int vy2,int time){
        ObjectAnimator cXAnimation=ObjectAnimator.ofFloat(text,"X",vx1,vx2);
        ObjectAnimator cYAnimation=ObjectAnimator.ofFloat(text,"Y",vy1,vy2);
        cXAnimation.setDuration(time);
        cYAnimation.setDuration(time);
        cXAnimation.start();
        cYAnimation.start();
    }
    //将单个卡牌移动至 x y  属性动画+结点坐标改变
    public static void moveOneCardToXY(ImageView image,int x,int y,int time){
        ObjectAnimator XAnimation=ObjectAnimator.ofFloat(image,"X",image.getX(),x);
        ObjectAnimator YAnimation=ObjectAnimator.ofFloat(image,"Y",image.getY(),y);
        XAnimation.setDuration(time);
        YAnimation.setDuration(time);
        XAnimation.start();
        YAnimation.start();
        //改变image对应卡堆卡牌结点坐标
        setCardLeftTop(CardPile.getCard(image),x,y);
    }
    //设置卡牌结点坐标
    public static void setCardLeftTop(Card card,int left,int top){
        card.setLeft(left);
        card.setTop(top);
    }


    //打开道具菜单
    public static void openPropMenu(Context context,RelativeLayout layout){
        if(propMenu==0) {
            dark = new ImageView(context);
            dark.setImageDrawable(context.getResources().getDrawable(R.drawable.dark));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
            params.setMargins(0, 200, 0, 0);
            params.width = 1500;
            params.height = 2000;
            layout.addView(dark, params);
            dark.setAlpha(0);
            dark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){}
            });


            //上卷轴
            if(reel_1!=null)reel_1.setVisibility(View.INVISIBLE);
            reel_1 = new ImageView(context);
            reel_1.setImageDrawable(context.getResources().getDrawable(R.drawable.reel_1));
            params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
            params.setMargins(0, 0, 0, 0);
            params.width = 8000;
            params.height = 8000;
            layout.addView(reel_1, params);
            //下卷轴
            if(reel_2!=null)reel_2.setVisibility(View.INVISIBLE);
            reel_2 = new ImageView(context);
            reel_2.setImageDrawable(context.getResources().getDrawable(R.drawable.reel_2));
            params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
            params.setMargins(0, 0, 0, 0);
            params.width = 8000;
            params.height = 8000;
            layout.addView(reel_2, params);
            //回溯
            if(prop_1!=null)prop_1.setVisibility(View.INVISIBLE);
            prop_1 = new ImageView(context);
            prop_1.setImageDrawable(context.getResources().getDrawable(R.drawable.prop_1));
            if(prop[0]<=0)prop_1.setImageDrawable(context.getDrawable(R.drawable.prop_1_grey));
            params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
            params.setMargins(0, 0, 0, 0);
            params.width = 400;
            params.height = 400;
            layout.addView(prop_1, params);
            //紊乱
            if(prop_2!=null)prop_2.setVisibility(View.INVISIBLE);
            prop_2 = new ImageView(context);
            prop_2.setImageDrawable(context.getResources().getDrawable(R.drawable.prop_2));
            if(prop[1]<=0)prop_2.setImageDrawable(context.getDrawable(R.drawable.prop_2_grey));
            params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
            params.setMargins(0, 0, 0, 0);
            params.width = 400;
            params.height = 400;
            layout.addView(prop_2, params);
            //挪移
            if(prop_3!=null)prop_3.setVisibility(View.INVISIBLE);
            prop_3 = new ImageView(context);
            prop_3.setImageDrawable(context.getResources().getDrawable(R.drawable.prop_3));
            if(prop[2]<=0) prop_3.setImageDrawable(context.getDrawable(R.drawable.prop_3_grey));
            params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
            params.setMargins(0, 0, 0, 0);
            params.width = 400;
            params.height = 400;
            layout.addView(prop_3, params);
            //探底
            if(prop_4!=null)prop_4.setVisibility(View.INVISIBLE);
            prop_4 = new ImageView(context);
            prop_4.setImageDrawable(context.getResources().getDrawable(R.drawable.prop_4));
            if(prop[3]<=0)prop_4.setImageDrawable(context.getDrawable(R.drawable.prop_4_grey));
            params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 600);
            params.setMargins(0, 0, 0, 0);
            params.width = 400;
            params.height = 400;
            layout.addView(prop_4, params);

            animation(reel_1, 2000, 0, -375, -375, 400);
            animation(reel_2, -2000, 0, 130, 130, 400);
            animation(prop_1, 2000, 200, 625, 625, 400);
            animation(prop_2, 2500, 700, 625, 625, 400);
            animation(prop_3, -1700, 100, 1125, 1125, 400);
            animation(prop_4, -1200, 600, 1125, 1125, 400);
            propMenu=1;

            prop_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(prop[0]<=0){
                        prop_1.setImageDrawable(context.getDrawable(R.drawable.prop_1_grey));
                        toast(context,"技能使用次数已用尽",0,0);
                        return;
                    }
                    if(CardSlot.length()==0){
                        toast(context,"无卡牌可回溯",0,0);
                        return;
                    }
                    prop_2(context,layout);
                    openPropMenu(context,layout);

                }
            });
            prop_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(prop[1]<=0){
                        prop_2.setImageDrawable(context.getDrawable(R.drawable.prop_2_grey));
                        toast(context,"技能使用次数已用尽",0,0);
                        return;
                    }
                    prop_3(context,layout);
                    openPropMenu(context,layout);
                }
            });
            prop_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(prop[2]<=0){
                        prop_3.setImageDrawable(context.getDrawable(R.drawable.prop_3_grey));
                        toast(context,"技能使用次数已用尽",0,0);
                        return;
                    }
                    if(CardSlot.length()<3){
                        toast(context,"下方卡槽内卡牌数小于3",0,1800);
                        return ;
                    }
                    prop_1(context,layout);
                    openPropMenu(context,layout);
                }
            });
            prop_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(prop[3]<=0){
                        prop_4.setImageDrawable(context.getDrawable(R.drawable.prop_4_grey));
                        toast(context,"技能使用次数已用尽",0,0);
                        return;
                    }
                    moveAwaySurface(context,layout);
                    openPropMenu(context,layout);
                    prop[3]--;
                }
            });
        }else{
            dark.setVisibility(View.INVISIBLE);
            animation(reel_1, 0, 2000, -375, -375, 400);
            animation(reel_2, 0, -2000, 130, 130, 400);
            animation(prop_1, 200, 2000, 625, 625, 400);
            animation(prop_2, 700 , 2500, 625, 625, 400);
            animation(prop_3, 100, -1700, 1125, 1125, 400);
            animation(prop_4, 600, -1200, 1125, 1125, 400);
            propMenu=0;
        }
    }


    //道具1
    public static void prop_1(Context context,RelativeLayout layout){
        if(CardSlot.length()<3)return ;
        moveOutThreeCardFromCardSlot(context,layout);
        prop[2]--;
    }
    //从卡槽移出三张卡牌到牌堆
    public static void moveOutThreeCardFromCardSlot(Context context,RelativeLayout layout){
        int x,y=1730;
        for(int i=0;i<3;i++){
            x=378+i*140;
            ImageView img=createCard(context,layout,x,y,50,getCardType(CardSlot.getFirstCard()));
            moveOneCardToXY(img,x,y,10);
            CardSlot.getFirstCard().getImage().setEnabled(false);
            CardSlot.getFirstCard().getImage().setVisibility(View.INVISIBLE);
            CardSlot.deleteFirstCard();
        }
        CardSlot.keepToTheSide();
    }
    //道具2
    public static void prop_2(Context context,RelativeLayout layout){
        if(CardSlot.length()==0)return;
        ImageView img=createCard(context,layout,last[0], last[1], last[2], getCardType(CardSlot.getLastCard()));
        moveOneCardToXY(img,last[0],last[1],10);
        CardSlot.getLastCard().getImage().setEnabled(false);
        CardSlot.getLastCard().getImage().setVisibility(View.INVISIBLE);
        CardSlot.deleteLastCard();
        CardPile.dark(context);
        prop[0]--;
    }
    //道具3
    public static void prop_3(Context context,RelativeLayout layout){
        int count=CardPile.cardPileCount;
        if(CardPile.cardPileCount==0)return;
        int[][] arr=CardPile.shuffle(context,layout);
        for(int i=0;i<count;i++){
            ImageView img= createCard(context,layout,arr[i][0],arr[i][1],arr[i][2],arr[i][3]);
            Utility.moveOneCardToXY(img,arr[i][0],arr[i][1],10);
        }
        CardPile.dark(context);
        prop[1]--;
    }
    //特殊，移动下方槽位所有卡至上方槽位
    public static void moveOutAllCardToTopCardslot(Context context,RelativeLayout layout){
        int x,y=230;
        for(int i=0;i<7;i++){
            x=48+i*157;
            ImageView img=createCard(context,layout,x,y,51,getCardType(CardSlot.getFirstCard()));
            moveOneCardToXY(img,x,y,10);
            CardSlot.getFirstCard().getImage().setEnabled(false);
            CardSlot.getFirstCard().getImage().setVisibility(View.INVISIBLE);
            CardSlot.deleteFirstCard();
        }
        CardSlot.keepToTheSide();
    }
    //移开表层7张卡  红7
    public static void moveAwaySurface(Context context,RelativeLayout layout){
        int x,y=1900;
        List List=new ArrayList();
        Card card=null;
        boolean flag;
        int times=7;
        if(times>CardPile.dp1To10number())times=CardPile.dp1To10number();
        for(int i=0;i<times;i++){
            x=100+i*140;
            do{
                flag=true;
                card=CardPile.getCardFromSurface_random();
                if(List.size()==0)break;
                for(int j=0;j<List.size();j++){
                    if(card==List.get(j))flag=false;
                }
            }while(flag==false);
            List.add(card);
            CardPile.changeCardDp(card,card.getDepth(),50);
            card.setDepth(50);
            moveOneCardToXY(card.getImage(),x,y,500);
        }
        //卡堆覆盖检测
        CardPile.illume(context);
        CardPile.dark(context);
    }






    //获取随机ImageView
    public static ImageView randomCard(Context context,int type){
        Random random = new Random();
        if(type==-1)type=random.nextInt(14);
        ImageView image=new ImageView(context);
        switch(type){
            case 0:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_bq));
                image.setId(R.id.bq);
                break;
            case 1:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_hfm));
                image.setId(R.id.hfm);
                break;
            case 2:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_ldy));
                image.setId(R.id.ldy);
                break;
            case 3:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_lsz));
                image.setId(R.id.lsz);
                break;
            case 4:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_ssm));
                image.setId(R.id.ssm);
                break;
            case 5:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_wwy));
                image.setId(R.id.wwy);
                break;
            case 6:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_zzj));
                image.setId(R.id.zzj);
                break;
            case 7:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_bcgm));
                image.setId(R.id.bcgm);
                break;
            case 8:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_jyj));
                image.setId(R.id.jyj);
                break;
            case 9:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_nj));
                image.setId(R.id.nj);
                break;
            case 10:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_pwl));
                image.setId(R.id.pwl);
                break;
            case 11:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_qjyf));
                image.setId(R.id.qjyf);
                break;
            case 12:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_shl));
                image.setId(R.id.shl);
                break;
            case 13:
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.card_tr));
                image.setId(R.id.tr);
                break;
        }
        return image;
    }
    //获取image对应枚举类
    public static CardType getImageCardType(ImageView image){
        if (image.getId() == R.id.bq)return CardType.BQ;
        else if (image.getId() == R.id.hfm) return CardType.HFM;
        else if (image.getId() == R.id.ldy) return CardType.LDY;
        else if (image.getId() == R.id.lsz) return CardType.LSZ;
        else if (image.getId() == R.id.ssm) return CardType.SSM;
        else if (image.getId() == R.id.wwy) return CardType.WWY;
        else if (image.getId() == R.id.zzj) return CardType.ZZJ ;
        else if (image.getId() == R.id.bcgm) return CardType.BCGM;
        else if (image.getId() == R.id.jyj) return CardType.JYJ;
        else if (image.getId() == R.id.nj) return CardType.NJ;
        else if (image.getId() == R.id.pwl)return CardType.PWL;
        else if (image.getId() == R.id.qjyf) return CardType.QJYF;
        else if (image.getId() == R.id.shl) return CardType.SHL;
        else if (image.getId() == R.id.tr) return CardType.TR;
        else return CardType.BQ;
    }
    //获取对应type
    public static int getCardType(Card card){
        if(card.getID()==CardType.BQ)return 0;
        else if(card.getID()==CardType.HFM)return 1;
        else if(card.getID()==CardType.LDY)return 2;
        else if(card.getID()==CardType.LSZ)return 3;
        else if(card.getID()==CardType.SSM)return 4;
        else if(card.getID()==CardType.WWY)return 5;
        else if(card.getID()==CardType.ZZJ)return 6;
        else if(card.getID()==CardType.BCGM)return 7;
        else if(card.getID()==CardType.JYJ)return 8;
        else if(card.getID()==CardType.NJ)return 9;
        else if(card.getID()==CardType.PWL)return 10;
        else if(card.getID()==CardType.QJYF)return 11;
        else if(card.getID()==CardType.SHL)return 12;
        else if(card.getID()==CardType.TR)return 13;
        else return 0;

    }
    //获取黑化Drawable
    public static Drawable getImageDrawable_Dark(Context context,ImageView image){
        if (image.getId() == R.id.bq)return context.getResources().getDrawable(R.drawable.card_bq_dark);
        else if (image.getId() == R.id.hfm) return context.getResources().getDrawable(R.drawable.card_hfm_dark);
        else if (image.getId() == R.id.ldy) return context.getResources().getDrawable(R.drawable.card_ldy_dark);
        else if (image.getId() == R.id.lsz) return context.getResources().getDrawable(R.drawable.card_lsz_dark);
        else if (image.getId() == R.id.ssm) return context.getResources().getDrawable(R.drawable.card_ssm_dark);
        else if (image.getId() == R.id.wwy) return context.getResources().getDrawable(R.drawable.card_wwy_dark);
        else if (image.getId() == R.id.zzj) return context.getResources().getDrawable(R.drawable.card_zzj_dark);
        else if (image.getId() == R.id.bcgm)return context.getResources().getDrawable(R.drawable.card_bcgm_dark);
        else if (image.getId() == R.id.jyj) return context.getResources().getDrawable(R.drawable.card_jyj_dark);
        else if (image.getId() == R.id.nj) return context.getResources().getDrawable(R.drawable.card_nj_dark);
        else if (image.getId() == R.id.pwl)return context.getResources().getDrawable(R.drawable.card_pwl_dark);
        else if (image.getId() == R.id.qjyf) return context.getResources().getDrawable(R.drawable.card_qjyf_dark);
        else if (image.getId() == R.id.shl) return context.getResources().getDrawable(R.drawable.card_shl_dark);
        else if (image.getId() == R.id.tr) return context.getResources().getDrawable(R.drawable.card_tr_dark);
        else return context.getResources().getDrawable(R.drawable.card_bq_dark);
    }
    //获取点亮Drawable
    public static Drawable getImageDrawable(Context context,ImageView image){
        if (image.getId() == R.id.bq)return context.getResources().getDrawable(R.drawable.card_bq);
        else if (image.getId() == R.id.hfm) return context.getResources().getDrawable(R.drawable.card_hfm);
        else if (image.getId() == R.id.ldy) return context.getResources().getDrawable(R.drawable.card_ldy);
        else if (image.getId() == R.id.lsz) return context.getResources().getDrawable(R.drawable.card_lsz);
        else if (image.getId() == R.id.ssm) return context.getResources().getDrawable(R.drawable.card_ssm);
        else if (image.getId() == R.id.wwy) return context.getResources().getDrawable(R.drawable.card_wwy);
        else if (image.getId() == R.id.zzj) return context.getResources().getDrawable(R.drawable.card_zzj);
        else if (image.getId() == R.id.bcgm)return context.getResources().getDrawable(R.drawable.card_bcgm);
        else if (image.getId() == R.id.jyj) return context.getResources().getDrawable(R.drawable.card_jyj);
        else if (image.getId() == R.id.nj) return context.getResources().getDrawable(R.drawable.card_nj);
        else if (image.getId() == R.id.pwl)return context.getResources().getDrawable(R.drawable.card_pwl);
        else if (image.getId() == R.id.qjyf) return context.getResources().getDrawable(R.drawable.card_qjyf);
        else if (image.getId() == R.id.shl) return context.getResources().getDrawable(R.drawable.card_shl);
        else if (image.getId() == R.id.tr) return context.getResources().getDrawable(R.drawable.card_tr);
        else return context.getResources().getDrawable(R.drawable.card_bq);
    }
    //清空
    public static void clear(){
        propMenu=0;
        prop[0]=1;
        prop[1]=1;
        prop[2]=1;
        prop[3]=1;
        prop[4]=1;
        CardPile.clear();
        CardSlot.card=new Card();
        deletTime=0;
    }
    //toast
    public static void toast(Context context, String text,int x,int y){
        Toast toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,x,y);
        toast.show();
    }

}
