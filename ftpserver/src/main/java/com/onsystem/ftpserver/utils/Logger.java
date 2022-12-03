package com.onsystem.ftpserver.utils;


import com.onsystem.ftpserver.configuration.Constanst;

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
    public void logWarning(Class<?> aClass, String message, Throwable ex) {
        logger.log(Level.WARNING,log(aClass,message,ex));
    }

    @Override
    public void logInfo(Class< ? > aClass,String message) {
        logger.log(Level.INFO,log(aClass,message));
    }

    @Override
    public void logInfo(Class<?> aClass, String message, Throwable ex) {
        logger.log(Level.INFO,log(aClass,message,ex));
    }

    private String log(Class< ? > aClass,String message){
        return aClass.getName().toLowerCase()+SEPARATOR+message;
    }
    private String log(Class< ? > aClass,String message,Throwable ex){
        ex.printStackTrace();
        return log(aClass,message)+"\n"+ex.getMessage();
    }
}
