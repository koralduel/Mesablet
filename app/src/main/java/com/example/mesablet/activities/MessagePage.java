package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mesablet.adapters.AdapterMessage;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.databinding.ActivityMessagePageBinding;
import com.example.mesablet.entities.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessagePage extends AppCompatActivity {

    private ActivityMessagePageBinding binding;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storageReference;
    DatabaseReference reference;

    AdapterMessage adapterMessage;
    List<Message> Messages;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.RVMessages.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        binding.RVMessages.setLayoutManager(linearLayoutManager);


        storageReference = FirebaseStorage.getInstance().getReference();

        intent= getIntent();
        String Post_owner_UID=intent.getStringExtra("Post_owner_UID");
        String Post_owner_name=intent.getStringExtra("Post_owner_name");
        String Post_owner_profileImg=intent.getStringExtra("Post_owner_profileImg");

        FireBase.downloadImage(Post_owner_profileImg,binding.IVProfilePhoto);

        binding.userChatWith.setText(Post_owner_name);
        readMessage(user.getUid(),Post_owner_UID,Post_owner_profileImg);

        binding.BtnSend.setOnClickListener(v -> {
            String msg=binding.ETMessageContent.getText().toString();
            if(!msg.equals("")){
                sendMessage(user.getUid(),Post_owner_UID,msg);
            }else{
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            }
            binding.ETMessageContent.setText("");
        });

    }

        private void sendMessage(String sender,String receiver,String message){

           // DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats").child(sender+receiver);
            FirebaseDatabase.getInstance().getReference("Chats").get().addOnSuccessListener(dataSnapshot -> {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if(snapshot.getValue().equals(sender+receiver)){
                        reference=FirebaseDatabase.getInstance().getReference("Chats").child(sender+receiver);
                    }else{
                        reference =FirebaseDatabase.getInstance().getReference("Chats").child(receiver+sender);
                    }
                }
            });
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("sender",sender);
            hashMap.put("receiver",receiver);
            hashMap.put("message",message);

            reference.push().setValue(hashMap);
        }

        public void readMessage(String myid, String userid, String image_Path){
            Messages=new ArrayList<>();
            reference=FirebaseDatabase.getInstance().getReference("Chats").child(myid+userid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    Messages.clear();
                    for (DataSnapshot snapshot:datasnapshot.getChildren()) {
                        Message message=snapshot.getValue(Message.class);
                        if(message.getReceiver().equals(myid) && message.getSender().equals(userid) ||
                            message.getReceiver().equals(userid) && message.getSender().equals(myid)){
                            Messages.add(message);
                        }
                        adapterMessage= new AdapterMessage(MessagePage.this,Messages);
                        binding.RVMessages.setAdapter(adapterMessage);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
}