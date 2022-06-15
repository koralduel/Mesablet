package com.example.mesablet.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

   private String publisher_image;
   private String publisher_name;
   private List<String> post_photos;
   private String post_context;
   private int likes;
   private String Address;
   private String Price;

   public Post (Post post){
       this.publisher_image = post.publisher_image;
       this.publisher_name = post.publisher_name;
       this.post_photos = post.post_photos;
       this.post_context = post.post_context;
       this.Address = post.Address;
       this.Price = post.Price;
   }

    public Post(String publisher_image, String publisher_name, int post_photos, String post_context ,String Address, String Price) {
        this.publisher_image = publisher_image;
        this.publisher_name = publisher_name;
        this.post_photos = post_photos;
        this.post_context = post_context;
        this.Address=Address;
        this.Price=Price;
    }

    public String getPublisher_image() {
        return publisher_image;
    }

    public void setPublisher_image(String publisher_image) {
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

    public int getLikes() {return likes;}

    public void setLikes(int likes) {this.likes = likes;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getAddress() {return Address;}

    public void setAddress(String address) {Address = address;}

    public String getPrice() {return Price;}

    public void setPrice(String price) {Price = price;}
}
