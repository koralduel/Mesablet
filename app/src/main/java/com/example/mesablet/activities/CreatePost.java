package com.example.mesablet.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mesablet.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreatePost extends AppCompatActivity {

    private static final int GALLERY_CODE = 2;
    //Firebase connection objects
    FirebaseUser user;

    ImageView backBtn,upload_photo;
    Button createBtn;
    EditText ET_Enter_Address,ET_Enter_Price,ET_description_value;
    private Uri imageUri = Uri.parse("android.resource://com.example.mesablet/drawable/ic_upload_profile");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        ET_Enter_Address = findViewById(R.id.ET_Enter_Address);
        ET_Enter_Price = findViewById(R.id.ET_Enter_Price);
        ET_description_value = findViewById(R.id.ET_description_value);
        backBtn = findViewById(R.id.backBtn);
        upload_photo = findViewById(R.id.upload_photo);
        createBtn = findViewById(R.id.createBtn);

        createBtn.setOnClickListener(view -> {
            String address = ET_Enter_Address.getText().toString();
            String price = ET_Enter_Price.getText().toString();
            String description = ET_description_value.getText().toString();

            //Validation check
            if(!TextUtils.isEmpty(address) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(description)){
                //Todo: Add new post
            }else
                Toast.makeText(this,"You must enter address,price and description",Toast.LENGTH_LONG).show();
        });

        upload_photo.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE);
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE){
            if(data != null){
                imageUri = data.getData();
                upload_photo.setImageURI(imageUri);
            }
        }
    }
}