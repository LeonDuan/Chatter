package com.chatter.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chatter.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Yunpeng Zhang on 10/22/2016.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView messageTextView;
    public TextView messengerTextView;
    public CircleImageView messengerImageView;

    public MessageViewHolder(View v){
        super(v);
        messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
        messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
        messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
    }

}
