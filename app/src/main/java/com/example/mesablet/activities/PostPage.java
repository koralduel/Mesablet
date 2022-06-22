package com.example.mesablet.activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.R;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.entities.Post;

public class PostPage extends AppCompatActivity {

    Post post;
    ImageView postOwnerPhoto;
    TextView postOwnerFullName,TV_Enter_Address,TV_Enter_Price,Tv_post_content;
    ImageSwitcher postPhotoSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        post =(Post) getIntent().getSerializableExtra("post");

        postOwnerPhoto=findViewById(R.id.postOwnerPhoto);
        postOwnerFullName=findViewById(R.id.postOwnerFullName);
        TV_Enter_Address=findViewById(R.id.TV_Enter_Address);
        TV_Enter_Price=findViewById(R.id.TV_Enter_Price);
        Tv_post_content=findViewById(R.id.Tv_post_content);
        postPhotoSwitcher=findViewById(R.id.postPhotoSwitcher);

        FireBase.downloadImage(post.getPublisher_image_path(),postOwnerPhoto);
        postOwnerFullName.setText(post.getPublisher_name());
        TV_Enter_Address.setText(post.getAddress());
        TV_Enter_Price.setText(post.getPrice());
        Tv_post_content.setText(post.getPost_context());
        postPhotoSwitcher.setImageURI(Uri.parse(post.getPost_context()));

    }
}