package com.example.mesablet.activities;

import static java.util.UUID.randomUUID;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mesablet.R;
import com.example.mesablet.adapters.Adapter_post;
import com.example.mesablet.adapters.ClickInterface;
import com.example.mesablet.databinding.ActivityHomePageBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements ClickInterface {

    private ActivityHomePageBinding binding;
    FirebaseAuth firebaseAuth;
    Adapter_post adapter;
    FirebaseUser user;
    List<Post> posts;

    private PostsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        viewModel= new ViewModelProvider(this).get(PostsViewModel.class);

        binding.topAppBar.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getTitle().equals("Add post")) {
                Intent intent = new Intent(this, CreatePost.class);
                startActivity(intent);
            }
            if(menuItem.getTitle().equals("messenger")){
                Intent intent = new Intent(this,ChatActivity.class);
                startActivity(intent);
            }
            return true;
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item ->{
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
            return true;
        });


        posts =new ArrayList<>();
        Uri image = Uri.parse("android.resource://com.example.mesablet/drawable/ic_image");
        Uri profilePhoto = Uri.parse("android.resource://com.example.mesablet/drawable/ic_image");
        String id=randomUUID().toString();
        RecyclerView recyclerView = findViewById(R.id.RV_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new Adapter_post(this,posts);
        recyclerView.setAdapter(adapter);

        //refresh refresh layout -reload new data
        SwipeRefreshLayout refreshLayout=findViewById(R.id.refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.reload();
            }
        });

        viewModel.get().observe(this,p->{
            adapter.setPosts(p);
            refreshLayout.setRefreshing(false);
        });

    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this,PostPage.class);
        intent.putExtra("post",posts.get(position));
        startActivity(intent);
    }

    @Override
    public void OnItemLongClick(int position) {
        viewModel.delete(posts.get(position));
        posts.remove(position);
    }
}