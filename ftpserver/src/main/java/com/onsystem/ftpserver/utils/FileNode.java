package com.onsystem.ftpserver.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileNode {

    private List<FileNode> children;
    private String name;
    private String path;
    private boolean isDirectory;


    private String getAbsolutePath (){
        return path + name ;
    }

}
