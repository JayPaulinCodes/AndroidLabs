package com.cst2335.paul0319;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MessageObject {

    private String content;
    private long id;
    private MessageType messageType;

    public MessageObject(MessageType messageType, String content) {
        setMessageType(messageType);
        setContent(content);
    }

    public MessageObject(MessageType messageType, String content, long id) {
        setMessageType(messageType);
        setContent(content);
        setId(id);
    }

    public void setContent(String content) { this.content = content; }

    public String getContent() { return content; }

    public void setId(long id) { this.id = id; }

    public long getId() { return id; }

    private void setMessageType(MessageType messageType) { this.messageType = messageType; }

    public MessageType getMessageType() { return this.messageType; }

    public int sendOrReceiveValue() {
        int value = -1;

        if (getMessageType().equals(MessageType.SEND)) value = 0;
        else if (getMessageType().equals(MessageType.RECEIVE)) value = 1;

        return value;
    }

    public boolean isSent() {
        boolean value = false;

        if (getMessageType().equals(MessageType.SEND)) value = true;
        else if (getMessageType().equals(MessageType.RECEIVE)) value = false;

        return value;
    }

    public boolean isReceived() {
        boolean value = false;

        if (getMessageType().equals(MessageType.SEND)) value = false;
        else if (getMessageType().equals(MessageType.RECEIVE)) value = true;

        return value;
    }

    public abstract View buildView(LayoutInflater inflater, ViewGroup parent);
}
