package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.model.request.WorkSpaceCreateRequest;
import com.onsystem.ftpserver.repository.WorkSpaceRepository;
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
    private UserService userService;
    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public Optional<ObjectId> insert(WorkSpaceCreateRequest workSpaceCreateRequest) {
        Optional<ObjectId> objectId = Optional.empty();
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();
        try {
            Optional< UserVO > userVO = userService.findByUserLogged();

            if(userVO.isPresent()){
                WorkSpaceVO workSpaceVO = objectMapper.convertValue(workSpaceCreateRequest, WorkSpaceVO.class);
                workSpaceVO.setUser(userId);
                workSpaceVO.setPermissionWorkSpace(List.of());

                WorkSpaceVO workSpaceInserted = workSpaceRepository.insert(workSpaceVO);
                objectId = Optional.of(workSpaceInserted.getObjectId());

                userVO.get().getWorkSpace().add(workSpaceInserted);
                userService.updateUser(userVO.get()).ifPresent(
                        objectId1 -> {
                            Optional<File> fileWorkSpace = fileStorageService.createDir(
                                    userId.toString(),
                                            workSpaceInserted.getObjectId().toString()
                            );
                        }
                );

            }


        }catch (Exception e){
            logger.logWarning(getClass(),"Cant create workspace to userId: "+managerAttributesSession.getAttributesInHttpSession().getObjectId());
            //todo quizas se podria limpiar el residuo si no proccede con exito
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
            return Optional.of(workSpaceRepository.findByUserId(objectIdUser));
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant filter WorkSpace By idUser: " + objectIdUser);
        }

        return Optional.empty();
    }

    @Override
    public Optional< File > createFile(String workSpace, String name) {
        Optional< File > file = Optional.empty();
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();
        try {
            if(userHavePermissionOrIsOwner(new ObjectId(workSpace), userId,"write")){
                file = fileStorageService.createNewFile(
                                managerAttributesSession.getAttributesInHttpSession().getObjectId().toString(),
                                workSpace,
                                name
                );
            }

        }catch (Exception e){
            logger.logWarning(getClass(), "Cant create file userId: " +userId);
        }

        return file;
    }

    @Override
    public Optional< File > createDirectory(String workSpace,String nameDirectory) {
        Optional< File > directory = Optional.empty();
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();

        try {

            if(userHavePermissionOrIsOwner(new ObjectId(workSpace),userId,"write"))
                directory = fileStorageService.createDir(
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
    public Optional< ObjectId > update(WorkSpaceVO workSpaceVO) {
        Optional< ObjectId > objectId = Optional.empty();
        try {
            objectId = Optional.of(workSpaceRepository.save(workSpaceVO).getObjectId());
        }catch (Exception e){
            logger.logInfo(getClass(),"Cant update Workspace, "
                    + String.format("user: %s , workspace: %s", managerAttributesSession.getAttributesInHttpSession().getObjectId()
                        ,workSpaceVO.getObjectId())
            );
        }

        return objectId;
    }

    private boolean userHavePermissionOrIsOwner(ObjectId workSpaceId, ObjectId userId, String permission){
        boolean canCreate = false;

        WorkSpaceVO workSpaceUser =  findById(workSpaceId).orElseThrow();
        if(workSpaceUser.getUser().equals(userId))
            return true;

        Optional< PermissionWorkSpaceVO > spaceVO = workSpaceUser.getPermissionWorkSpace()
                .stream()
                .filter(
                        permissionWorkSpaceVO -> permissionWorkSpaceVO.getUser() == userId
                                && permissionWorkSpaceVO.getPermission().stream().anyMatch(s -> s.contains(permission))
                )
                .findFirst();
        if(spaceVO.isPresent())
            canCreate = true;

        return canCreate;
    }
}
