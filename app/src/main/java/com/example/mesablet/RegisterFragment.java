package com.example.mesablet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mesablet.activities.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterFragment extends Fragment {

    private static final int GALLERY_CODE = 1;
    TextInputEditText username_value,fullname_value,email_value,password_value;
    ImageView upload_profile_btn;
    Button BtnRegister;
    private Uri imageUri = Uri.parse("android.resource://com.example.mesablet/drawable/ic_upload_profile");

    String uid;


    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        username_value = view.findViewById(R.id.username_value);
        fullname_value = view.findViewById(R.id.fullname_value);
        email_value = view.findViewById(R.id.email_value);
        password_value = view.findViewById(R.id.password_value);
        BtnRegister = view.findViewById(R.id.BtnRegister);
        upload_profile_btn = view.findViewById(R.id.upload_profile_btn);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");


        BtnRegister.setOnClickListener(view1 -> {
            createUser();
        });

        upload_profile_btn.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE);
        });

        return view;
    }

    private void createUser() {

        String username = username_value.getText().toString();
        String fullname = fullname_value.getText().toString();
        String email = email_value.getText().toString();
        String password = password_value.getText().toString();
        String profile_photo = imageUri.toString();

        if(TextUtils.isEmpty(username)){
            username_value.setError("Username cannot be null");
            username_value.requestFocus();
        }else if(TextUtils.isEmpty(fullname)) {
            fullname_value.setError("Full Name cannot be null");
            fullname_value.requestFocus();
        }else if(TextUtils.isEmpty(email)) {
            email_value.setError("Email cannot be null");
            email_value.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            password_value.setError("Password cannot be null");
            password_value.requestFocus();
        }else{

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(),"User Created",Toast.LENGTH_LONG).show();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fullname)
                                .build();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Log.d("Success","Profile Updated");
                            }
                        });
                        uploadProfilePhoto();
                        startActivity(new Intent(getActivity(), LoginPage.class));
                    }else{
                        Toast.makeText(getActivity(),"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void uploadProfilePhoto() {

        storageReference = FirebaseStorage.getInstance().getReference("Users/"+uid);
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(),"Upload success",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_CODE){
            if(data != null){
                imageUri = data.getData();
                upload_profile_btn.setImageURI(imageUri);
            }
        }
    }


}