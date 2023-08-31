package com.sparkfire.squirmulu.entity.response;

public class LoginResponse {
    String token;
    String id;

    public LoginResponse() {
    }

    public LoginResponse(String token, String id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
