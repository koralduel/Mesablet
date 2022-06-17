package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mesablet.R;
import com.example.mesablet.adapters.AdapterChat;
import com.example.mesablet.adapters.ClickInterface;
import com.example.mesablet.entities.Chat;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements ClickInterface {

    List<Chat> chats;
    AdapterChat adapterChat;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView=findViewById(R.id.RV_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterChat= new AdapterChat(chats,this);
        recyclerView.setAdapter(adapterChat);
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