package com.chatter.model;

/**
 * Created by Leon on 10/21/2016.
 */

public class ChatterImageMessageAdapter implements ChatterMessageInterface {

    ChatterImageMessage imageMessage;
    public ChatterImageMessageAdapter(ChatterImageMessage imageMessage) {
        this.imageMessage = imageMessage;
    }
    @Override
    public String getText() {
        return imageMessage.getImageUrl();
    }
    @Override
    public void setText(String text) {
        imageMessage.setImageUr(text);
    }
    @Override
    public String getName() {
        return imageMessage.getName();
    }
    @Override
    public void setName(String name) {
        imageMessage.setName(name);
    }
    @Override
    public String getPhotoUrl() {
        return imageMessage.getPhotoUrl();
    }
    @Override
    public void setPhotoUrl(String photoUrl) {
        imageMessage.setPhotoUrl(photoUrl);
    }
}