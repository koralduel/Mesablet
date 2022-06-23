package com.example.mesablet.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.mesablet.entities.Post;
import com.example.mesablet.repositories.PostsRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FireBase {

   private static StorageReference storageRef;
   //private FirebaseDatabase dateBase = FirebaseDatabase.getInstance();
   private DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Posts");

   private PostDao dao;
   private PostsRepository.PostListData postListData;

   private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

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
        List<String> photosPaths = new ArrayList<>();
        photosPaths.add(post.getPost_photos_path());
        photosPaths.add(post.getPost_photos_path1());
        photosPaths.add(post.getPost_photos_path2());
        for (int i=1 ; i<=photosPaths.size(); i ++) {
            UploadImage("Posts",post.getId(),"Post_image"+i, Uri.parse(post.getPost_photos_path()));
        }
        storageRef = FirebaseStorage.getInstance().getReference();
        post.setPost_photos_path(storageRef.child("Posts").child(post.getId()).child("Post_image1").toString());
        post.setPost_photos_path1(storageRef.child("Posts").child(post.getId()).child("Post_image2").toString());
        post.setPost_photos_path2(storageRef.child("Posts").child(post.getId()).child("Post_image3").toString());
        dataRef.child(String.valueOf(post.getId())).setValue(post);

   }

   public void delete(Post post){

       dataRef.child(post.getId()).removeValue();
 //      storageRef.child("Users").child(user.getUid()).delete();
       storageRef=FirebaseStorage.getInstance().getReference();
       storageRef.child("Posts").child(post.getId()).delete();
   }

   public void reload(){
       dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               List<Post> posts=new ArrayList<>();
               for (DataSnapshot child:snapshot.getChildren()) {
                       posts.add(new Post(child.getValue(Post.class)));
               }
                postListData.setValue(posts);
               new Thread(()->{
                   dao.clear();
                   dao.insertList(posts);
               }).start();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.d("Error","Cannot refresh new posts");
           }
       });

   }

    static Bitmap bitmap = null;
    public static void downloadImage(String path, ImageView imageView)
    {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(path);
        try {
            bitmap = null;
            File localFile = File.createTempFile("tempfile",".jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    if(bitmap != null)
                        imageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void UploadImage(String folder,String id,String type ,Uri... uris) {

        storageRef = FirebaseStorage.getInstance().getReference(folder+"/").child(id+"/").child(type+"/");

        for (Uri u:uris) {
            storageRef.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });
        }

    }

}
