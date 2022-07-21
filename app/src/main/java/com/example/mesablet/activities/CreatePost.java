package com.example.mesablet.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mesablet.R;
import com.example.mesablet.databinding.ActivityCreatePostBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreatePost extends AppCompatActivity {

    private static final int GALLERY_CODE1 = 1;
    private static final int GALLERY_CODE2 = 2;
    private static final int GALLERY_CODE3 = 3;
    //Firebase connection objects
    FirebaseUser user;
    PostsViewModel viewModel =new PostsViewModel();
    FirebaseAuth firebaseAuth;

    private ActivityCreatePostBinding binding;
    StorageReference storageRef;
    private Uri imageUri = Uri.parse("android.resource://com.example.mesablet/drawable/ic_upload_profile");
    private Uri imageUri2 = Uri.parse("android.resource://com.example.mesablet/drawable/ic_upload_profile");
    private Uri videoUri = Uri.parse("android.resource://com.example.mesablet/raw/sample_video");


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageRef = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();

        binding.backBtn.setOnClickListener(view -> {
            finish();
        });

        String publisher_photo= storageRef.child("Users").child(user.getUid()).child("ProfileImg").toString();
        String publisher_name = user.getDisplayName();

        MaterialDatePicker.Builder builder=MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(R.string.SELECT_A_DATE);
        final MaterialDatePicker materialDatePicker = builder.build();

        binding.BtnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                binding.EnterStartDate.setText(materialDatePicker.getHeaderText());
            }
        });

        MaterialDatePicker.Builder builder2=MaterialDatePicker.Builder.datePicker();
        builder2.setTitleText(R.string.SELECT_A_DATE);
        final MaterialDatePicker materialDatePicker2 = builder.build();


        binding.BtnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker2.show(getSupportFragmentManager(),"DATE_PICKER");
            }
        });

        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                binding.EnterEndDate.setText(materialDatePicker2.getHeaderText());


            }
        });


        binding.createBtn.setOnClickListener(view -> {
            String address = binding.ETEnterAddress.getText().toString();
            String price = binding.ETEnterPrice.getText().toString();
            String description = binding.ETDescriptionValue.getText().toString();
            String startDate = binding.EnterStartDate.getText().toString();
            String endDate = binding.EnterEndDate.getText().toString();
            String city = binding.ETEnterCity.getText().toString();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
            LocalDate datestart=null;
            LocalDate dateend=null;
            if(startDate.length()==11){
                datestart = LocalDate.parse(startDate, formatter2);
            }
            else if(startDate.length()==12){
                 datestart = LocalDate.parse(startDate, formatter);
            }
            if(endDate.length()==11){
                 dateend = LocalDate.parse(endDate, formatter2);
            }
            else if(endDate.length()==12){
                 dateend = LocalDate.parse(endDate, formatter);
            }

            LocalDate today=LocalDate.now();

            if( TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate) ||datestart.isBefore(today) || datestart.isAfter(dateend)){
                Toast.makeText(this, R.string.Date_is_not_valid,Toast.LENGTH_LONG).show();
            }

            //Validation check
            else if(!TextUtils.isEmpty(address) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(description)
                    && !TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(city)){

                viewModel.add(new Post(publisher_photo,publisher_name,imageUri.toString(),
                        imageUri2.toString(),videoUri.toString(),description,address,price,user.getUid(),
                        startDate,endDate,city,today.toString()));


                Intent intent = new Intent(this,HomePage.class);
                startActivity(intent);
            }else
                Toast.makeText(this, R.string.must_enter,Toast.LENGTH_LONG).show();
        });

        binding.uploadPhoto.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE1);
        });
        binding.uploadPhoto2.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE2);
        });
        binding.uploadVideo.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("video/*");
            startActivityForResult(galleryIntent,GALLERY_CODE3);
        });

        binding.bottomNavigation.setSelectedItemId(R.id.addPost);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item ->{
            if(item.getTitle().equals(getString(R.string.home))){
                Intent intent = new Intent(this,HomePage.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals(getString(R.string.add_post))){
                //Stay here
            }
            else if(item.getTitle().equals(getString(R.string.profile))){
                Intent intent=new Intent(this, Profile_page.class);
                intent.putExtra("userUid",user.getUid());
                intent.putExtra("user_fullname",user.getDisplayName());
                startActivity(intent);
            }
            else if(item.getTitle().equals(getString(R.string.logout))){
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginPage.class));
            }
            return true;
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == GALLERY_CODE1) {
                imageUri = data.getData();
                binding.uploadPhoto.setImageURI(imageUri);
            }
            else if(requestCode ==GALLERY_CODE2) {
                imageUri2 = data.getData();
                binding.uploadPhoto2.setImageURI(imageUri2);
            }
            else if(requestCode == GALLERY_CODE3) {
                videoUri = data.getData();
                binding.uploadVideo.setVideoURI(videoUri);
                binding.uploadVideo.start();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bottomNavigation.setSelectedItemId(R.id.addPost);
    }
}