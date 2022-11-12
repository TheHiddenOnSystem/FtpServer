package com.onsystem.ftpserver.dto;

import lombok.Data;


@Data
public class UserRegister {
    private String password;
    private String userName;
    private String email;
}
