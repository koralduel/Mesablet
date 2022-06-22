package com.example.mesablet.entities;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Chat implements Serializable {

   private DatabaseReference dateBase = FirebaseDatabase.getInstance().getReference();

    String senderID;
    String Image_sender_path;
    String sender_fullName;

    public Chat(String senderID) {
        this.senderID = senderID;
        this.Image_sender_path=dateBase.child("Users").child(senderID).child("profileImageUri").toString();
        this.sender_fullName= dateBase.child("Users").child(senderID).child("fullname").toString();

    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getImage_sender_path() {
        return Image_sender_path;
    }

    public void setImage_sender_path(String image_sender_path) {
        Image_sender_path = image_sender_path;
    }

    public String getSender_fullName() {
        return sender_fullName;
    }

    public void setSender_fullName(String sender_fullName) {
        this.sender_fullName = sender_fullName;
    }
}
