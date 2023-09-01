package com.example.tmc_eliminate;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.tmc_eliminate.Utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardPile {
    public static List[] lists=new List[100];
    public static int cardPileCount=0;

    public static void build(int depth){
        lists=new List[depth];
        for(int i=0;i< lists.length;i++)lists[i]=new ArrayList<Card>();
        cardPileCount=0;
    }

    //51层 获取上方卡牌栏位中卡牌数
    public static int getSpecialCardslotLength(){
        ArrayList L=(ArrayList)lists[51];
        return L.size();
    }
    //随机顶层获取一张card
    public static Card getCardFromSurface_random(){
        int dp=10;
        for(int i=10;i>-1;i--){
            if(lists[i].size()>0){
                dp=i;
                break;
            }
        }
        Random random=new Random();
        int n=random.nextInt(lists[dp].size());
        return (Card) lists[dp].get(n);
    }
    //1-10层（普通排队层）卡牌总数
    public static int dp1To10number(){
        int result=0;
        for(int dp=0;dp<11;dp++){
            for(int i=0;i<lists[dp].size();i++)result++;
        }
        return result;
    }

    //获取不同类型卡牌各自的个数，返回 int 数组
    public static int[] getCardCountArr(){
        int[] arr={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(int dp=1;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                arr[Utility.getCardType((Card)L.get(i))]++;
            }
        }
        return arr;
    }
    //打乱
    public static int[][] shuffle(Context context, RelativeLayout layout){
        int[] arr=getCardCountArr();

        int[][] result=new int[300][4];
        int xydt=0;
        int x,y,d,type;

        Random random=new Random();
        for(int dp=1;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                x=((Card)L.get(i)).getLeft();
                y=((Card)L.get(i)).getTop();
                d=((Card)L.get(i)).getDepth();

                while(true){
                    type= random.nextInt(14);
                    if(arr[type]>0){
                        arr[type]--;
                        break;
                    }
                }
                result[xydt][0]=x;
                result[xydt][1]=y;
                result[xydt][2]=d;
                result[xydt++][3]=type;

                ((Card)L.get(i)).getImage().setVisibility(View.INVISIBLE);

            }
        }
        clear();
        //level=2;
        return result;

    }

    //改变card层级
    public static void changeCardDp(Card card,int dp,int new_dp){
        for(int i=0;i<lists[dp].size();i++){
            if(lists[dp].get(i)==card){
                lists[dp].remove(i);
                break;
            }
        }
        lists[new_dp].add(card);
    }


    //添加卡牌
    public static void add(Card card,int i){
        lists[i].add(card);
        cardPileCount++;
    }
    //删除image对应card
    public static Card delete(ImageView image){
        Card result=getCard(image);
        DELETE:
        for(int dp=1;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                if(L.get(i)==result){
                    L.remove(i);
                    break DELETE;
                }
            }
        }
        return result;
    }
    //获取image对应的card
    public static Card getCard(ImageView image){
        for(int dp=0;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                if(((Card)L.get(i)).getImage()==image){
                    return (Card)L.get(i);
                }
            }
        }
        return new Card(CardType.BQ,null,0);
    }

    //禁用所有卡牌
    public static void enabledAllCard(){
        for(int dp=1;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                ((Card)L.get(i)).getImage().setEnabled(false);
            }
        }
    }

    //点亮所有卡牌
    public static void illume(Context context){
        for(int dp=1;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                ((Card)L.get(i)).getImage().setImageDrawable(Utility.getImageDrawable(context,((Card)L.get(i)).getImage()));
                ((Card)L.get(i)).getImage().setEnabled(true);
            }
        }
    }


    //黑化被覆盖卡牌
    public static void dark(Context context){
        for(int dp=1;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                if(oneCardEquals((Card)L.get(i))){
                    ((Card)L.get(i)).getImage().setImageDrawable(Utility.getImageDrawable_Dark(context,((Card)L.get(i)).getImage()));
                    ((Card)L.get(i)).getImage().setEnabled(false);
                }
            }
        }
    }
    // card 是否被覆盖
    public static Boolean oneCardEquals(Card card){
        for(int dp= card.getDepth()+1;dp<lists.length;dp++){
            ArrayList L=(ArrayList)lists[dp];
            for(int i=0;i<L.size();i++){
                if(cardIsCover(card,(Card)L.get(i)))return true;
            }
        }
        return false;
    }
    // card2 是否覆盖 card1
    public static boolean cardIsCover(Card card1,Card card2){
        int flag[]={0,0,0,0};

        if(card2.getLeft()>=card1.getLeft()&&card2.getLeft()<card1.getLeft()+140)flag[0]=1;
        //if(card2.getTop()>=card1.getTop()&&card2.getTop()<card1.getTop()+125)flag[1]=1;
        if(card2.getTop()>=card1.getTop()&&card2.getTop()<card1.getTop()+140)flag[1]=1;
        if(card2.getLeft()+140>card1.getLeft()&&card2.getLeft()+140<=card1.getLeft()+140)flag[2]=1;
        if(card2.getTop()+140>card1.getTop()&&card2.getTop()+140<=card1.getTop()+140)flag[3]=1;
        if((flag[0]==1||flag[2]==1)&&(flag[1]==1||flag[3]==1))return true;

        return false;
    }



    //清空
    public static void clear(){
        cardPileCount=0;
        //level=1;
        build(100);
    }
}
