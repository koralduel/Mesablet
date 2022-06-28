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
    String otherUser_image_Path;
    String otherUser_fullname;


    public Chat(String user1, String user2, String otherUser_image_Path, String otherUser_fullname) {
        this.user1 = user1;
        this.user2 = user2;
        this.otherUser_image_Path = otherUser_image_Path;
        this.otherUser_fullname = otherUser_fullname;
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

    public String getOtherUser_image_Path() {
        return otherUser_image_Path;
    }

    public void setOtherUser_image_Path(String otherUser_image_Path) {
        this.otherUser_image_Path = otherUser_image_Path;
    }

    public String getOtherUser_fullname() {
        return otherUser_fullname;
    }

    public void setOtherUser_fullname(String otherUser_fullname) {
        this.otherUser_fullname = otherUser_fullname;
    }
}
