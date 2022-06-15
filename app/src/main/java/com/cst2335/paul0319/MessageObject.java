package com.cst2335.paul0319;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MessageObject extends Object {

    private String content;

    public MessageObject(String content) {
        setContent(content);
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public abstract View buildView(LayoutInflater inflater, ViewGroup parent);
}
