package com.chatter.model;

public class ChatterMessage {

    private String text;
    private String name;
    private String photoUrl;

    public ChatterMessage() {
    }
    public ChatterMessage(String text, String name){
        this.text = text;
        this.name = name;
    }

    public ChatterMessage(String text, String name, String photoUrl) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
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
}
