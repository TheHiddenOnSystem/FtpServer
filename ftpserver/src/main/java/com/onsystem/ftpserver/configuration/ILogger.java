package com.onsystem.ftpserver.configuration;

public interface ILogger {

    void logWarning(Class< ? > aClass,String message);
    void logInfo(Class< ? > aClass,String message);
}
