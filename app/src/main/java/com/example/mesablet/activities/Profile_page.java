package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.R;
import com.example.mesablet.adapters.GridAdapter;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.databinding.ActivityProfilePageBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.fragments.EditProfile;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Profile_page extends AppCompatActivity {

    private ActivityProfilePageBinding binding;
    String[] images = {"android.resource://com.example.mesablet/drawable/ic_image","android.resource://com.example.mesablet/drawable/ic_image"};
    GridAdapter gridAdapter;
    List<Post> myPosts;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //FirebaseUser user;
    FirebaseAuth firebaseAuth;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
       // user = FirebaseAuth.getInstance().getCurrentUser();
        intent= getIntent();
        String userUid=intent.getStringExtra("userUid");
        String user_fullname=intent.getStringExtra("user_fullname");
        String user_imagepath=intent.getStringExtra("user_imagepath");



        List<Post> posts = new ArrayList<>();
        PostsViewModel viewModel = new PostsViewModel();
        posts.addAll(viewModel.get().getValue());
        //String uid = user.getUid();
        myPosts = new ArrayList<>();
        for (Post p: posts) {
            if(p.getPublisher_id().equals(userUid))
                myPosts.add(p);
        }

        List<String> photos = new ArrayList<>();
        for (Post p : myPosts) {
            photos.add(p.getPost_photos_path());
        }
        binding.TVPostsNumber.setText(String.valueOf(myPosts.size()));
        gridAdapter = new GridAdapter(this,photos);
        binding.photoGrid.setAdapter(gridAdapter);

        binding.photoGrid.setOnItemClickListener((adapterView, view, i, l) -> {
            Post selectedPost = myPosts.get(i);
            Intent intent = new Intent(this,PostPage.class);
            intent.putExtra("post",selectedPost);
            startActivity(intent);
        });

        FireBase.downloadImage(user.getPhotoUrl().toString(),binding.IVProfilePhoto);
        binding.TvFullName.setText(user_fullname);
        binding.topAppBar.setTitle(user_fullname);
        
        binding.BtnSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_page.this, MessagePage.class);
                intent.putExtra("user1",userUid);
                intent.putExtra("user2",user.getUid());
                intent.putExtra("user1_image_Path",user_imagepath);
                intent.putExtra("user2_image_Path",user.getPhotoUrl().toString());
                intent.putExtra("user1_fullName",user_fullname);
                intent.putExtra("user2_fullname",user.getDisplayName());
                startActivity(intent);
            }
        });



        binding.bottomNavigation.setSelectedItemId(R.id.profile);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item ->{
            Intent intent;
            if(item.getTitle().equals(getString(R.string.home))){
                intent = new Intent(this,HomePage.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals(getString(R.string.add_post))){
                intent=new Intent(this, CreatePost.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals(getString(R.string.profile))){ }
            else if(item.getTitle().equals(getString(R.string.logout))){
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginPage.class));
            }
            return true;
        });

        binding.BtnEditProfile.setOnClickListener(view -> {
            EditProfile editProfile = new EditProfile();
            editProfile.show(getSupportFragmentManager(),"EditProfileDialog");
        });

        if(user.getUid().equals(userUid))
            binding.BtnSentMessage.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bottomNavigation.setSelectedItemId(R.id.profile);
    }
}