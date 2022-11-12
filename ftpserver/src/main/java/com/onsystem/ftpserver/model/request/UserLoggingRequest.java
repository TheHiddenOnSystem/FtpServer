package com.onsystem.ftpserver.model.request;

import lombok.Data;

@Data
public class UserLoggingRequest {
    private String name;
    private String password;
}
