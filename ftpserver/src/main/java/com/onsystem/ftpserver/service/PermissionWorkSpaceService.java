package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.dto.PermissionWorkSpaceDto;
import com.onsystem.ftpserver.model.request.PermissionWorkSpaceCreateRequest;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionWorkSpaceService {

    Optional< ObjectId > insertPermissionSpace(PermissionWorkSpaceVO permissionWorkSpaceVO);
    Optional< PermissionWorkSpaceVO > findById(ObjectId objectId);
    boolean update(PermissionWorkSpaceVO permissionWorkSpaceVO);
}
