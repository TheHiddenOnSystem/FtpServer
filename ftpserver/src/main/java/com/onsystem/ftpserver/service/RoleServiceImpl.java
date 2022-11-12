package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.configuration.ILogger;
import com.onsystem.ftpserver.model.VO.Role;
import com.onsystem.ftpserver.repository.RoleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private ILogger logger;
    @Autowired
    private RoleRepository iRoleService;


    @Override
    public Optional<ObjectId> insertRole(String name) {
        try{
            findByName(name).ifPresent(role -> {
                throw new IllegalArgumentException();
            });

            Role role = new Role();
            role.setName(name);

            return Optional.of(iRoleService.save(role).getObjectId());
        }catch (Exception e){
            logger.logInfo(this.getClass(),"Cant create role");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> findById(ObjectId id) {
        return iRoleService.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return iRoleService.findByName(name);
    }
}
