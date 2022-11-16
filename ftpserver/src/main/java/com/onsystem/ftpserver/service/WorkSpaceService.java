package com.onsystem.ftpserver.service;


import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.model.request.WorkSpaceCreateRequest;
import org.bson.types.ObjectId;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface WorkSpaceService {

    Optional< ObjectId > insert(WorkSpaceCreateRequest workSpaceCreateRequest);

    Optional< WorkSpaceVO > findById(ObjectId objectId);
    Optional< List< WorkSpaceVO > > findByIdUser(ObjectId objectIdUser);

    Optional< File > createFile(String workSpace, String name);
    Optional< File > createDirectory(String workSpace, String name);

    Optional< ObjectId > update(WorkSpaceVO workSpaceVO);
}
