package com.example.mesablet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mesablet.LoginFragment;
import com.example.mesablet.R;
import com.example.mesablet.RegisterFragment;
import com.example.mesablet.adapters.VPadapter;
import com.google.android.material.tabs.TabLayout;

public class LoginPage extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
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


    }
}