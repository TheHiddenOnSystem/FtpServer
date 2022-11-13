package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.VO.RoleVO;
import com.onsystem.ftpserver.model.dto.RoleDto;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<ObjectId> insertRole(String name);
    Optional<RoleVO> findById(ObjectId id);

    Optional<RoleVO> findByName(String name);

    Optional< List <RoleVO> > getAllRole();
    Optional< List <RoleDto> > getAllRoleDto();

}
