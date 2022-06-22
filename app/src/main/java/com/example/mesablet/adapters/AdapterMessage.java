package com.example.mesablet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mesablet.R;
import com.example.mesablet.activities.MessagePage;
import com.example.mesablet.data.FireBase;
import com.example.mesablet.entities.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.ViewHolder>{

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private LayoutInflater layoutInflater;
    private List<Message> data;
    FirebaseUser user;
    DatabaseReference reference;
    MessagePage messagePage=new MessagePage();

    public AdapterMessage(Context layoutInflater, List<Message> data) {
        this.layoutInflater =  LayoutInflater.from(layoutInflater);
        this.data = data;
    }

    @NonNull
    @Override
    public AdapterMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view = layoutInflater.inflate(R.layout.chat_item_right,viewGroup,false);
            return new AdapterMessage.ViewHolder(view);
        }else{
            View view = layoutInflater.inflate(R.layout.chat_item_left,viewGroup,false);
            return new AdapterMessage.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMessage.ViewHolder viewHolder, int i) {

    Message message=data.get(i);

    viewHolder.show_message.setText(message.getMessage());
    //String sender_img_path= "Users/"+message.getSender()+"/"+"ProfileImg";

    FirebaseDatabase.getInstance().getReference("Users").child(message.getSender()).child("profileImageUri").get()
            .addOnSuccessListener(dataSnapshot -> {
                FireBase.downloadImage(dataSnapshot.getValue().toString(), viewHolder.profile_img);
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d("koko",e.toString());
        }
    });

    }


    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       public TextView show_message;
       public ImageView profile_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
            profile_img=itemView.findViewById(R.id.profile_img);

        }
    }

    @Override
    public int getItemViewType(int position) {
        user= FirebaseAuth.getInstance().getCurrentUser();
        if(data.get(position).getSender().equals(user.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
