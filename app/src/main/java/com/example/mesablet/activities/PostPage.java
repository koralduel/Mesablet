package com.example.mesablet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.example.mesablet.R;
import com.example.mesablet.interfaces.ICallable;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.databinding.ActivityPostPageBinding;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class PostPage extends AppCompatActivity implements ICallable {

    private ActivityPostPageBinding binding;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int counter = 0;
    PostsViewModel viewModel =new PostsViewModel();

    Post post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        post =(Post) getIntent().getSerializableExtra("post");

        FireBase.downloadImage(post.getPublisher_image_path(),binding.postOwnerPhoto);
        binding.postOwnerFullName.setText(post.getPublisher_name());
        binding.TVEnterAddress.setText(post.getAddress());
        binding.TVEnterPrice.setText(post.getPrice());
        binding.TvPostContent.setText(post.getPost_context());
        binding.TVStartDate.setText(post.getStartDate());
        binding.TVEndDate.setText(post.getEndDate());
        List<Bitmap> bitmapList = new ArrayList<>();
        FireBase.downloadImage(post.getPost_photos_path(),bitmapList,this);
        FireBase.downloadImage(post.getPost_photos_path1(),bitmapList,null);
        FireBase.downloadImage(post.getPost_photos_path2(),bitmapList,null);


        if(!post.getPublisher_id().equals(user.getUid()))
            binding.openMenuBtn.setVisibility(View.GONE);
        binding.openMenuBtn.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(PostPage.this,binding.openMenuBtn);
            popupMenu.getMenuInflater()
                    .inflate(R.menu.post_options,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getTitle().equals(getString(R.string.edit))){


                }
                else if(item.getTitle().equals(getString(R.string.delete))){
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                    builder.setTitle(getString(R.string.delete));
                    builder.setMessage(getString(R.string.sure_delete));
                    builder.setNegativeButton(getString(R.string.yes),(dialogInterface, i) ->{
                        viewModel.delete(post);
                        Intent intent = new Intent(this,HomePage.class);
                        startActivity(intent);
                    });
                    builder.setPositiveButton(getString(R.string.cancel),(dialogInterface, i) -> { });
                    builder.show();
                }
                return true;
            });
            popupMenu.show();

        });


        binding.prevBtn.setOnClickListener(view -> {
            if (counter == 0)
                counter = 2;
            else
                counter--;
            binding.postPhotoSwitcher.setImageBitmap(bitmapList.get(counter));
        });

        binding.nextBtn.setOnClickListener(view -> {
            if(counter==2)
                counter=0;
            else
                counter++;
            binding.postPhotoSwitcher.setImageBitmap(bitmapList.get(counter));
        });

    }

    @Override
    public void call(Bitmap bitmap) {
        binding.postPhotoSwitcher.setImageBitmap(bitmap);
    }
}