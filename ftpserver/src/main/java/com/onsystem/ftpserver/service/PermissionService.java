package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.VO.PermissionVO;
import org.bson.types.ObjectId;

import java.util.List;

public interface PermissionService {

    List<PermissionVO> getPermissionAll();

    ObjectId insertPermission(PermissionVO permissionVO);

}
