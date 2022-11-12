package com.onsystem.ftpserver.configuration;


import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Level;

public class Logger implements ILogger{


    private String loggerName;
    private static final String SEPARATOR = " - ";
    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Constanst.APPLICATION_NAME);


    @Override
    public void logWarning(Class< ? > aClass,String message) {
        logger.log(Level.WARNING,log(aClass,message));
    }

    @Override
    public void logInfo(Class< ? > aClass,String message) {
        logger.log(Level.INFO,log(aClass,message));
    }

    private String log(Class< ? > aClass,String message){
        return aClass.getName().toLowerCase()+SEPARATOR+message;
    }
}
