package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.VO.Role;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface IRoleService {

    Optional<ObjectId> insertRole(String name);
    Optional<Role> findById(ObjectId id);

    Optional<Role> findByName(String name);

}
