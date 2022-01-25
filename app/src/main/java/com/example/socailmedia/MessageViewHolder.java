package com.example.socailmedia;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView sendertv,receivertv;
    ImageView iv_sender,iv_receiver;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

    }
    public void Setmessage(Application application ,  String message, String time, String date,String type,
                           String senderuid,String receiveruid){

        sendertv = itemView.findViewById(R.id.sender_tv);
        receivertv = itemView.findViewById(R.id.receiver_tv);

        iv_receiver = itemView.findViewById(R.id.iv_receiver);
        iv_sender = itemView.findViewById(R.id.iv_sender);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = user.getUid();


        if (type.equals("text")){
            if (currentUid.equals(senderuid)){
                receivertv.setVisibility(View.GONE);
                sendertv.setText(message);

            }else if (currentUid.equals(receiveruid)){
                sendertv.setVisibility(View.GONE);
                receivertv.setText(message);

            }
        }else if (type.equals("iv")){

            if (currentUid.equals(senderuid)){
                receivertv.setVisibility(View.GONE);
                sendertv.setVisibility(View.GONE);
                iv_sender.setVisibility(View.VISIBLE);
                Picasso.get().load(message).into(iv_sender);
            }else if (currentUid.equals(receiveruid)){
                receivertv.setVisibility(View.GONE);
                sendertv.setVisibility(View.GONE);
                iv_receiver.setVisibility(View.VISIBLE);
                Picasso.get().load(message).into(iv_receiver);
            }

        }else {

        }






    }



}
