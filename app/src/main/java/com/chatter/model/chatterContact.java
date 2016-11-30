package com.chatter.model;

public class ChatterContact {
    private String email;
    private String photoUrl;

    public ChatterContact(){}

    public ChatterContact(String email){
        this.email = email;
        this.photoUrl = null;
    }

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public String getPhotoUrl(){return photoUrl;}

}
