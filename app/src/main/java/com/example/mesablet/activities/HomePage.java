package com.example.mesablet.activities;

import static java.util.UUID.randomUUID;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mesablet.R;
import com.example.mesablet.adapters.Adapter_post;
import com.example.mesablet.databinding.ActivityHomePageBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.fragments.CustomDialog;
import com.example.mesablet.interfaces.ClickInterface;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomePage extends AppCompatActivity implements ClickInterface {

    private ActivityHomePageBinding binding;
    FirebaseAuth firebaseAuth;
    Adapter_post adapter;
    FirebaseUser user;
    List<Post> posts;

    private PostsViewModel viewModel;

    Sensor acceleromoter;
    SensorManager sm;
    SensorEventListener sensorEventListener;
    private double sumCurrentValue,sumPreviousValue;
    private boolean itIsNotFirstTime = false;
    float disX,disY,disZ,lastX,lastY,lastZ;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        acceleromoter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener= new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event!=null){
                    float x_accel = event.values[0];
                    float y_accel = event.values[1];
                    float z_accel = event.values[2];


                    if(itIsNotFirstTime){
                        disX = Math.abs(lastX-x_accel);
                        disY = Math.abs(lastY-y_accel);
                        disZ = Math.abs(lastZ-z_accel);

                        if(disX > 2 && disY > 2 ||
                        disX > 2 && disZ > 2 ||
                        disY > 2 && disZ > 2){
                            executeShakeAction();
                            itIsNotFirstTime=false;
                        }

                    }
                    lastX = x_accel;
                    lastY = y_accel;
                    lastZ = z_accel;
                    itIsNotFirstTime=true;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }
        };

        viewModel= new ViewModelProvider(this).get(PostsViewModel.class);

        binding.topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getTitle().equals(getString(R.string.settings))) {
                CustomDialog customDialog=new CustomDialog();
                customDialog.show(getSupportFragmentManager(),"");
            }
            if(menuItem.getTitle().equals(getString(R.string.messenger))){
                Intent intent = new Intent(this,ChatActivity.class);
                startActivity(intent);
            }
            return true;
        });

        Locale current = getResources().getConfiguration().locale;

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item ->{
            if(item.getTitle().equals(getString(R.string.home))){
                //stay in this page
            }
            else if(item.getTitle().equals(getString(R.string.add_post))){
                Intent intent=new Intent(this, CreatePost.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals(getString(R.string.profile))){
                Intent intent=new Intent(this, Profile_page.class);
                intent.putExtra("userUid",user.getUid());
                intent.putExtra("user_fullname",user.getDisplayName());
                intent.putExtra("user_imagepath",user.getPhotoUrl().toString());
                startActivity(intent);
            }
            else if(item.getTitle().equals(getString(R.string.logout))){
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginPage.class));
            }
            return true;
        });


        posts =new ArrayList<>();
        Uri image = Uri.parse("android.resource://com.example.mesablet/drawable/ic_image");
        Uri profilePhoto = Uri.parse("android.resource://com.example.mesablet/drawable/ic_image");
        String id=randomUUID().toString();
        binding.RVPost.setLayoutManager(new LinearLayoutManager(this));
        adapter =new Adapter_post(this,posts);
        binding.RVPost.setAdapter(adapter);


        binding.BtnSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String city = binding.SearchView.getQuery().toString().toLowerCase(Locale.ROOT);
                List<Post> postBycity = new ArrayList<>();
                if(posts!=null && !city.equals("")){
                    for (Post p:posts) {
                        if(p.getCity().toLowerCase(Locale.ROOT).equals(city))
                            postBycity.add(p);
                    }
                    adapter =new Adapter_post(HomePage.this,postBycity);
                }
                else if (city.equals("")){
                    adapter =new Adapter_post(HomePage.this,posts);
                }
                binding.RVPost.setAdapter(adapter);



            }
        });
        
        //refresh refresh layout -reload new data
        executeShakeAction();

    }
    //do when shaking happens
    private void executeShakeAction() {
        binding.refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.SearchView.setQuery("",false);
                binding.SearchView.clearFocus();
                viewModel.reload();
            }
        });

        viewModel.get().observe(this,p->{
            posts.clear();
            posts.addAll(p);
            adapter.setPosts(p);
            binding.refreshlayout.setRefreshing(false);
        });

        Toast.makeText(this, R.string.Feed_updated, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this,PostPage.class);
        intent.putExtra("post",posts.get(position));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bottomNavigation.setSelectedItemId(R.id.home);
        sm.registerListener(sensorEventListener,acceleromoter,sm.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(sensorEventListener);
    }
}