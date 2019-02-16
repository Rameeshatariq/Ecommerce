package com.example.cv.e_commerce.Model;

import android.media.Image;

public class Users {
    private String Password, Phone, UserName, Image, Address;

    public Users() {

    }

    public Users(String password, String phone, String userName, String image, String address) {
        Password = password;
        Phone = phone;
        UserName = userName;
        Image = image;
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
