package com.example.tmc_eliminate;

import android.view.View;

import com.example.tmc_eliminate.Utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CardSlot {
    public static Card card=new Card();

    //length
    public static int length(){
        int len=0;
        Card p=card;
        while(p.next!=null){
            p=p.next;
            len++;
        }
        return len;
    }


    //取出头尾结点卡牌
    public static Card getFirstCard(){
        return card.next;
    }
    public static Card getLastCard(){
        Card p=card;
        while(p.next!=null)p=p.next;
        return p;
    }
    //删除头尾结点卡牌
    public static void deleteFirstCard(){
        card.next=card.next.next;
        if(card.next!=null)card.next.pre=card;
    }
    public static void deleteLastCard(){
        Card p=card;
        while(p.next.next!=null)p=p.next;
        p.next=null;
    }


    //设置卡槽内卡牌 可交互 布尔值
    public static void cardEnable(Boolean flag){
        Card p=card.next;
        while(p!=null){
            p.getImage().setEnabled(flag);
            p=p.next;
        }
    }


    //添加卡牌到卡槽
    public static void add(Card newcard)throws Exception{
        Card p=card;
        while(p.next!=null)p=p.next;
        p.next=newcard;
        p.next.pre=p;
        cardEnable(false);
        delete();
        if(length()==7){
            if(sevenCard()==1&&Utility.deletTime>=10)throw new Exception("seven_1");
            else if(sevenCard()==2&&Utility.deletTime>=10)throw new Exception("seven_2");
            else throw new Exception("槽位已满");
        }
    }

    //卡槽中 7张不同红卡 or 7张不同蓝卡
    public static int sevenCard(){
        Card p=card.next;
        int e[]=new int[14];
        while(p!=null){
            if(p.getID()==CardType.BQ)e[0]++;
            else if(p.getID()==CardType.HFM)e[1]++;
            else if(p.getID()==CardType.LDY)e[2]++;
            else if(p.getID()==CardType.LSZ)e[3]++;
            else if(p.getID()==CardType.SSM)e[4]++;
            else if(p.getID()==CardType.WWY)e[5]++;
            else if(p.getID()==CardType.ZZJ)e[6]++;
            else if(p.getID()==CardType.BCGM)e[7]++;
            else if(p.getID()==CardType.JYJ)e[8]++;
            else if(p.getID()==CardType.NJ)e[9]++;
            else if(p.getID()==CardType.PWL)e[10]++;
            else if(p.getID()==CardType.QJYF)e[11]++;
            else if(p.getID()==CardType.SHL)e[12]++;
            else if(p.getID()==CardType.TR)e[13]++;
            p=p.next;
        }
        if(e[0]==1&&e[1]==1&&e[2]==1&&e[3]==1&&e[4]==1&&e[5]==1&&e[6]==1)return 1;
        else if(e[7]==1&&e[8]==1&&e[9]==1&&e[10]==1&&e[11]==1&&e[12]==1&&e[13]==1)return 2;
        else return 0;
    }






    //卡牌左边顶格
    public static void keepToTheSide(){
        Card p=card.next;
        int i=0;
        while(p!=null){
            int x=48+i*158;
            p.getImage().setX(x);
            p=p.next;
            i++;
        }

    }


    //三消
    public static void delete(){
        Card p =card.next;
        while(p!=null){
            if(sameCardCount(p.getID())==3){
                deleteSameCard(p.getID());
                Utility.deletTime++;
                break;
            }
            p=p.next;
        }
    }
    //卡槽内相同卡牌数
    public static int sameCardCount(CardType cardType){
        Card p=card.next;
        int count=0;
        while(p!=null){
            if(p.getID()==cardType)count++;
            p=p.next;
        }
        return count;
    }
    //删除卡槽内cardType类型的卡牌
    public static void deleteSameCard(CardType cardType){
        Card p=card.next;
        List<Card> list=new ArrayList<>();
        while(p!=null){
            if(p.getID()==cardType){
                if(p.next==null)p.pre.next=null;
                else {
                    p.pre.next = p.next;
                    p.next.pre = p.pre;
                }
                list.add(p);
                p=card.next;
                continue;
            }
            p=p.next;

        }
        //wait,delete three card after animation
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                for(Card card:list){
                    card.getImage().setEnabled(false);
                    card.getImage().setVisibility(View.INVISIBLE);
                    keepToTheSide();
                }
                timer.cancel();
            }
        };
        timer.schedule(task,500);
    }

}
