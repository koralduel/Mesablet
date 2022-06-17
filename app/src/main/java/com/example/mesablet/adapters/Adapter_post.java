package com.example.mesablet.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mesablet.R;
import com.example.mesablet.entities.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Adapter_post extends RecyclerView.Adapter<Adapter_post.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<Post> data;
    StorageReference storageReference;
    Bitmap bitmap = null;

    public Adapter_post(Context layoutInflater, List<Post> data) {
        this.layoutInflater =  LayoutInflater.from(layoutInflater);
        this.data = data;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @NonNull
    @Override
    public Adapter_post.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.card_post_item,viewGroup,false);
        return new Adapter_post.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_post.ViewHolder viewHolder, int i) {

        String publisher_name= data.get(i).getPublisher_name();
        viewHolder.publisher_name.setText(publisher_name);

        String post_context=data.get(i).getPost_context();
        viewHolder.post_context.setText(post_context);

        String post_Address=data.get(i).getAddress();
        viewHolder.post_Address.setText(post_Address);

        String post_Price=data.get(i).getPrice();
        viewHolder.post_Price.setText(post_Price);

        String publisher_image = data.get(i).getPublisher_image_path();
        downloadImage(data.get(i).getId(),"Publisher_image",publisher_image);
        if(bitmap != null)
            viewHolder.publisher_image.setImageBitmap(bitmap);

        String post_photos = data.get(i).getPost_photos_path();
        downloadImage(data.get(i).getId(),"Post_image",post_photos);
        if(bitmap != null)
            viewHolder.post_photos.setImageBitmap(bitmap);



    }

    private void downloadImage(String id, String type, String publisher_image)
    {
        storageReference.child(id).child(type).child(publisher_image);
        try {
            bitmap = null;
            File localFile = File.createTempFile("tempfile",".jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setPosts(List<Post> s){
        data = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else return 0;
    }

    public List<Post> getPosts() {
        return data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView publisher_image;
        ImageView post_photos;
        TextView publisher_name,post_context,post_Address,post_Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            publisher_image=itemView.findViewById(R.id.IV_profilePhoto);
            post_photos=itemView.findViewById(R.id.IV_card_photo);
            publisher_name=itemView.findViewById(R.id.TV_publisher_name);
            post_context=itemView.findViewById(R.id.Tv_post_content);
            post_Address=itemView.findViewById(R.id.TV_Enter_Address);
            post_Price=itemView.findViewById(R.id.TV_Enter_Price);

        }
    }
}
