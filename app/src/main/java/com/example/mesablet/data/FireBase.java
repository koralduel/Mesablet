package com.example.mesablet.data;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mesablet.data.PostDao;
import com.example.mesablet.entities.Post;
import com.example.mesablet.repositories.PostsRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FireBase {

   private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
   private FirebaseDatabase dateBase = FirebaseDatabase.getInstance();
   private DatabaseReference dataRef = dateBase.getReference("Posts");

   private PostDao dao;
   private PostsRepository.PostListData postListData;

    public FireBase(PostDao dao, PostsRepository.PostListData postListData) {
       dataRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               List<Post> posts = new ArrayList<>();
               for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                   Post post = dataSnapshot.getValue(Post.class);
                   posts.add(post);
               }
               postListData.setValue(posts);
               new Thread(()->{
                   dao.clear();
                   dao.insertList(posts);
               }).start();
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","Failed to change data in dao");
           }
       });
        this.dao=dao;
        this.postListData=postListData;
    }

    public void add(Post post){

       dataRef.child(String.valueOf(post.getId())).setValue(post);
       storageRef.child(String.valueOf(post.getId())).child("Publisher_image").putFile(Uri.parse(post.getPublisher_image_path()));
       storageRef.child(String.valueOf(post.getId())).child("Post_image").putFile(Uri.parse(post.getPost_photos_path()));

   }

   public void delete(Post post){

       dataRef.child(String.valueOf(post.getId())).removeValue();
       storageRef.child(String.valueOf(post.getId())).delete();
   }

   /*public void reload(){
       dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               List<Post> posts=new ArrayList<>();
               for (DataSnapshot child:snapshot.getChildren()) {
                       posts.add(new Post(child.getValue(Post.class)));
               }
                postListData.setValue(posts);
                dao.insertList(posts);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.d("Error","Cannot refresh new posts");
           }
       });

   }*/

   /*public int[][] getPhotoBitmap(String path){

   }*/


}
