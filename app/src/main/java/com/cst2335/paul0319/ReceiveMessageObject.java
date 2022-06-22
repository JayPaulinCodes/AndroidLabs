package com.cst2335.paul0319;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReceiveMessageObject extends MessageObject {

    public ReceiveMessageObject(String content) {
        super(MessageType.RECEIVE, content);
    }

    public ReceiveMessageObject(String content, long id) {
        super(MessageType.RECEIVE, content, id);
    }

    @Override
    public View buildView(LayoutInflater inflater, ViewGroup parent) {

        View newView = inflater.inflate(R.layout.receive_message_row_layout, parent, false);

        TextView textView_messageContent = newView.findViewById(R.id.receiveMessageRowLayout_text);
        textView_messageContent.setText(getContent());

        return newView;
    }

}
