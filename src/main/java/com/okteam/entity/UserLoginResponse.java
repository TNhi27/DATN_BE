package com.okteam.entity;

import lombok.Data;

@Data
public class UserLoginResponse {

    String username;
    String role;
    String token;
    String image;
    String shopid;

}