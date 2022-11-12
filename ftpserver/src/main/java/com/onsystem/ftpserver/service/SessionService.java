package com.onsystem.ftpserver.service;

public interface SessionService {
    boolean login(String username, String password);
    boolean logout();
}
