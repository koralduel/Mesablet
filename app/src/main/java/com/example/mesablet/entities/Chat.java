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

    public Chat(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
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

    public String getSender_fullName(){
        if(this.getUser1().equals(user.getUid()))
            return this.getUser2();
        return this.getUser1();
    }

    public String getOtheruserId(){
        if(this.getUser1().equals(user.getUid()))
            return this.getUser2();
        return this.getUser1();
    }
}
