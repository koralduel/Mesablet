package com.example.mesablet.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mesablet.R;
import com.example.mesablet.adapters.IntroAdapter;
import com.example.mesablet.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    final String SHOW_KEY = "show";
    private ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(isFirstTime()) {
            binding.pager.setAdapter(new IntroAdapter(getSupportFragmentManager()));
            binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
                @Override
                public void onPageSelected(int position) {
                    if(position == 3)
                        binding.BtnSkip.setText(R.string.next);
                    else
                        binding.BtnSkip.setText(R.string.skip);
                }
                @Override
                public void onPageScrollStateChanged(int state) { }
            });

            binding.dots.setupWithViewPager(binding.pager,true);
            binding.BtnSkip.setOnClickListener(v ->{
                SharedPreferences p = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = p.edit();
                editor.putBoolean(SHOW_KEY,false);
                editor.commit();

                Intent intent = new Intent(v.getContext(),LoginPage.class);
                startActivity(intent);
            });

        }
        else{
            Intent intent = new Intent(this,LoginPage.class);
            startActivity(intent);
            finish();
        }

    }

    private boolean isFirstTime(){
        SharedPreferences p = getPreferences(MODE_PRIVATE);
        return p.getBoolean(SHOW_KEY,true);
    }
}



