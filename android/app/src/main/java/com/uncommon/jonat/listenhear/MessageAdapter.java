package com.uncommon.jonat.listenhear;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

/**
 * Created by jonat on 2/10/2018.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, R.layout.item_message, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }

        TextView ts = (TextView) convertView.findViewById(R.id.timestamp);
        TextView txt = (TextView) convertView.findViewById(R.id.message);

        ts.setText(msg.timestamp);
        txt.setText(msg.message);
        return convertView;
    }

}
