package com.chatter.model;

import com.google.firebase.storage.StorageReference;

import static com.chatter.Constants.*;

public class ChatterMessage implements ChatterMessageInterface{
    private final int type;
    private String text;
    private String name;
    private String photoUrl;
    private String localUri;

    public ChatterMessage() {
        type = TEXT;
    }
    public ChatterMessage(String text, String name){
        this.text = text;
        this.name = name;
        type = TEXT;
    }


    public ChatterMessage(String text, String name, String photoUrl, int type) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.type = type;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getType()
    {
        return type;
    }

}
