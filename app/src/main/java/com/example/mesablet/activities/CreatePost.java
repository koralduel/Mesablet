package com.example.mesablet.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.databinding.ActivityCreatePostBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CreatePost extends AppCompatActivity {

    private static final int GALLERY_CODE = 2;
    //Firebase connection objects
    FirebaseUser user;
    PostsViewModel viewModel =new PostsViewModel();

    private ActivityCreatePostBinding binding;
    StorageReference storageRef;
    private Uri imageUri = Uri.parse("android.resource://com.example.mesablet/drawable/ic_upload_profile");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageRef = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        String publisher_photo= storageRef.child("Users").child(user.getUid()).child("ProfileImg").toString();
        String publisher_name = user.getDisplayName();

        binding.createBtn.setOnClickListener(view -> {
            String address = binding.ETEnterAddress.getText().toString();
            String price = binding.ETEnterPrice.getText().toString();
            String description = binding.ETDescriptionValue.getText().toString();

            //Validation check
            if(!TextUtils.isEmpty(address) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(description)){

                viewModel.add(new Post(publisher_photo,publisher_name,imageUri.toString(),description,address,price,user.getUid()));
                Intent intent = new Intent(this,HomePage.class);
                startActivity(intent);
            }else
                Toast.makeText(this,"You must enter address,price and description",Toast.LENGTH_LONG).show();
        });

        binding.uploadPhoto.setOnClickListener(view1 -> {
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
                binding.uploadPhoto.setImageURI(imageUri);
            }
        }
    }
}