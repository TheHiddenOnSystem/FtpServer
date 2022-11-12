package com.onsystem.ftpserver.model.request;

import lombok.Data;


@Data
public class UserRegisterRequest {
    private String password;
    private String userName;
    private String email;
}
