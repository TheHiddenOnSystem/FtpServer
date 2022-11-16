package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionWorkSpaceService {

    Optional< ObjectId > insertPermissionSpace(PermissionWorkSpaceVO permissionWorkSpaceVO);

    Optional< PermissionWorkSpaceVO > findById(ObjectId objectId);

    boolean update(PermissionWorkSpaceVO permissionWorkSpaceVO);

    Optional< List< PermissionWorkSpaceVO > > findByUserAssignPermission(ObjectId objectId);

    Optional< List< PermissionWorkSpaceVO > > findByWorkSpace(ObjectId objectId);
}
