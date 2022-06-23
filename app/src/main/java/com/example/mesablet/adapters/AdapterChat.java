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
import com.example.mesablet.activities.ChatActivity;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.entities.Chat;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ViewHolder> {

    List<Chat> chats;
    LayoutInflater layoutInflater;
    ClickInterface clickInterface;


    public AdapterChat(List<Chat> chats, Context context) {
        this.chats = chats;
        this.layoutInflater = LayoutInflater.from(context);
        this.clickInterface = (ChatActivity)context;
    }

    @NonNull
    @Override
    public AdapterChat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view, clickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.ViewHolder holder, int i) {

        String sender_fullName = chats.get(i).getSender_fullName();
        holder.sender_fullName.setText(sender_fullName);

        String image_sender_path = chats.get(i).getImage_sender_path();
        FireBase.downloadImage(chats.get(i).getImage_sender_path(),holder.sender_Profile_photo);





    }

    @Override
    public int getItemCount() {
        if (chats != null)
            return chats.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sender_fullName;
        ImageView sender_Profile_photo;

        public ViewHolder(@NonNull View viewItem, ClickInterface clickInterface) {
            super(viewItem);
            sender_fullName = viewItem.findViewById(R.id.sender_name);
            sender_Profile_photo=viewItem.findViewById(R.id.IV_profilePhoto);

            viewItem.setOnClickListener(v -> {
                if (clickInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickInterface.OnItemClick(position);
                    }
                }
            });
        }
    }
}
