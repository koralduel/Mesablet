package com.example.mesablet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.example.mesablet.R;
import com.example.mesablet.adapters.GridAdapter;

public class Favorite_page extends AppCompatActivity {


    GridView photoGrid;
    String[] images = {"android.resource://com.example.mesablet/drawable/ic_image","android.resource://com.example.mesablet/drawable/ic_image"};
    GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_page);
        photoGrid = findViewById(R.id.photoGrid);

        gridAdapter = new GridAdapter(this,images);
        photoGrid.setAdapter(gridAdapter);
    }
}