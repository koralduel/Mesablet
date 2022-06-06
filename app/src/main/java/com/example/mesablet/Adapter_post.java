package com.example.mesablet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Adapter_post extends RecyclerView.Adapter<Adapter_post.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<Post> data;

    public Adapter_post(Context layoutInflater, List<Post> data) {
        this.layoutInflater =  LayoutInflater.from(layoutInflater);
        this.data = data;
    }

    @NonNull
    @Override
    public Adapter_post.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.card_post_item,viewGroup,false);
        return new Adapter_post.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_post.ViewHolder viewHolder, int i) {

        int publisher_image = data.get(i).getPublisher_image();
        viewHolder.publisher_image.setImageResource(publisher_image);

        int post_photos = data.get(i).getPost_photos();
        viewHolder.post_photos.setImageResource(post_photos);

        String publisher_name= data.get(i).getPublisher_name();
        viewHolder.publisher_name.setText(publisher_name);

        String post_context=data.get(i).getPost_context();
        viewHolder.post_context.setText(post_context);


    }

    @Override
    public int getItemCount() {return data.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView publisher_image;
        ImageView post_photos;
        TextView publisher_name,post_context;
        BottomNavigationView bottomNavigationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            publisher_image=itemView.findViewById(R.id.IV_profilePhoto);
            post_photos=itemView.findViewById(R.id.IV_card_photo);
            publisher_name=itemView.findViewById(R.id.TV_publisher_name);
            post_context=itemView.findViewById(R.id.Tv_post_content);
            bottomNavigationView=itemView.findViewById(R.id.bottom_navigation);
        }
    }
}
