package com.onsystem.ftpserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@Service
public class FilesManager {

    @Autowired
    private ILogger logger;
    private final Path path_initial=Path.of("."+File.separator+"storage");


    public Optional<File> createDir(String... name){
        try{
            URI uri = new URI(path_initial.toUri()+getPathBuilder(name));
            File file = new File(uri);

            if(!file.mkdirs())
                throw new FileSystemException("Cant create directory");

            return Optional.of(file);
        }catch (Exception e){
            logger.logWarning(getClass(),"Cant create Dir");
        }
        return Optional.empty();
    }

    public Optional< File > createNewFile(String... path){
        try{

            URI uri = new URI(path_initial.toUri()+getPathBuilder(path));
            File file = new File(uri);

            if(!file.createNewFile())
                throw new FileSystemException("Cant create directory");

            return Optional.of(file);
        }catch (Exception e){
            logger.logWarning(getClass(),"Cant create Dir");
        }
        return Optional.empty();
    }
    private String getPathBuilder(String... paths){
        StringBuilder pathBuilder = new StringBuilder();

        for (String path:
             paths) {
            pathBuilder.append(File.separator)
                    .append(path);
        }

        return pathBuilder.toString();
    }

}
