package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.repository.PermissionWorkSpaceRepository;
import com.onsystem.ftpserver.utils.ILogger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionWorkSpaceServiceImpl implements PermissionWorkSpaceService{
    @Autowired
    private ILogger logger;
    @Autowired
    private PermissionWorkSpaceRepository permissionWorkSpaceRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkSpaceService workSpaceService;

    @Override
    public Optional< ObjectId > insertPermissionSpace(PermissionWorkSpaceVO permissionWorkSpaceVO) {
        Optional<ObjectId> objectIdInserted = Optional.empty();
        try {
            Optional< UserVO > searchUserToAddPermission = userService.findById(permissionWorkSpaceVO.getUser());
            if( searchUserToAddPermission.isEmpty() )
                return Optional.empty();

            Optional< WorkSpaceVO > searchWorkSpaceAddPermission = workSpaceService.findById(permissionWorkSpaceVO.getWorkSpace());
            if( searchWorkSpaceAddPermission.isEmpty() )
                return Optional.empty();

            PermissionWorkSpaceVO permissionWorkSpaceInserted = permissionWorkSpaceRepository.save(permissionWorkSpaceVO);

            searchUserToAddPermission.get().getPermissionWorkSpace().add(permissionWorkSpaceInserted);
            userService.updateUser(searchUserToAddPermission.get());

            searchWorkSpaceAddPermission.get().getPermissionWorkSpace().add(permissionWorkSpaceInserted);
            workSpaceService.update(searchWorkSpaceAddPermission.get());

            objectIdInserted = Optional.of(permissionWorkSpaceInserted.getObjectId());

        }catch (Exception e){
            logger.logWarning(getClass(), "Cant create PermissionSpace");
        }

        return objectIdInserted;
    }

    @Override
    public Optional<PermissionWorkSpaceVO> findById(ObjectId objectId) {
        return permissionWorkSpaceRepository.findById(objectId);
    }


    @Override
    public boolean update(PermissionWorkSpaceVO permissionWorkSpaceVO) {
        boolean result = false;

        try {
            if( findById( permissionWorkSpaceVO.getObjectId() ).isPresent() ){
                permissionWorkSpaceRepository.save(permissionWorkSpaceVO);
                result = true;
            }

        }catch (Exception e){
            logger.logWarning(getClass(), "Cant update PermissionWorkSpaceVO");
        }

        return result;
    }

    @Override
    public Optional< List < PermissionWorkSpaceVO > > findByUserAssignPermission(ObjectId objectId) {
        Optional<List < PermissionWorkSpaceVO > > userPermissionsAssigned = Optional.empty();
        try{
            userPermissionsAssigned = Optional.of( permissionWorkSpaceRepository.findByUserAssignedPermission(objectId) );
        }catch (Exception e){
            logger.logWarning(getClass(), "Error method findByUserAssignPermission");
        }
        return userPermissionsAssigned;
    }

    @Override
    public Optional< List < PermissionWorkSpaceVO > > findByWorkSpace(ObjectId objectId) {
        Optional< List < PermissionWorkSpaceVO > > permissionsInWorkSpace =Optional.empty();
        try {
            permissionsInWorkSpace = Optional.of( permissionWorkSpaceRepository.findByWorkSpace(objectId) );
        }catch (Exception e){
            logger.logWarning(getClass(), "Error method findByWorkSpace");
        }
        return permissionsInWorkSpace;
    }
}
