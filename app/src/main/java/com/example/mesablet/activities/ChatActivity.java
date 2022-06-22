package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mesablet.adapters.AdapterChat;
import com.example.mesablet.adapters.ClickInterface;
import com.example.mesablet.databinding.ActivityChatBinding;
import com.example.mesablet.entities.Chat;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements ClickInterface {

    ActivityChatBinding binding;
    List<Chat> chats;
    AdapterChat adapterChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.RVChats.setLayoutManager(new LinearLayoutManager(this));
        adapterChat= new AdapterChat(chats,this);
        binding.RVChats.setAdapter(adapterChat);
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this,MessagePage.class);
        intent.putExtra("Message",chats.get(position));
        startActivity(intent);
    }

    @Override
    public void OnItemLongClick(int position) {

    }
}