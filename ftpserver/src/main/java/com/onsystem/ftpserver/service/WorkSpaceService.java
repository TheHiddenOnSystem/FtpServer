package com.onsystem.ftpserver.service;


import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.model.dto.WorkSpaceDto;
import com.onsystem.ftpserver.model.request.WorkSpaceCreateRequest;
import com.onsystem.ftpserver.utils.FileNode;
import org.bson.types.ObjectId;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface WorkSpaceService {

    Optional< ObjectId > insert(WorkSpaceVO workSpaceVO);
    Optional< WorkSpaceVO > findById(ObjectId objectId);
    Optional< List< WorkSpaceVO > > findByIdUser(ObjectId objectIdUser);
    Optional< File > createFile(String workSpace, String name);
    Optional< File > createDirectory(String workSpace, String name);
    Optional< List < WorkSpaceVO > > findByIdUser();
    Optional< ObjectId > update(WorkSpaceVO workSpaceVO);

    Optional< FileNode > getDirectories(String workSpaceObjectId);
}
