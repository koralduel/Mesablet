package com.example.mesablet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button logoutBtn;
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
    }
}