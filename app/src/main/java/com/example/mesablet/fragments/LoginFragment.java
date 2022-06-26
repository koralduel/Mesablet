package com.example.mesablet.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mesablet.R;
import com.example.mesablet.activities.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {


    private Button BtnLogin;
    private FirebaseAuth firebaseAuth;

    private TextInputEditText email_value, password_value;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email_value = view.findViewById(R.id.email_value);
        password_value = view.findViewById(R.id.password_value);
        BtnLogin = view.findViewById(R.id.BtnLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        BtnLogin.setOnClickListener(view1 -> {
            String email = email_value.getText().toString();
            String password = password_value.getText().toString();
            if (TextUtils.isEmpty(email)) {
                email_value.setError("Email cannot be null");
                email_value.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                password_value.setError("Password cannot be null");
                password_value.requestFocus();
            }
            else{
                loginUser(email,password);
            }
        });
        return view;
    }

    private void loginUser(String email, String password) {
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(),"Login Success",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), HomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }else {
                        Toast.makeText(getActivity(),"Login Error: "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
