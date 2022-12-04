package com.onsystem.ftpserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.util.*;

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

    public FileNode getNodesInDirectory(String... route) throws NullPointerException,IllegalArgumentException{

        Path path= Path.of(path_initial.toAbsolutePath()+getPathBuilder(route));
        File directory = path.toFile();

        FileNode fileNodeInDirectory = new FileNode();
        fileNodeInDirectory.setName(directory.getName());
        fileNodeInDirectory.setPath(directory.getAbsolutePath());

        if(directory.isDirectory() && directory.exists()){
            fileNodeInDirectory.setDirectory(true);
            fileNodeInDirectory.setChildren(
                    recursiveListFile(
                            Objects.requireNonNull(directory.listFiles())
                    )
            );

        }
        else
            throw new IllegalArgumentException("Require directory and is file or not exist " + directory.getAbsolutePath());

        return fileNodeInDirectory;
    }


    private List<FileNode> recursiveListFile(File[] filesInDirectory) throws NullPointerException{
        List<FileNode> fileNodesResult = new ArrayList<>();

        for (File file:
             filesInDirectory) {
            FileNode fileNodeInDirectory = new FileNode();
            fileNodeInDirectory.setName(file.getName());
            fileNodeInDirectory.setPath(file.getAbsolutePath());

            if(file.isDirectory()){
                fileNodeInDirectory.setDirectory(true);
                fileNodeInDirectory.setChildren(recursiveListFile(Objects.requireNonNull(file.listFiles())));

            }else{
                fileNodeInDirectory.setDirectory(false);
            }

            fileNodesResult.add(fileNodeInDirectory);

        }

        return fileNodesResult;
    }



}
