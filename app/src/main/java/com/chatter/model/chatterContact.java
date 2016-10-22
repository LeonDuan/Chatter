package com.chatter.model;

public class ChatterContact {
    private String user;
    private String email;
    private String photoUrl;

    public ChatterContact(){}

    public ChatterContact(String user, String email, String photoUrl){
        this.user = user;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public String getUser(){
        return user;
    }

    public String getEmail(){return email;}

    public String getPhotoUrl(){
        return photoUrl;
    }

    public void setUser(String user){
        this. user = user;
    }

    public void setEmail(String email){this.email = email;}

    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
