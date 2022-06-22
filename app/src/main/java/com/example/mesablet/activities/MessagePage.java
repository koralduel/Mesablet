package com.example.mesablet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mesablet.R;
import com.example.mesablet.adapters.AdapterMessage;
import com.example.mesablet.data.FireBase;
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

    ImageView sender_Profile_Img;
    TextView sender_full_name;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ImageButton Btn_send;
    EditText ET_message_content;
    StorageReference storageReference;
    DatabaseReference reference;

    AdapterMessage adapterMessage;
    List<Message> Messages;

    RecyclerView RV_messages;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);

        RV_messages=findViewById(R.id.RV_messages);
        RV_messages.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        RV_messages.setLayoutManager(linearLayoutManager);

        sender_full_name=findViewById(R.id.user_chat_with);
        sender_Profile_Img=findViewById(R.id.IV_profilePhoto);
        Btn_send=findViewById(R.id.Btn_send);
        ET_message_content=findViewById(R.id.ET_message_content);
        storageReference = FirebaseStorage.getInstance().getReference();

        intent= getIntent();
        String Post_owner_UID=intent.getStringExtra("Post_owner_UID");
        String Post_owner_name=intent.getStringExtra("Post_owner_name");
        String Post_owner_profileImg=intent.getStringExtra("Post_owner_profileImg");

        FireBase.downloadImage(Post_owner_profileImg,sender_Profile_Img);

        sender_full_name.setText(Post_owner_name);
        readMessage(user.getUid(),Post_owner_UID,Post_owner_profileImg);

        Btn_send.setOnClickListener(v -> {
            String msg=ET_message_content.getText().toString();
            if(!msg.equals("")){
                sendMessage(user.getUid(),Post_owner_UID,msg);
            }else{
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            }
            ET_message_content.setText("");
        });

    }

        private void sendMessage(String sender,String receiver,String message){

            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats").child(sender+receiver);

            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("sender",sender);
            hashMap.put("receiver",receiver);
            hashMap.put("message",message);

            reference.push().setValue(hashMap);
        }

        public void readMessage(String myid, String userid, String image_Path){
            Messages=new ArrayList<>();

            reference = FirebaseDatabase.getInstance().getReference("Chats").child(myid+userid);
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
                        RV_messages.setAdapter(adapterMessage);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
}