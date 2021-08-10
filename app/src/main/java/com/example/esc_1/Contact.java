package com.example.esc_1;

public class Contact {
    String name;
    String phoneNumber;

    public Contact(){}

    public Contact(String phoneNumber, String name){
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getPhoneNumber(){return phoneNumber;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

}
