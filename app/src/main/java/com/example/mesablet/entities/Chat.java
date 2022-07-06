package com.example.mesablet.entities;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Chat implements Serializable {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference dateBase = FirebaseDatabase.getInstance().getReference("Users");


    String user1;
    String user2;
    String user1_image_Path;
    String user1_fullname;
    String user2_image_Path;
    String user2_fullname;


    public Chat(String user1, String user2, String user1_image_Path, String user1_fullname, String user2_image_Path, String user2_fullname) {
        this.user1 = user1;
        this.user2 = user2;
        this.user1_image_Path = user1_image_Path;
        this.user1_fullname = user1_fullname;
        this.user2_image_Path = user2_image_Path;
        this.user2_fullname = user2_fullname;
    }

    public Chat(){}

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getSender(){
        if(this.getUser1().equals(user.getUid()))
            return this.getUser2();
        return this.getUser1();
    }

    public String getOtheruserId(){
        if(this.getUser1().equals(user.getUid()))
            return this.getUser2();
        return this.getUser1();
    }

    public String getOtherUser_fullname(){
        if(user.getUid().equals(getUser1())){
            return getUser2_fullname();
        }
        return user1_fullname;
    }

    public String getOtherUser_image_Path(){
        if(user.getUid().equals(getUser1())){
            return getUser2_image_Path();
        }
        return user1_image_Path;
    }

    public String getUser1_image_Path() {
        return user1_image_Path;
    }

    public void setUser1_image_Path(String user1_image_Path) {
        this.user1_image_Path = user1_image_Path;
    }

    public String getUser1_fullname() {
        return user1_fullname;
    }

    public void setUser1_fullname(String user1_fullname) {
        this.user1_fullname = user1_fullname;
    }

    public String getUser2_image_Path() {
        return user2_image_Path;
    }

    public void setUser2_image_Path(String user2_image_Path) {
        this.user2_image_Path = user2_image_Path;
    }

    public String getUser2_fullname() {
        return user2_fullname;
    }

    public void setUser2_fullname(String user2_fullname) {
        this.user2_fullname = user2_fullname;
    }
}
