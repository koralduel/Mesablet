package com.example.mesablet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mesablet.R;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.entities.Post;

import java.util.List;

public class Adapter_post extends RecyclerView.Adapter<Adapter_post.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<Post> data;
    ;


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

        String publisher_name= data.get(i).getPublisher_name();
        viewHolder.publisher_name.setText(publisher_name);

        String post_context=data.get(i).getPost_context();
        viewHolder.post_context.setText(post_context);

        String post_Address=data.get(i).getAddress();
        viewHolder.post_Address.setText(post_Address);

        String post_Price=data.get(i).getPrice();
        viewHolder.post_Price.setText(post_Price);

        String publisher_image = data.get(i).getPublisher_image_path();
        FireBase.downloadImage(data.get(i).getPublisher_image_path(),viewHolder.publisher_image);

        String post_photos = data.get(i).getPost_photos_path();
        FireBase.downloadImage(data.get(i).getPost_photos_path(),viewHolder.post_photos);

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
