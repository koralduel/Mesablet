package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mesablet.adapters.AdapterChat;
import com.example.mesablet.adapters.ClickInterface;
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
        adapterChat= new AdapterChat(chats,this);
        binding.RVChats.setAdapter(adapterChat);
       // getAllChats();
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this,MessagePage.class);
        intent.putExtra("Message",chats.get(position));
        startActivity(intent);
    }

    public void updateChats(){
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()){
                    if(snap.getKey().contains(user.getUid())){
                        chats=new ArrayList<>();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}