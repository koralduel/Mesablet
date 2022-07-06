package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mesablet.R;
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
    String help;

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
        String user1=intent.getStringExtra("user1");
        String user2 = intent.getStringExtra("user2");
        String user1_image_Path=intent.getStringExtra("user1_image_Path");
        String user2_image_Path=intent.getStringExtra("user2_image_Path");
        String user1_fullName=intent.getStringExtra("user1_fullName");
        String user2_fullname= intent.getStringExtra("user2_fullname");

        String me,my_image,my_fullName;
        String otherUser_UID,other_image,other_fullname;
        if(user.getUid().equals(user1)){
            me=user1;
            my_image=user1_image_Path;
            my_fullName=user1_fullName;
            otherUser_UID=user2;
            other_image=user2_image_Path;
            other_fullname=user2_fullname;
        }
        else{
            me=user2;
            my_image=user2_image_Path;
            my_fullName=user2_fullname;
            otherUser_UID=user1;
            other_image=user1_image_Path;
            other_fullname=user1_fullName;

        }
        FireBase.downloadImage(other_image,binding.IVProfilePhoto);
        binding.userChatWith.setText(other_fullname);
        readMessage(me, otherUser_UID, my_image,other_image,my_fullName,other_fullname);

        binding.BtnBackChats.setOnClickListener(view -> {finish();});

        binding.BtnSend.setOnClickListener(v -> {
            String msg=binding.ETMessageContent.getText().toString();
            if(!msg.equals("")){
                sendMessage(user.getUid(),otherUser_UID,msg);
            }else{
                Toast.makeText(this, getString(R.string.not_empty), Toast.LENGTH_SHORT).show();
            }
            binding.ETMessageContent.setText("");
        });

    }

        private void sendMessage(String sender,String receiver,String message){

           // DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats").child(sender+receiver);
            reference=FirebaseDatabase.getInstance().getReference("Chats").child(help);
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("sender",sender);
            hashMap.put("receiver",receiver);
            hashMap.put("message",message);

            reference.push().setValue(hashMap);
        }

        public void readMessage(String myid, String userid, String user1_image_Path,String user2_image_Path,String user1_fullName,String user2_fullname){
            help="";
           reference=FirebaseDatabase.getInstance().getReference("Chats");
           reference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                   if(datasnapshot.getValue()==null){
                       help=myid+userid;
                   }
                   for (DataSnapshot snapshot:datasnapshot.getChildren()) {
                       if(snapshot.getKey().equals(myid+userid)){
                          help=myid+userid;
                       }
                       else if(snapshot.getKey().equals(userid+myid)){
                           help=userid+myid;
                       }
                       else{
                           help=myid+userid;
                       }
                   }
                   HashMap<String,Object> chatHashMap=new HashMap<>();
                   chatHashMap.put("user1",myid);
                   chatHashMap.put("user2",userid);
                   chatHashMap.put("user1_image_Path",user1_image_Path);
                   chatHashMap.put("user1_fullname",user1_fullName);
                   chatHashMap.put("user2_image_Path",user2_image_Path);
                   chatHashMap.put("user2_fullname",user2_fullname);
                   reference.child(help).child("users").setValue(chatHashMap);

                   reference=FirebaseDatabase.getInstance().getReference("Chats");
                   reference.child(help).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                           Messages=new ArrayList<>();
                               for (DataSnapshot s: snapshot.getChildren()) {
                                   if(!s.getKey().equals("users")){
                                       Message message = s.getValue(Message.class);
                                       if (message.getReceiver().equals(myid) && message.getSender().equals(userid) ||
                                               message.getReceiver().equals(userid) && message.getSender().equals(myid)) {
                                           Messages.add(message);
                                       }
                                       adapterMessage = new AdapterMessage(MessagePage.this, Messages);
                                       binding.RVMessages.setAdapter(adapterMessage);
                                   }

                                   }
                               }



                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
    }
}