package com.onsystem.ftpserver.utils;

public interface ILogger {

    void logWarning(Class< ? > aClass,String message);
    void logInfo(Class< ? > aClass,String message);
}
