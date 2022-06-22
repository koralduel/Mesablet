package com.example.mesablet.activities;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.data.FireBase;
import com.example.mesablet.databinding.ActivityPostPageBinding;
import com.example.mesablet.entities.Post;

public class PostPage extends AppCompatActivity {

    private ActivityPostPageBinding binding;

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
        binding.postPhotoSwitcher.setImageURI(Uri.parse(post.getPost_context()));

    }
}