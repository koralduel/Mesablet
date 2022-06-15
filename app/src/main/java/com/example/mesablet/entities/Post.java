package com.example.mesablet.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

   private String publisher_image_path;
   private String publisher_name;
   private String post_photos_path;
   private String post_context;
   private int likes;
   private String Address;
   private String Price;

   public Post (Post post){
       this.publisher_image_path = post.publisher_image_path;
       this.publisher_name = post.publisher_name;
       this.post_photos_path = post.post_photos_path;
       this.post_context = post.post_context;
       this.Address = post.Address;
       this.Price = post.Price;
   }

    public Post(String publisher_image_path, String publisher_name, String post_photos_path, String post_context, int likes, String address, String price) {
        this.publisher_image_path = publisher_image_path;
        this.publisher_name = publisher_name;
        this.post_photos_path = post_photos_path;
        this.post_context = post_context;
        this.likes = likes;
        Address = address;
        Price = price;
    }

    public String getPublisher_image_path() {
        return publisher_image_path;
    }

    public void setPublisher_image_path(String publisher_image_path) {this.publisher_image_path = publisher_image_path;}

    public String getPost_photos_path() {
        return post_photos_path;
    }

    public void setPost_photos_path(String post_photos_path) {this.post_photos_path = post_photos_path;}

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }


    public String getPost_context() {
        return post_context;
    }

    public void setPost_context(String post_context) {
        this.post_context = post_context;
    }

    public int getLikes() {return likes;}

    public void setLikes(int likes) {this.likes = likes;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getAddress() {return Address;}

    public void setAddress(String address) {Address = address;}

    public String getPrice() {return Price;}

    public void setPrice(String price) {Price = price;}
}
