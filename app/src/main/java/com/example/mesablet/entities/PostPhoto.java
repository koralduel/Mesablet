package com.example.mesablet.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PostPhoto {

    @PrimaryKey(autoGenerate = true)
    private int id;
    int PostId;
    private String photo;

    public PostPhoto(int postId, String photo) {
        PostId = postId;
        this.photo = photo;
    }

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }
}
