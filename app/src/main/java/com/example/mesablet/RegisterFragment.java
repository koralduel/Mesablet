package com.example.mesablet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mesablet.activities.LoginPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class RegisterFragment extends Fragment {

    private static final int GALLERY_CODE = 1;
    TextInputEditText fullname_value,email_value,password_value;
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

        fullname_value = view.findViewById(R.id.fullname_value);
        email_value = view.findViewById(R.id.email_value);
        password_value = view.findViewById(R.id.password_value);
        BtnRegister = view.findViewById(R.id.BtnRegister);
        upload_profile_btn = view.findViewById(R.id.upload_profile_btn);

        mAuth = FirebaseAuth.getInstance();



        BtnRegister.setOnClickListener(view1 -> {
            String txt_fullName=fullname_value.getText().toString();
            String txt_email=email_value.getText().toString();
            String txt_password=password_value.getText().toString();
            if( txt_fullName.equals(null) || txt_email.equals(null) || txt_password.equals(null) ){
                Toast.makeText(getActivity(), R.string.fileds_required,Toast.LENGTH_SHORT).show();
            }
            else if(txt_password.length()<6){
                Toast.makeText(getActivity(), R.string.password_error,Toast.LENGTH_SHORT).show();
            }
            else {
                createUser(txt_fullName,txt_email,txt_password);
            }
        });

        upload_profile_btn.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE);
        });

        return view;
    }

    private void createUser(String fullname,String email,String password) {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(getActivity(), "User Created", Toast.LENGTH_LONG).show();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userID = user.getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userID);
                        hashMap.put("fullname", fullname);
                        hashMap.put("email", email);
                        hashMap.put("profileImageUri", imageUri.toString());

                        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), R.string.registerSuccessfully, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), LoginPage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
                    }else{
                            Toast.makeText(getActivity(), R.string.errorRegister,Toast.LENGTH_LONG).show();
                        }

                }

            });

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