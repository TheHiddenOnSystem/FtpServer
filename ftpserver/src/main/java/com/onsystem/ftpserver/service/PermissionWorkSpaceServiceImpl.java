package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.model.dto.PermissionWorkSpaceDto;
import com.onsystem.ftpserver.model.request.PermissionWorkSpaceCreateRequest;
import com.onsystem.ftpserver.repository.PermissionWorkSpaceRepository;
import com.onsystem.ftpserver.utils.ILogger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionWorkSpaceServiceImpl implements PermissionWorkSpaceService{
    @Autowired
    private ILogger logger;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PermissionWorkSpaceRepository workSpaceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkSpaceService workSpaceService;

    @Override
    public Optional< ObjectId > insertPermissionSpace(PermissionWorkSpaceVO permissionWorkSpaceVO) {
        Optional<ObjectId> objectIdInserted = Optional.empty();
        try {
            boolean isValid = true;
            Optional< UserVO > userToAddPermission = userService.findById(permissionWorkSpaceVO.getUser());
            if( userToAddPermission.isEmpty() )
                isValid = false;

            Optional< WorkSpaceVO > workSpaceToAddPermission = workSpaceService.findById(permissionWorkSpaceVO.getWorkSpace());
            if( workSpaceToAddPermission.isEmpty() )
                isValid = false;

            if(isValid){
                PermissionWorkSpaceVO permissionWorkSpaceInserted = workSpaceRepository.save(permissionWorkSpaceVO);
                userToAddPermission.get().getPermissionWorkSpace().add(permissionWorkSpaceInserted);
                userService.updateUser(userToAddPermission.get());

                workSpaceToAddPermission.get().getPermissionWorkSpace().add(permissionWorkSpaceInserted);
                workSpaceService.update(workSpaceToAddPermission.get());

                objectIdInserted = Optional.of(permissionWorkSpaceInserted.getObjectId());
            }




        }catch (Exception e){
            logger.logWarning(getClass(), "Cant create PermissionSpace");
        }

        return objectIdInserted;
    }

    @Override
    public Optional<ObjectId> insertPermissionSpace(PermissionWorkSpaceCreateRequest permissionWorkSpaceCreateRequest) {
        Optional<ObjectId> objectId = Optional.empty();
        try {
            PermissionWorkSpaceVO permissionWorkSpaceVO = objectMapper.convertValue(permissionWorkSpaceCreateRequest, PermissionWorkSpaceVO.class);
            objectId = insertPermissionSpace(permissionWorkSpaceVO);
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant convert PermissionWorkSpaceDto to Vo in insertPermissionSpace");
        }
        return objectId;
    }

    @Override
    public Optional<PermissionWorkSpaceVO> findById(ObjectId objectId) {
        return Optional.empty();
    }

    @Override
    public Optional<PermissionWorkSpaceDto> findByIdDto(ObjectId objectId) {
        return Optional.empty();
    }

    @Override
    public boolean update(PermissionWorkSpaceVO permissionWorkSpaceVO) {
        return false;
    }
}
