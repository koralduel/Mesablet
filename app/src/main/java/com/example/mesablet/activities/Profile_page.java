package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.R;
import com.example.mesablet.databinding.ActivityProfilePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile_page extends AppCompatActivity {

    private ActivityProfilePageBinding binding;

    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


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