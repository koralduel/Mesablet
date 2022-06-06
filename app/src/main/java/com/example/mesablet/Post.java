package com.example.mesablet;

import android.widget.Button;

public class Post<bottomNavigationView> {

    int publisher_image;
    String publisher_name;
    int post_photos;
    String post_context;
    bottomNavigationView bottomNavigationView;

    public Post(int publisher_image, String publisher_name, int post_photos, String post_context) {
        this.publisher_image = publisher_image;
        this.publisher_name = publisher_name;
        this.post_photos = post_photos;
        this.post_context = post_context;
    }

    public int getPublisher_image() {
        return publisher_image;
    }

    public void setPublisher_image(int publisher_image) {
        this.publisher_image = publisher_image;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public int getPost_photos() {
        return post_photos;
    }

    public void setPost_photos(int post_photos) {
        this.post_photos = post_photos;
    }

    public String getPost_context() {
        return post_context;
    }

    public void setPost_context(String post_context) {
        this.post_context = post_context;
    }

    public bottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
}
