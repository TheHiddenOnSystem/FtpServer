package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.dto.StructFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface FileStorageService {

    boolean save(  MultipartFile file, String... path );

    Optional<Resource> load( String... path );

    Optional<File> createDir( String... path );
    Optional< File > createNewFile( String... path );

    Optional<StructFile> listAllFiles( String... path );
}
