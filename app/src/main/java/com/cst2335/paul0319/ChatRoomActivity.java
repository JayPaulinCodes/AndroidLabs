package com.cst2335.paul0319;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    public final static String INTENT_MESSAGE_TEXT = "Intent_Message_Text";
    public final static String INTENT_MESSAGE_ID = "Intent_Message_Id";
    public final static String INTENT_MESSAGE_TYPE = "Intent_Message_Type";

    private final ArrayList<MessageObject> messages = new ArrayList<>(  );
    MyOpenHelper myOpener;
    SQLiteDatabase Database;
    MyListAdapter listAdapter;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        FrameLayout frame_layout = findViewById(R.id.chatRoom_frame_layout);

        isTablet = (frame_layout != null);

        myOpener = new MyOpenHelper(this);
        Database = myOpener.getWritableDatabase();

        Cursor results = Database.rawQuery(
                            String.format(
                               "Select * from %s;",
                               MyOpenHelper.TABLE_NAME
                            ),
                null);

        printCursor(results);

        int id_index = results.getColumnIndex(MyOpenHelper.COL_ID);
        int message_index = results.getColumnIndex(MyOpenHelper.COL_MESSAGE);
        int send_or_receive_index = results.getColumnIndex(MyOpenHelper.COL_SEND_RECEIVE);

        while (results.moveToNext()) {
            MessageObject message_object = null;
            int id = results.getInt(id_index);
            int send_or_receive = results.getInt(send_or_receive_index);
            String message = results.getString(message_index);


            // 0 = send, 1 = receive
            if (send_or_receive == 0) message_object = new SendMessageObject(message, id);
            else if (send_or_receive == 1) message_object = new ReceiveMessageObject(message, id);

            if (message_object != null) messages.add(message_object);
        }



        Button button_send = findViewById(R.id.chatRoom_button_send);
        Button button_receive = findViewById(R.id.chatRoom_button_receive);
        EditText editText_message = findViewById(R.id.chatRoom_editText_message);

        ListView listView_messages = findViewById(R.id.chatRoom_listView_messages);
        listView_messages.setAdapter(listAdapter = new MyListAdapter());

        // Handle button push
        button_send.setOnClickListener(click -> buttonPush(editText_message, MessageType.SEND));
        button_receive.setOnClickListener(click -> buttonPush(editText_message, MessageType.RECEIVE));

        // Handle long push to delete message
        listView_messages.setOnItemLongClickListener( (p, b, position, id) -> {
            String alertMessage = getString(R.string.chatRoom_alertDialog_message);
            alertMessage = alertMessage.replace("%row%", String.valueOf(position));
//            alertMessage = alertMessage.replace("%id%", String.valueOf(id));
            alertMessage = alertMessage.replace("%id%", Long.toString(listAdapter.getItemId(position)));

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getString(R.string.chatRoom_alertDialog_title))
                    .setMessage(alertMessage)
                    .setPositiveButton(getString(R.string.chatRoom_alertDialog_yes), (click, arg) -> {
                        Database.delete(
                                MyOpenHelper.TABLE_NAME,
                                String.format("%s = ?", MyOpenHelper.COL_ID),
                                new String[] { Long.toString(listAdapter.getItemId(position)) }
                        );
                        messages.remove(position);
                        listAdapter.notifyDataSetChanged();
                        if (isTablet) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            Fragment frag = fragmentManager.findFragmentById(R.id.chatRoom_frame_layout);

                            if (frag != null) {
                                fragmentManager
                                        .beginTransaction()
                                        .remove(frag)
                                        .commit();
                            }

                        }
                    })
                    .setNegativeButton(getString(R.string.chatRoom_alertDialog_no), (click, arg) -> {})
                    .create()
                    .show();
            return true;
        });

        listView_messages.setOnItemClickListener((list, view, position, id) -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DetailsFragment detailsFragment = new DetailsFragment();
            MessageObject messageObject = (MessageObject) listAdapter.getItem(position);
            Bundle args = new Bundle();

            args.putString(INTENT_MESSAGE_TEXT, messageObject.getContent());
            args.putLong(INTENT_MESSAGE_ID, messageObject.getId());
            args.putString(INTENT_MESSAGE_TYPE, messageObject.getMessageType().toString());

            detailsFragment.setArguments(args);

            if (isTablet) {

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.chatRoom_frame_layout, detailsFragment)
                        .commit();

            } else {
                Intent goToEmptyActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);

                goToEmptyActivity.putExtra(INTENT_MESSAGE_TEXT, args.getString(INTENT_MESSAGE_TEXT));
                goToEmptyActivity.putExtra(INTENT_MESSAGE_ID, args.getLong(INTENT_MESSAGE_ID));
                goToEmptyActivity.putExtra(INTENT_MESSAGE_TYPE, args.getString(INTENT_MESSAGE_TYPE));

                startActivity(goToEmptyActivity, args);
            }
        });
    }

    private void buttonPush(EditText editText, MessageType type) {
        // Get content from EditText
        String content = editText.getText().toString();

        // Make sure the text isn't empty
        if (content.isEmpty()) return;

        // Create MessageObject
        MessageObject message_object = null;
        if (type.equals(MessageType.SEND)) message_object = new SendMessageObject(content);
        else if (type.equals(MessageType.RECEIVE)) message_object = new ReceiveMessageObject(content);

        // Add into database
        ContentValues new_row = new ContentValues();
        new_row.put(MyOpenHelper.COL_MESSAGE, content);
        new_row.put(MyOpenHelper.COL_SEND_RECEIVE, message_object.sendOrReceiveValue());

        // Get row ID
        long id = Database.insert(
                MyOpenHelper.TABLE_NAME,
                null,
                new_row
        );

        // Assign Row ID
        message_object.setId(id);

        // Add to list
        messages.add(message_object);

        // Clear content from EditText
        editText.setText("");

        // NotifyDataSetChanged
        listAdapter.notifyDataSetChanged();
    }

    @SuppressLint("DefaultLocale")
    private void printCursor(Cursor cursor) {
        int id_index = cursor.getColumnIndex(MyOpenHelper.COL_ID);
        int message_index = cursor.getColumnIndex(MyOpenHelper.COL_MESSAGE);
        int send_or_receive_index = cursor.getColumnIndex(MyOpenHelper.COL_SEND_RECEIVE);
        String column_names = "";
        String rows = "";

        for (int i = 0; i < cursor.getColumnNames().length; i++) {
            if (i == 0) column_names = column_names.concat(cursor.getColumnNames()[i]);
            else column_names = column_names.concat(", " + cursor.getColumnNames()[i]);
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(id_index);
            int send_or_receive = cursor.getInt(send_or_receive_index);
            String message = cursor.getString(message_index);

            rows = rows.concat(String.format(
                "Row #%d, _id: %d, sendOrReceive: %d, message: %s%n",
                id,
                id,
                send_or_receive,
                message
            ));
        }

        cursor.moveToPosition(-1);

        String log = String.format(
                "Database Version Number %d%nNumber Of Columns: %d%n"  +
                "Column Names: %s%nNumber Of Rows In The Cursor: %d%nRow Results:%n%s%n",
                MyOpenHelper.VERSION,
                cursor.getColumnCount(),
                column_names,
                cursor.getCount(),
                rows
        );

        Log.i("PRINT_CURSOR", log);
    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() { return messages.size(); }

        @Override
        public Object getItem(int position) { return messages.get(position); }

        @Override
        public long getItemId(int position) { return messages.get(position).getId(); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MessageObject msgObject = (MessageObject) getItem(position);

            return msgObject.buildView(getLayoutInflater(), parent);
        }


    }

}


