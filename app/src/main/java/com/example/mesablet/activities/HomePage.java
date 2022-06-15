package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mesablet.R;
import com.example.mesablet.adapters.Adapter_post;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Adapter_post adapter;
    BottomNavigationView bottomNavigationView;
    TextView TV_Name;
    FirebaseUser user;

    DatabaseReference databaseReference;
    private PostsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        bottomNavigationView=findViewById(R.id.bottom_navigation);


        TV_Name = findViewById(R.id.TV_Name);
        TV_Name.setText(user.getDisplayName());

     //   viewModel= new ViewModelProvider(this).get(PostsViewModel.class);

        bottomNavigationView.setOnNavigationItemReselectedListener(item ->{
            if(item.getTitle().equals("Home")){
                //stay in this page
            }
            else if(item.getTitle().equals("Favorites")){
                Intent intent=new Intent(this, Favorite_page.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Profile")){
                Intent intent=new Intent(this, Profile_page.class);
                startActivity(intent);
            }
            else if(item.getTitle().equals("Logout")){
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginPage.class));
            }
                });


        List<Post> posts =new ArrayList<>();

        posts.add(new Post("123","koral duel",android.R.drawable.ic_dialog_email,"new post","Ashkenazi 68,Tel Aviv","50-60"));
        RecyclerView recyclerView = findViewById(R.id.RV_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new Adapter_post(this,posts);
        recyclerView.setAdapter(adapter);

        //refresh refresh layout -reload new data
        SwipeRefreshLayout refreshLayout=findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(()->{
            viewModel.reload();
        });


        viewModel.get().observe(this,p->{
            adapter.setPosts(p);
            refreshLayout.setRefreshing(false);
        });

    }
}