package com.example.hamzazamir.food_wheels;

/**
 * Created by HamzaZamir on 10/12/2017.
 */
public class UserData {
    private String name;
    private String email;
    private String contact;
    private String address;
    private String type;

    public UserData(String name, String email, String contact, String address,String type){
            this.name = name;
            this.email = email;
            this.contact = contact;
            this.address = address;
            this.type = type;
        }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




}
