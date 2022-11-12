package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.model.request.WorkSpaceCreateRequest;
import com.onsystem.ftpserver.repository.WorkSpaceRepository;
import com.onsystem.ftpserver.utils.FilesManager;
import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.utils.ManagerAttributesSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService{

    @Autowired
    private ManagerAttributesSession managerAttributesSession;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ILogger logger;
    @Autowired
    private WorkSpaceRepository workSpaceRepository;
    @Autowired
    private FilesManager filesManager;


    @Override
    public Optional<ObjectId> insert(WorkSpaceCreateRequest workSpaceCreateRequest) {
        Optional<ObjectId> objectId = Optional.empty();
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();
        try {
            WorkSpaceVO workSpaceVO = objectMapper.convertValue(workSpaceCreateRequest, WorkSpaceVO.class);
            workSpaceVO.setUser(userId);
            workSpaceVO.setPermissionWorkSpace(List.of());

            objectId = Optional.of(workSpaceRepository.insert(workSpaceVO).getObjectId());

            Optional<File> fileWorkSpace = filesManager.createDir(
                    userId.toString(),
                    objectId.get().toString()
            );

        }catch (Exception e){
            logger.logWarning(getClass(),"Cant create workspace to userId: "+managerAttributesSession.getAttributesInHttpSession().getObjectId());
        }

        return objectId;
    }

    @Override
    public Optional< WorkSpaceVO > findById( ObjectId objectId ) {
        return this.workSpaceRepository.findById(objectId);
    }

    @Override
    public Optional< List < WorkSpaceVO > > findByIdUser( ObjectId objectIdUser ) {

        try {
            return workSpaceRepository.findByUserId(objectIdUser);
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant filter WorkSpace By idUser: " + objectIdUser);
        }


        return Optional.empty();
    }

    @Override
    public Optional< List < WorkSpaceVO > > findByIdUser() {
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();
        return findByIdUser(userId);
    }

    @Override
    public Optional< File > createFile(ObjectId workSpace, String name) {
        Optional< File > file = Optional.empty();
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();

        try {
            if(userHavePermissionOrIsOwner(workSpace,userId,"write")){
                file = filesManager.createNewFile(
                        managerAttributesSession.getAttributesInHttpSession().getObjectId().toString(),
                        workSpace.toString(),
                        name
                );
            }

        }catch (Exception e){
            logger.logWarning(getClass(), "Cant create file userId:");
        }

        return file;
    }

    @Override
    public Optional< File > createDirectory(String workSpace,String nameDirectory) {
        Optional< File > directory = Optional.empty();
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();

        try {

            if(userHavePermissionOrIsOwner(new ObjectId(workSpace),userId,"write"))
                directory = filesManager.createDir(
                        managerAttributesSession.getAttributesInHttpSession().getObjectId().toString(),
                        workSpace,
                        nameDirectory
                );

        }catch (Exception e){
            logger.logWarning(getClass(), "Cant create directory userId: "+userId);
        }
        return directory;
    }

    @Override
    public boolean update(WorkSpaceVO workSpaceVO) {
        try {
            workSpaceRepository.save(workSpaceVO);
            return true;
        }catch (Exception e){
            logger.logInfo(getClass(),"Cant update Workspace, "
                    + String.format("user: %s , workspace: %s", managerAttributesSession.getAttributesInHttpSession().getObjectId()
                        ,workSpaceVO.getObjectId())
            );
        }

        return false;
    }

    private boolean userHavePermissionOrIsOwner(ObjectId workSpaceId, ObjectId userId, String permission){
        boolean canCreate = false;

        WorkSpaceVO workSpaceUser =  findById(workSpaceId).orElseThrow();
        if(workSpaceUser.getUser() == userId)
            return true;

            /*
            todo Comparar en un futuro si tengo permisos de escritura
             */
        Optional< PermissionWorkSpaceVO > spaceVO = workSpaceUser.getPermissionWorkSpace()
                .stream()
                .filter(
                        permissionWorkSpaceVO -> permissionWorkSpaceVO.getUsers() == userId
                                && permissionWorkSpaceVO.getPermission().stream().anyMatch(s -> s.contains(permission))
                )
                .findFirst();
        if(spaceVO.isPresent())
            canCreate = true;

        return canCreate;
    }
}
