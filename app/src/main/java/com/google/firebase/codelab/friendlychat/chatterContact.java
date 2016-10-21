package com.google.firebase.codelab.friendlychat;

import java.util.ArrayList;

public class chatterContact {
    private String user;
    private String photoUrl;

    public chatterContact(){}

    public chatterContact(String user, String photoUrl){
        this.user = user;
        this.photoUrl = photoUrl;
    }

    public String getUser(){
        return user;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }

    public void setUser(String user){
        this. user = user;
    }

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
