package com.example.mesablet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button logoutBtn;
    Adapter_post adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();
        logoutBtn = findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(view -> {
           firebaseAuth.signOut();
            startActivity(new Intent(this,LoginPage.class));
        });

        List<Post> posts=new ArrayList<>();

        posts.add(new Post(android.R.drawable.ic_dialog_email,"koral duel",android.R.drawable.ic_dialog_email,"new post"));
        RecyclerView recyclerView = findViewById(R.id.RV_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new Adapter_post(this,posts);
        recyclerView.setAdapter(adapter);
    }
}