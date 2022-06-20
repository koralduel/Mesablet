package com.example.mesablet.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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
        uploadProfilePhoto("Posts",post.getId(),"Post_image", Uri.parse(post.getPost_photos_path()));
        storageRef = FirebaseStorage.getInstance().getReference();
        post.setPost_photos_path(storageRef.child("Posts").child(post.getId()).child("Post_image").toString());
        dataRef.child(String.valueOf(post.getId())).setValue(post);
 //      storageRef.child("Users").child(user.getUid()).child("ProfileImg").putFile(Uri.parse(post.getPublisher_image_path()));

  //     storageRef.child("Posts").child(post.getId()).child("Post_image").putFile(Uri.parse(post.getPost_photos_path()));

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

    public static void uploadProfilePhoto(String folder,String id,String type ,Uri imageUri) {

        storageRef = FirebaseStorage.getInstance().getReference(folder+"/").child(id+"/").child(type+"/");
        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });

    }

}
