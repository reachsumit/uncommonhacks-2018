package com.uncommon.jonat.listenhear;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by jonat on 2/10/2018.
 */

public class ViewMessageFragment extends Fragment {

    ArrayAdapter<Message> mAdapter;
    ArrayList<Message> mMessages;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessages = new ArrayList<>();
        while(!ListenService.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            RemoteMessage rm = ListenService.pop();
            String time = format.format(rm.getSentTime());
            mMessages.add(new Message(time, rm.getNotification().getBody()));
        }
        mAdapter = new MessageAdapter(getActivity(), mMessages);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ListView lv = view.findViewById(R.id.list_message);
        lv.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);

        Button refresh = getActivity().findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while(!ListenService.isEmpty()) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    RemoteMessage rm = ListenService.pop();
                    String time = format.format(rm.getSentTime());
                    mMessages.add(new Message(time, rm.getNotification().getBody()));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
