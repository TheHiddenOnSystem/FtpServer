package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.model.VO.RoleVO;
import com.onsystem.ftpserver.repository.RoleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private ILogger logger;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Optional<ObjectId> insertRole(String name) {
        try{
            findByName(name).ifPresent(role -> {
                throw new IllegalArgumentException();
            });

            RoleVO role = new RoleVO(null,name);
            return Optional.of(roleRepository.save(role).getObjectId());
        }catch (Exception e){
            logger.logInfo(this.getClass(),"Cant create role");
        }
        return Optional.empty();
    }

    @Override
    public Optional<RoleVO> findById(ObjectId id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<RoleVO> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional< List < RoleVO > > getAllRole() {
        try {
            return Optional.of(roleRepository.findAll());
        }catch (Exception e){
            logger.logInfo(this.getClass(),"Cant get roles");
        }

        return Optional.empty();
    }
}
