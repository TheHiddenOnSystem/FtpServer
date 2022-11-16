package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.dto.StructFile;
import com.onsystem.ftpserver.utils.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService{
    @Autowired
    private ILogger logger;
    private final String path_initial="storage";


    /**
     * @param file contains file to save
     * @param path required add end file name
     * @return
     */

    @Override
    public boolean save( MultipartFile file, String... path ) {
        boolean result = false;
        try {

            Path lastPath = Path.of(path_initial, path);
            Files.copy( file.getInputStream(), lastPath );
            result = true;
        }catch ( Exception e ){
            logger.logWarning( getClass(), "Error save file" );
        }

        return result;
    }

    @Override
    public Optional< Resource > load( String... path ) {
        Optional< Resource > resourceResult = Optional.empty();
        try {
            Resource resource = new UrlResource(
                    Path.of(path_initial, path).toUri()
            );
            if (resource.exists() || resource.isReadable()) {
                resourceResult = Optional.of(resource);
            } else {
                throw new RuntimeException();
            }

        } catch (MalformedURLException e) {
            logger.logWarning(getClass(), "MalformedUrl");
        }catch (RuntimeException e){
            logger.logWarning(getClass(),"Cant read the file!");
        }

        return resourceResult;
    }



    @Override
    public Optional< File > createDir( String... path ){
        Optional< File > fileResult = Optional.empty();

        try{
            URI uri = Path.of(path_initial, path).toUri();
            File file = new File(uri);

            if(!file.mkdirs())
                throw new FileSystemException("Cant create directory");

            fileResult = Optional.of(file);
        }catch (Exception e){
            logger.logWarning(getClass(),"Cant create Dir");
        }

        return fileResult;
    }
    @Override
    public Optional< File > createNewFile(String... path){
        try{

            URI uri = Path.of(path_initial, path).toUri();
            File file = new File(uri);

            if( !file.createNewFile() )
                throw new FileSystemException("Cant create file");

            return Optional.of( file );
        }catch ( Exception e ){
            logger.logWarning( getClass(),"Cant create file" );
        }
        return Optional.empty();
    }

    /**
     *
     * @param path required add end file name
     * @return
     */
    @Override
    public Optional<StructFile> listAllFiles(String... path) {
        Path pathFinal = Path.of(path_initial, path);
        Optional<StructFile> structFileReturn = Optional.empty();

        try{
            StructFile structFile = new StructFile();
            File file = pathFinal.toFile();
            if( !file.exists() ){
                logger.logWarning(getClass(), "Not exist path: " + pathFinal.toUri());
                return structFileReturn;
            }


            if( file.isFile() ){
                structFile.setFile(true);
                structFile.setName(pathFinal.toFile().getName());
                structFileReturn = Optional.of(structFile);
            }else if( file.isDirectory() ){
                structFile.setFile(false);
                structFile.setName(pathFinal.toFile().getName());
                structFile.setDirectoryContent(new ArrayList<>());

                File[] filesListContains = file.listFiles();
                assert filesListContains != null;

                for (File f : filesListContains ){
                    String[] newPath = Arrays.copyOf(path,path.length + 1 );
                    newPath [ path.length ] = f.getName();

                    Optional<StructFile> structFileContainsFile = listAllFiles(newPath);
                    structFileContainsFile.ifPresent(structFileRecursive -> {
                        structFile.getDirectoryContent().add(structFileRecursive);
                    });

                }
                structFileReturn = Optional.of(structFile);
            }

        }catch ( Exception e ){
            logger.logWarning(getClass(), "Error listAllFiles in path, "+pathFinal.toUri().toString());
        }

        return structFileReturn;
    }

}
