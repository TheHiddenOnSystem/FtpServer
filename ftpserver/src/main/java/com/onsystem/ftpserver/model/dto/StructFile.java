package com.onsystem.ftpserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StructFile {

    private String name;
    private boolean isFile;
    private List<StructFile> directoryContent;
}
