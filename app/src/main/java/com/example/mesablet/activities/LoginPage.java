package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mesablet.fragments.LoginFragment;
import com.example.mesablet.R;
import com.example.mesablet.fragments.RegisterFragment;
import com.example.mesablet.adapters.VPadapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpage);

        tabLayout.setupWithViewPager(viewPager);
        VPadapter vPadapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vPadapter.addFragment(new LoginFragment(),"Login");
        vPadapter.addFragment(new RegisterFragment(),"Register");
        viewPager.setAdapter(vPadapter);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(this, HomePage.class));
        }
    }
}