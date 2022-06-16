package com.example.mesablet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.mesablet.R;
import com.example.mesablet.adapters.GridAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Favorite_page extends AppCompatActivity {


    GridView photoGrid;
    String[] images = {"android.resource://com.example.mesablet/drawable/ic_image","android.resource://com.example.mesablet/drawable/ic_image"};
    GridAdapter gridAdapter;
    BottomNavigationView bottomNavigationView;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        setContentView(R.layout.activity_favorite_page);
        photoGrid = findViewById(R.id.photoGrid);
        gridAdapter = new GridAdapter(this,images);
        photoGrid.setAdapter(gridAdapter);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.favorites);

        bottomNavigationView.setOnNavigationItemSelectedListener(item ->{
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