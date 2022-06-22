package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.R;
import com.example.mesablet.adapters.GridAdapter;
import com.example.mesablet.databinding.ActivityFavoritePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Favorite_page extends AppCompatActivity {



    String[] images = {"android.resource://com.example.mesablet/drawable/ic_image","android.resource://com.example.mesablet/drawable/ic_image"};
    GridAdapter gridAdapter;
    private ActivityFavoritePageBinding binding;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        gridAdapter = new GridAdapter(this,images);
        binding.photoGrid.setAdapter(gridAdapter);
        binding.bottomNavigation.setSelectedItemId(R.id.favorites);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item ->{
            Intent intent;
            if(item.getTitle().equals("Home")){
                intent = new Intent(this,HomePage.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Favorites")){ }
            else if(item.getTitle().equals("Profile")){
                intent=new Intent(this, Profile_page.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Logout")){
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginPage.class));
            }
            return true;
        });
    }
}