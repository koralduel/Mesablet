package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.R;
import com.example.mesablet.adapters.GridAdapter;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.databinding.ActivityProfilePageBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Profile_page extends AppCompatActivity {

    private ActivityProfilePageBinding binding;
    String[] images = {"android.resource://com.example.mesablet/drawable/ic_image","android.resource://com.example.mesablet/drawable/ic_image"};
    GridAdapter gridAdapter;
    List<Post> myPosts;

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
        gridAdapter = new GridAdapter(this,photos);
        binding.photoGrid.setAdapter(gridAdapter);

        binding.photoGrid.setOnItemClickListener((adapterView, view, i, l) -> {
            Post selectedPost = myPosts.get(i);
            Intent intent = new Intent(this,PostPage.class);
            intent.putExtra("post",selectedPost);
            startActivity(intent);
        });

        FireBase.downloadImage(posts.get(0).getPublisher_image_path(),binding.IVProfilePhoto);
        binding.TvFullName.setText(user_fullname);
        binding.topAppBar.setTitle(user_fullname);



        binding.bottomNavigation.setSelectedItemId(R.id.profile);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item ->{
            Intent intent;
            if(item.getTitle().equals("Home")){
                intent = new Intent(this,HomePage.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Favorites")){
                intent=new Intent(this, Favorite_page.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Profile")){ }
            else if(item.getTitle().equals("Logout")){
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginPage.class));
            }
            return true;
        });
    }
}