package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.VO.PermissionVO;
import com.onsystem.ftpserver.repository.PermissionRepository;
import com.onsystem.ftpserver.utils.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    private Logger logger;
    @Autowired
    private PermissionRepository permissionRepository;


    @Override
    public List<PermissionVO> getPermissionAll() {
        return permissionRepository.findAll();
    }

    @Override
    public ObjectId insertPermission(PermissionVO permissionVO) {
        permissionVO.setObjectId(null);
        Optional<PermissionVO> permissionSearchByName = permissionRepository.findByName(permissionVO.getName());
        if(permissionSearchByName.isPresent())
            throw new IllegalArgumentException("The name in permission are exist");

        return permissionRepository.insert(permissionVO).getObjectId();
    }
}
