package com.cst2335.paul0319;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<MessageObject> messages = new ArrayList<>(  );
    MyListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button button_send = findViewById(R.id.chatRoom_button_send);
        Button button_receive = findViewById(R.id.chatRoom_button_receive);
        EditText editText_message = findViewById(R.id.chatRoom_editText_message);

        ListView listView_messages = findViewById(R.id.chatRoom_listView_messages);
        listView_messages.setAdapter(listAdapter = new MyListAdapter());

        // Handle button push
        button_send.setOnClickListener(click -> { buttonPush(editText_message, MessageType.SEND); });
        button_receive.setOnClickListener(click -> { buttonPush(editText_message, MessageType.RECEIVE); });

        // Handle long push to delete message
        listView_messages.setOnItemLongClickListener( (p, b, position, id) -> {
            String alertMessage = getString(R.string.chatRoom_alertDialog_message);
            Log.i("XXX", "onCreate: " + alertMessage);
            alertMessage = alertMessage.replace("%row%", String.valueOf(position));
            Log.i("XXX", "onCreate: " + alertMessage);
            alertMessage = alertMessage.replace("%id%", String.valueOf(id));
            Log.i("XXX", "onCreate: " + alertMessage);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.chatRoom_alertDialog_title))
                    .setMessage(alertMessage)
                    .setPositiveButton(getString(R.string.chatRoom_alertDialog_yes), (click, arg) -> {
                        messages.remove(position);
                        listAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.chatRoom_alertDialog_no), (click, arg) -> {})
                    .create()
                    .show();
            return true;
        });
    }

    private void buttonPush(EditText editText, MessageType type) {
        // Get content from EditText
        String content = editText.getText().toString();

        // Create MessageObject
        if (type.equals(MessageType.SEND)) {
            MessageObject messageObject = new SendMessageObject(content);

            // Add MessageObject to list
            messages.add(messageObject);
        } else if (type.equals(MessageType.RECEIVE)) {
            MessageObject messageObject = new ReceiveMessageObject(content);

            // Add MessageObject to list
            messages.add(messageObject);
        }

        // Clear content from EditText
        editText.setText("");

        // NotifyDataSetChanged
        listAdapter.notifyDataSetChanged();
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() { return messages.size(); }

        @Override
        public Object getItem(int position) { return messages.get(position); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            MessageObject msgObject = (MessageObject) getItem(position);

            return msgObject.buildView(inflater, parent);
        }


    }

}


