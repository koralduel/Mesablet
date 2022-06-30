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
        String Post_owner_UID=intent.getStringExtra("Post_owner_UID");
        String Post_owner_name=intent.getStringExtra("Post_owner_name");
        String Post_owner_profileImg=intent.getStringExtra("Post_owner_profileImg");

        FireBase.downloadImage(Post_owner_profileImg,binding.IVProfilePhoto);

        binding.userChatWith.setText(Post_owner_name);
        readMessage(user.getUid(),Post_owner_UID,Post_owner_profileImg,Post_owner_name);

        binding.BtnSend.setOnClickListener(v -> {
            String msg=binding.ETMessageContent.getText().toString();
            if(!msg.equals("")){
                sendMessage(user.getUid(),Post_owner_UID,msg);
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

        public void readMessage(String myid, String userid, String image_Path,String Post_owner_name){
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
                   chatHashMap.put("otherUser_image_Path",image_Path);
                   chatHashMap.put("otherUser_fullname",Post_owner_name);
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