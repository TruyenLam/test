package com.it.quanlyxevai.model;

public class LoginRequest {

    private String UserID;
    private String PassWord;

    public LoginRequest(String userID, String passWord) {
        UserID = userID;
        PassWord = passWord;
    }
}
