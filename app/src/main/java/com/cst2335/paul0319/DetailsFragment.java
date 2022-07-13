package com.cst2335.paul0319;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    private static final String ARG_MESSAGE_TEXT = ChatRoomActivity.INTENT_MESSAGE_TEXT;
    private static final String ARG_MESSAGE_ID = ChatRoomActivity.INTENT_MESSAGE_ID;
    private static final String ARG_MESSAGE_TYPE = ChatRoomActivity.INTENT_MESSAGE_TYPE;

    private final DetailsFragment INSTANCE = this;

    private String messageText;
    private long messageId;
    private String messageType;

    public DetailsFragment() {
        // Required empty public constructor
    }

//    public static DetailsFragment newInstance(String messageText, long messageId, String messageType) {
//        DetailsFragment fragment = new DetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_MESSAGE_TEXT, messageText);
//        args.putLong(ARG_MESSAGE_ID, messageId);
//        args.putString(ARG_MESSAGE_TYPE, messageType);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                messageText = getArguments().getString(ARG_MESSAGE_TEXT);
                messageId = getArguments().getLong(ARG_MESSAGE_ID);
                messageType = getArguments().getString(ARG_MESSAGE_TYPE);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView messageHereText = view.findViewById(R.id.detailsFragment_message_text);
        TextView messageIdText = view.findViewById(R.id.detailsFragment_message_id);
        CheckBox sentMessageCheckbox = view.findViewById(R.id.detailsFragment_sent_message_checkbox);
        Button hideButton = view.findViewById(R.id.detailsFragment_hide_button);

        messageHereText.setText(messageText);
        messageIdText.setText(String.format("ID=%d", messageId));
        sentMessageCheckbox.setChecked((messageType == MessageType.SEND.toString()));



        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .remove(getParentFragmentManager().findFragmentById(R.id.chatRoom_frame_layout))
                        .commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}