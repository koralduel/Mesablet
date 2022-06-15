package com.example.mesablet;

import androidx.annotation.NonNull;

import com.example.mesablet.entities.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireBase {

   private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
   private FirebaseDatabase dateBase = FirebaseDatabase.getInstance();

   DatabaseReference dataRef = dateBase.getReference("Posts");

   public void add(Post post){

       dataRef.child(String.valueOf(post.getId())).setValue(post);
   }

   public void delete(Post post){

       dataRef.child(String.valueOf(post.getId())).removeValue();
   }

   public void reload(){
       dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
   }


}
