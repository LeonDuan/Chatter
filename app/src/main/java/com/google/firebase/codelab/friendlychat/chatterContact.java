package com.google.firebase.codelab.friendlychat;

import java.util.ArrayList;

public class chatterContact {
    private String user;
    private ArrayList<String> contacts;
    private ArrayList<String> photoUrl;

    public chatterContact(){}

    public chatterContact(String user, ArrayList<String> contacts, ArrayList<String> photoUrl){
        this.user = user;
        this.contacts = new ArrayList<>(contacts);
        this.photoUrl = new ArrayList<>(contacts);
    }

    public String getUser(){
        return user;
    }

    public ArrayList<String> getContacts(){
        return contacts;
    }

    public ArrayList<String> getPhotoUrl(){
        return photoUrl;
    }

    public void setUser(String user){
        this. user = user;
    }

    public void setContacts(ArrayList<String> contacts){
        this.contacts = new ArrayList<>(contacts);
    }

    public void setPhotoUrl(ArrayList<String> photoUrl){
        this.photoUrl = new ArrayList<>(photoUrl);
    }
}
