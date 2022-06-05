package com.example.mesablet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    TextInputEditText username_value,fullname_value,email_value,password_value;
    Button BtnRegister;
    FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

        BtnRegister.setOnClickListener(view1 -> {
            createUser();
        });

        return view;
    }

    private void createUser() {

        String username = username_value.getText().toString();
        String fullname = fullname_value.getText().toString();
        String email = email_value.getText().toString();
        String password = password_value.getText().toString();

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
                        startActivity(new Intent(getActivity(),LoginPage.class));
                    }else{
                        Toast.makeText(getActivity(),"Registration Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}