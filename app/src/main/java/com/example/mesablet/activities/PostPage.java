package com.example.mesablet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.data.FireBase;
import com.example.mesablet.databinding.ActivityPostPageBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class PostPage extends AppCompatActivity {

    private ActivityPostPageBinding binding;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int counter = 0;
    PostsViewModel viewModel =new PostsViewModel();

    Post post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        post =(Post) getIntent().getSerializableExtra("post");


        FireBase.downloadImage(post.getPublisher_image_path(),binding.postOwnerPhoto);
        binding.postOwnerFullName.setText(post.getPublisher_name());
        binding.TVEnterAddress.setText(post.getAddress());
        binding.TVEnterPrice.setText(post.getPrice());
        binding.TvPostContent.setText(post.getPost_context());
        String[] photos = {post.getPost_photos_path(),post.getPost_photos_path1(),post.getPost_photos_path2()};
        FireBase.downloadImage(post.getPost_photos_path(),binding.postPhotoSwitcher);

        if(!post.getPublisher_id().equals(user.getUid()))
            binding.postDeleteBtn.setVisibility(View.GONE);
        binding.postDeleteBtn.setOnClickListener(view -> {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this post?");
                builder.setNegativeButton("Yes",(dialogInterface, i) ->{
                    viewModel.delete(post);
                    Intent intent = new Intent(this,HomePage.class);
                    startActivity(intent);
                });
                builder.setPositiveButton("Cancel",(dialogInterface, i) -> {
                });
                builder.show();

            });



        binding.prevBtn.setOnClickListener(view -> {
            if (counter == 0)
                counter = 2;
            else
                counter--;
            FireBase.downloadImage(photos[counter],binding.postPhotoSwitcher);
        });

        binding.nextBtn.setOnClickListener(view -> {
            if(counter==2)
                counter=0;
            else
                counter++;
            FireBase.downloadImage(photos[counter],binding.postPhotoSwitcher);
        });




      //  binding.postPhotoSwitcher.setImageURI(Uri.parse(post.getPost_context()));

    }
}