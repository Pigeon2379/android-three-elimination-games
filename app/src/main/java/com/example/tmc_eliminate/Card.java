package com.example.tmc_eliminate;

import android.widget.ImageView;

public class Card {
    public Card pre=null;
    private CardType ID=null;
    private ImageView image=null;
    private int Depth=0;
    private Boolean isCover=false;
    private int left=0;
    private int top=0;
    public Card next=null;


    public Card(CardType ID, ImageView image, int Depth){
        this.ID=ID;
        this.image=image;
        this.Depth=Depth;
    }
    public Card(){}


    public int getDepth(){
        return Depth;
    }
    public void setDepth(int Depth){
        this.Depth=Depth;
    }
    public CardType getID() {
        return ID;
    }
    public void setID(CardType ID) {
        this.ID = ID;
    }
    public ImageView getImage() {
        return image;
    }
    public void setImage(ImageView image) {
        this.image = image;
    }
    public Boolean getIsCover() {
        return isCover;
    }
    public void setCover(Boolean cover) {
        isCover = cover;
    }
    public void setLeft(int left) {
        this.left = left;
    }
    public void setTop(int top) {
        this.top = top;
    }
    public int getLeft() {
        return left;
    }
    public int getTop() {
        return top;
    }
}
