package com.example.kasingj.smokecessation2;

/**
 * Created by Bradndon on 3/17/16.
 */
public class FeedPost {

    private int id;
    private String date;
    private String description;
    private int likes;
    private int dislikes;
    private boolean reacted;

    public FeedPost(int id, String date, String description, int likes, int dislikes) {
        this.id = id;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public FeedPost(int id, String date, String description, int likes, int dislikes, boolean reacted) {
        this.id = id;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.reacted = reacted;
    }
    public int getId(){return this.id;}
    public String getDate(){return this.date;}
    public int getLikes(){return this.likes;}
    public int getDislikes(){return this.dislikes;}
    public String getDescription(){return this.description;}
    public boolean getReaction() {return this.reacted;}
    public void setReaction(boolean reacted) {this.reacted = reacted;}


}
