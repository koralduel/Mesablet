package com.example.mesablet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mesablet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile_page extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(item ->{
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