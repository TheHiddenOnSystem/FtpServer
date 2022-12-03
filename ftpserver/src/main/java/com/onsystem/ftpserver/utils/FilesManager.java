package com.onsystem.ftpserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FilesManager {
    private final String separator = File.separator;
    private final Path path_initial=Path.of("."+separator+"storage");

    @Autowired
    private ILogger logger;


    public Optional<File> createDir(String... name) {
        Optional<File> resultDirectory = Optional.empty();

        try {
            URI uri =  Path.of(path_initial.toAbsolutePath()+getPathBuilder(name)).toUri();
            File file = new File(uri);

            if(!file.mkdirs())
                throw new FileSystemException(uri.getPath());

            resultDirectory = Optional.of(file);
        }catch (Exception e){
            this.logger.logWarning(getClass(),"Cant create dir" ,e);
        }

        return resultDirectory;

    }

    public Optional< File > createNewFile(String... path) {
        try{

            URI uri = Path.of(path_initial.toAbsolutePath()+getPathBuilder(path)).toUri();
            File file = new File(uri);

            if(!file.createNewFile())
                throw new FileSystemException("Cant create file");

            return Optional.of(file);
        }catch (Exception e){
            logger.logWarning(getClass(),"Cant create file",e);
        }
        return Optional.empty();
    }
    private String getPathBuilder(String... paths){
        StringBuilder pathBuilder = new StringBuilder();

        for (String path:
             paths) {
            pathBuilder.append(separator)
                    .append(path);
        }

        return pathBuilder.toString();
    }

}
