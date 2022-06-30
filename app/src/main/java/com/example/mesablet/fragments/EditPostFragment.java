package com.example.mesablet.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.mesablet.R;
import com.example.mesablet.activities.PostPage;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.entities.Post;
import com.example.mesablet.viewmodels.PostsViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.grpc.okhttp.internal.Util;

public class EditPostFragment extends DialogFragment implements View.OnClickListener {

    StorageReference storageRef;

    private static final int GALLERY_CODE1 = 1;
    private static final int GALLERY_CODE2 = 2;
    private static final int GALLERY_CODE3 = 3;
    private Post post;
    Uri uri1,uri2,uri3;

    ImageView upload_photo;
    ImageView upload_photo2;
    ImageView upload_photo3;

    PostsViewModel viewModel =new PostsViewModel();


    public EditPostFragment(Bundle bundle) {
        this.post = (Post) bundle.getSerializable("post");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        storageRef = FirebaseStorage.getInstance().getReference();

        View view = inflater.inflate(R.layout.fragment_edit_post,container,false);
        EditText start_date = view.findViewById(R.id.start_date);
        EditText end_date = view.findViewById(R.id.end_date);
        EditText address_value = view.findViewById(R.id.address_value);
        EditText price_value = view.findViewById(R.id.price_value);
        EditText description_value = view.findViewById(R.id.description_value);
        upload_photo = view.findViewById(R.id.upload_photo);
        upload_photo2 = view.findViewById(R.id.upload_photo2);
        upload_photo3 = view.findViewById(R.id.upload_photo3);
        Button saveBtn = view.findViewById(R.id.saveBtn);
        Button cancelBtn = view.findViewById(R.id.cancelBtn);


        uri1 = Uri.parse(post.getPost_photos_path());
        uri2 = Uri.parse(post.getPost_photos_path1());
        uri3 = Uri.parse(post.getPost_photos_path2());

        start_date.setText(post.getStartDate());
        end_date.setText(post.getEndDate());
        address_value.setText(post.getAddress());
        price_value.setText(post.getPrice());
        description_value.setText(post.getPost_context());
        FireBase.downloadImage(post.getPost_photos_path(),upload_photo);
        FireBase.downloadImage(post.getPost_photos_path1(),upload_photo2);
        FireBase.downloadImage(post.getPost_photos_path2(),upload_photo3);

        upload_photo.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE1);
        });

        upload_photo2.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE2);
        });

        upload_photo3.setOnClickListener(view1 -> {
            //add new photo from camera/gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_CODE3);
        });


        saveBtn.setOnClickListener(view1 -> {
            if(!TextUtils.isEmpty(address_value.getText()) && !TextUtils.isEmpty(price_value.getText()) &&
                    !TextUtils.isEmpty(description_value.getText())
                    && !TextUtils.isEmpty(start_date.getText()) && !TextUtils.isEmpty(end_date.getText())){

                Post newPost = new Post(post);
                newPost.setAddress(address_value.getText().toString());
                newPost.setPrice(price_value.getText().toString());
                newPost.setPost_context(description_value.getText().toString());
                newPost.setStartDate(start_date.getText().toString());
                newPost.setEndDate(end_date.getText().toString());

                if(!uri1.toString().equals(post.getPost_photos_path()))
                    FireBase.UploadImage("Posts",post.getId(),"Post_image1",uri1);
                if(!uri2.toString().equals(post.getPost_photos_path1()))
                    FireBase.UploadImage("Posts",post.getId(),"Post_image2",uri2);
                if(!uri3.toString().equals(post.getPost_photos_path2()))
                    FireBase.UploadImage("Posts",post.getId(),"Post_image3",uri3);

                storageRef = FirebaseStorage.getInstance().getReference();
                newPost.setPost_photos_path(storageRef.child("Posts").child(post.getId()).child("Post_image1").toString());
                newPost.setPost_photos_path1(storageRef.child("Posts").child(post.getId()).child("Post_image2").toString());
                newPost.setPost_photos_path2(storageRef.child("Posts").child(post.getId()).child("Post_image3").toString());

                viewModel.updatePost(newPost);


                Intent intent = new Intent(getActivity(),PostPage.class);
                intent.putExtra("post",newPost);
                startActivity(intent);
                getDialog().dismiss();

            }

        });

        cancelBtn.setOnClickListener(view1 -> {getDialog().dismiss();});


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode == GALLERY_CODE1) {
                uri1 = data.getData();
                upload_photo.setImageURI(uri1);
            }
            else if(requestCode ==GALLERY_CODE2) {
                uri2 = data.getData();
                upload_photo2.setImageURI(uri2);
            }
            else if(requestCode == GALLERY_CODE3) {
                uri3 = data.getData();
                upload_photo3.setImageURI(uri3);
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
