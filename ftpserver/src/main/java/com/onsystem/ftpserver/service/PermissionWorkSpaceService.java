package com.onsystem.ftpserver.service;

import org.bson.types.ObjectId;
import java.util.Optional;

public interface PermissionWorkSpaceService {

    Optional< ObjectId > insertPermissionSpace();
}
