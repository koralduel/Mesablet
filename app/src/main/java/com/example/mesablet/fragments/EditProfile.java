package com.example.mesablet.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mesablet.R;
import com.example.mesablet.activities.PostPage;
import com.example.mesablet.activities.Profile_page;
import com.example.mesablet.data.FireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditProfile extends DialogFragment {

    private static final int GALLERY_CODE1 = 1;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Uri uri;
    ImageView profile_image;
    StorageReference storageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        uri = user.getPhotoUrl();

        storageRef = FirebaseStorage.getInstance().getReference();

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        EditText fullname_field = view.findViewById(R.id.fullname_field);
        profile_image = view.findViewById(R.id.profile_image);
        Button saveBtn = view.findViewById(R.id.saveBtn);
        Button cancelBtn = view.findViewById(R.id.cancelBtn);
        fullname_field.setText(user.getDisplayName());
        FireBase.downloadImage(user.getPhotoUrl().toString(),profile_image);

        profile_image.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE1);
        });

        saveBtn.setOnClickListener(view1 -> {
            if(!TextUtils.isEmpty(fullname_field.getText().toString())){
                if(!uri.equals(user.getPhotoUrl()))
                    FireBase.UploadImage("Users",user.getUid(),"ProfileImg",uri);
                uri= Uri.parse(storageRef.child("Users").child(user.getUid()).child("ProfileImg").toString());

                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                        .setDisplayName(fullname_field.getText().toString())
                        .setPhotoUri(uri).build();
                user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) { }
                });
                Intent intent = new Intent(getActivity(), Profile_page.class);
                startActivity(intent);
                getDialog().dismiss();
            }
        });

        cancelBtn.setOnClickListener(view1 -> {
            getDialog().dismiss();
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == GALLERY_CODE1) {
                uri = data.getData();
                profile_image.setImageURI(uri);
            }
        }
    }
}