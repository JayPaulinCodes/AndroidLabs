package com.cst2335.paul0319;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EmptyActivity extends AppCompatActivity {

    private String messageText;
    private long messageId;
    private String messageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Intent intent = getIntent();

        messageText = intent.getStringExtra(ChatRoomActivity.INTENT_MESSAGE_TEXT);
        messageId = intent.getLongExtra(ChatRoomActivity.INTENT_MESSAGE_ID, 0);
        messageType = intent.getStringExtra(ChatRoomActivity.INTENT_MESSAGE_TYPE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();

        args.putString(ChatRoomActivity.INTENT_MESSAGE_TEXT, messageText);
        args.putLong(ChatRoomActivity.INTENT_MESSAGE_ID, messageId);
        args.putString(ChatRoomActivity.INTENT_MESSAGE_TYPE, messageType);

        detailsFragment.setArguments(savedInstanceState);

        fragmentManager
                .beginTransaction()
                .replace(R.id.chatRoom_frame_layout, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}