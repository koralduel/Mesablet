package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mesablet.adapters.AdapterChat;
import com.example.mesablet.interfaces.ClickInterface;
import com.example.mesablet.databinding.ActivityChatBinding;
import com.example.mesablet.entities.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ClickInterface {

    ActivityChatBinding binding;
    List<Chat> chats;
    AdapterChat adapterChat;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.RVChats.setLayoutManager(new LinearLayoutManager(this));
        getAllChats();

        binding.BtnBackChats.setOnClickListener(view -> {finish();});


    }


    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, MessagePage.class);
        intent.putExtra("user1",chats.get(position).getUser1());
        intent.putExtra("user2",chats.get(position).getUser2());
        intent.putExtra("user1_image_Path",chats.get(position).getUser1_image_Path());
        intent.putExtra("user2_image_Path",chats.get(position).getUser2_image_Path());
        intent.putExtra("user1_fullName",chats.get(position).getUser1_fullname());
         intent.putExtra("user2_fullname",chats.get(position).getUser2_fullname());
        startActivity(intent);
    }


    public void getAllChats() {
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        if (snap.getKey().contains(user.getUid())) {
                            addchat(snap.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addchat(String chatid) {
        reference = FirebaseDatabase.getInstance().getReference("Chats").child(chatid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    chats = new ArrayList<>();
                    if (snap.getKey().equals("users")) {
                        Chat chat = snap.getValue(Chat.class);
                        if (chat.getUser2().equals(user.getUid()) || chat.getUser1().equals(user.getUid())) {
                            chats.add(chat);
                        }

                        adapterChat = new AdapterChat(chats, ChatActivity.this);
                        binding.RVChats.setAdapter(adapterChat);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
