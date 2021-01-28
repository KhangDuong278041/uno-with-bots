package com.jungko.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Player {
    private int x;
    private int y;
    private int handX;
    private int handY;
    private int id;
    private String name;
//    private ImageView avatar;
    private Image avatar;
    private ArrayList<Card> hand;
    private int score;
    private boolean you;

    public Player(int id, String name, Image avatar, ArrayList<Card> hand, int score, boolean you) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.hand = hand;
        this.score = score;
        this.you = you;
    }

    public void setPosition(int x, int y, int handX, int handY){
        this.x = x;
        this.y = y;
        this.handX = handX;
        this.handY = handY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHandX() {
        return handX;
    }

    public int getHandY() {
        return handY;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Image getAvatar() {
        return avatar;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }

    public boolean isYou() {
        return you;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void addScore(int score){
        this.score += score;
    }

//    public void setScore(int score) {
//        this.score = score;
//    }
}
