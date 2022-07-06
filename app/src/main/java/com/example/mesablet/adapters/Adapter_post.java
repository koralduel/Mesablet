package com.example.mesablet.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mesablet.R;
import com.example.mesablet.activities.HomePage;
import com.example.mesablet.activities.MessagePage;
import com.example.mesablet.activities.Profile_page;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.entities.Post;
import com.example.mesablet.interfaces.ClickInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Adapter_post extends RecyclerView.Adapter<Adapter_post.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<Post> data;
    ClickInterface clickInterface;
    FirebaseUser user;


    public Adapter_post(Context context, List<Post> data) {
        this.layoutInflater =  LayoutInflater.from(context);
        this.data = data;
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.clickInterface = (HomePage)context;
    }

    @NonNull
    @Override
    public Adapter_post.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.card_post_item,viewGroup,false);
        return new ViewHolder(view,clickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_post.ViewHolder viewHolder, int i) {

        String publisher_name= data.get(i).getPublisher_name();
        viewHolder.publisher_name.setText(publisher_name);
        viewHolder.publisher_name.setOnClickListener(v -> {
            Intent intent=new Intent(layoutInflater.getContext(), Profile_page.class);
            intent.putExtra("userUid",data.get(i).getPublisher_id());
            intent.putExtra("user_fullname",data.get(i).getPublisher_name());
            layoutInflater.getContext().startActivity(intent);
        });

        String post_context=data.get(i).getPost_context();
        viewHolder.post_context.setText(post_context);

        String post_Address=data.get(i).getAddress() +","+ data.get(i).getCity();
        viewHolder.post_Address.setText(post_Address);

        String post_Price=data.get(i).getPrice();
        viewHolder.post_Price.setText(post_Price+"$");

        String publisher_image = data.get(i).getPublisher_image_path();
        FireBase.downloadImage(data.get(i).getPublisher_image_path(),viewHolder.publisher_image);

        String post_photos = data.get(i).getPost_photos_path();
        FireBase.downloadImage(data.get(i).getPost_photos_path(),viewHolder.post_photos);

        String start_date = data.get(i).getStartDate();
        viewHolder.start_date.setText(start_date);

        String end_date = data.get(i).getEndDate();
        viewHolder.end_date.setText(end_date);

        if(user.getUid().equals(data.get(i).getPublisher_id())){
            viewHolder.btn_send_message.setEnabled(true);
            viewHolder.btn_send_message.setVisibility(View.GONE);
        }else{
            viewHolder.btn_send_message.setOnClickListener(v -> {
                Intent intent=new Intent(layoutInflater.getContext(), MessagePage.class);
                intent.putExtra("user1",data.get(i).getPublisher_id());
                intent.putExtra("user2",user.getUid());
                intent.putExtra("user1_image_Path",publisher_image);
                intent.putExtra("user2_image_Path",user.getPhotoUrl().toString());
                intent.putExtra("user1_fullName",data.get(i).getPublisher_name());
                intent.putExtra("user2_fullname",user.getDisplayName());
                layoutInflater.getContext().startActivity(intent);
            });
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
        Button btn_send_message;
        Button btn_add_favorite;
        ImageView post_photos;
        TextView publisher_name,post_context,post_Address,post_Price,start_date,end_date;

        public ViewHolder(@NonNull View itemView,ClickInterface clickInterface) {
            super(itemView);
            publisher_image=itemView.findViewById(R.id.IV_profilePhoto);
            post_photos=itemView.findViewById(R.id.IV_card_photo);
            publisher_name=itemView.findViewById(R.id.TV_publisher_name);
            post_context=itemView.findViewById(R.id.Tv_post_content);
            post_Address=itemView.findViewById(R.id.TV_Enter_Address);
            post_Price=itemView.findViewById(R.id.TV_Enter_Price);
            btn_send_message=itemView.findViewById(R.id.btn_send_message);
            start_date = itemView.findViewById(R.id.TV_Start_Date);
            end_date = itemView.findViewById(R.id.TV_End_Date);



            itemView.setOnClickListener(v -> {
                if(clickInterface!=null){
                    int position= getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        clickInterface.OnItemClick(position);
                    }
                }
            });

        }


        }
    }

