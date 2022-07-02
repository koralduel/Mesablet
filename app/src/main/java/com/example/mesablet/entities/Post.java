package com.example.mesablet.entities;

import static java.util.UUID.randomUUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Post implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String id;
    @ColumnInfo
    private String publisher_image_path;
    @ColumnInfo
    private String publisher_name;
    @ColumnInfo
    private String post_photos_path;
    @ColumnInfo
    private String post_photos_path1;
    @ColumnInfo
    private String post_photos_path2;
    @ColumnInfo
    private String post_context;
    @ColumnInfo
    private String publisher_id;
    @ColumnInfo
    private String Address;
    @ColumnInfo
    private String Price;
    @ColumnInfo
    private String startDate;
    @ColumnInfo
    private String endDate;
    @ColumnInfo
    private String city;


    public Post() { }

    public Post (Post post){
       this.publisher_image_path = post.publisher_image_path;
       this.publisher_name = post.publisher_name;
       this.post_photos_path = post.post_photos_path;
       this.post_photos_path1 = post.post_photos_path1;
       this.post_photos_path2 = post.post_photos_path2;
       this.post_context = post.post_context;
       this.Address = post.Address;
       this.Price = post.Price;
       this.id=post.id;
       this.publisher_id=post.publisher_id;
       this.startDate = post.startDate;
       this.endDate = post.endDate;
       this.city = post.city;
   }

    public Post(String publisher_image_path, String publisher_name, String post_photos_path,
                String post_photos_path1,String post_photos_path2,String post_context, String address, String price,
                String publisher_id,String startDate,String endDate, String city) {
        this.id=randomUUID().toString().replace("-","");
        this.publisher_image_path = publisher_image_path;
        this.publisher_name = publisher_name;
        this.post_photos_path = post_photos_path;
        this.post_photos_path1= post_photos_path1;
        this.post_photos_path2 = post_photos_path2;
        this.post_context = post_context;
        Address = address;
        Price = price;
        this.publisher_id=publisher_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city= city;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getPublisher_image_path() { return publisher_image_path; }

    public void setPublisher_image_path(String publisher_image_path) { this.publisher_image_path = publisher_image_path; }

    public String getPublisher_name() { return publisher_name; }

    public void setPublisher_name(String publisher_name) { this.publisher_name = publisher_name; }

    public String getPost_photos_path() { return post_photos_path; }

    public void setPost_photos_path(String post_photos_path) { this.post_photos_path = post_photos_path; }

    public String getPost_context() { return post_context; }

    public void setPost_context(String post_context) { this.post_context = post_context; }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getPrice() { return Price; }

    public void setPrice(String price) { Price = price; }

    public String getPost_photos_path1() {
        return post_photos_path1;
    }

    public void setPost_photos_path1(String post_photos_path1) { this.post_photos_path1 = post_photos_path1; }

    public String getPost_photos_path2() {
        return post_photos_path2;
    }

    public void setPost_photos_path2(String post_photos_path2) { this.post_photos_path2 = post_photos_path2; }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}
}
