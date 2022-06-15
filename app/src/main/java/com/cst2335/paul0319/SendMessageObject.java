package com.cst2335.paul0319;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SendMessageObject extends MessageObject {

    public SendMessageObject(String content) {
        super(content);
    }

    @Override
    public View buildView(LayoutInflater inflater, ViewGroup parent) {

        View newView = inflater.inflate(R.layout.send_message_row_layout, parent, false);

        TextView textView_messageContent = newView.findViewById(R.id.sendMessageRowLayout_text);
        textView_messageContent.setText(getContent());

        return newView;
    }
}
