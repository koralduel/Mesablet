package com.example.mesablet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Adapter_post adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemReselectedListener(item ->{
            if(item.getTitle().equals("Home")){
                //stay in this page
            }
            else if(item.getTitle().equals("Favorites")){
                Intent intent=new Intent(this,Favorite_page.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Profile")){
                Intent intent=new Intent(this,Profile_page.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Logout")){
                firebaseAuth.signOut();
                startActivity(new Intent(this,LoginPage.class));
            }
                });


        List<Post> posts=new ArrayList<>();

        posts.add(new Post(android.R.drawable.ic_dialog_email,"koral duel",android.R.drawable.ic_dialog_email,"new post"));
        RecyclerView recyclerView = findViewById(R.id.RV_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new Adapter_post(this,posts);
        recyclerView.setAdapter(adapter);
    }
}