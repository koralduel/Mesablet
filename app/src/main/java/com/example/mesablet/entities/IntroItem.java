package com.example.mesablet.entities;

import java.io.Serializable;

public class IntroItem implements Serializable
{
    String title;
    String text;
    Integer img;

    public IntroItem(String title, String text, Integer img) {
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public Integer getImg() { return img; }

    public void setImg(Integer img) { this.img = img; }
}
