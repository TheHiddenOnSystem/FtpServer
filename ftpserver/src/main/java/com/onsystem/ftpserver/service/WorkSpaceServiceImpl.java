package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.repository.PermissionWorkSpaceRepository;
import com.onsystem.ftpserver.repository.WorkSpaceRepository;
import com.onsystem.ftpserver.utils.FilesManager;
import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.utils.ManagerAttributesSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

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
    private PermissionWorkSpaceRepository permissionWorkSpaceRepository;
    @Autowired
    private FilesManager filesManager;


    @Override
    public Optional<ObjectId> insert(WorkSpaceVO workSpaceVO) {
        Optional<ObjectId> objectId = Optional.empty();
        try {
            Optional< UserVO > userVO = userService.findByUserLogged();

            if(userVO.isPresent()){
                List<PermissionWorkSpaceVO> permissionWorkSpaceVO = new ArrayList<>();

                if(workSpaceVO.getPermission()!=null){
                    permissionWorkSpaceVO.addAll(workSpaceVO.getPermission());
                    workSpaceVO.getPermission().clear();
                }

                WorkSpaceVO workSpaceInserted = workSpaceRepository.insert(workSpaceVO);
                objectId = Optional.of(workSpaceInserted.getObjectId());

                if(!permissionWorkSpaceVO.isEmpty()){
                    List<PermissionWorkSpaceVO> permissionGenerated = new ArrayList<>();

                    for (PermissionWorkSpaceVO permissionInsert:
                            permissionWorkSpaceVO) {
                        permissionInsert.setWorkSpace(workSpaceInserted.getObjectId());
                        permissionGenerated.add(permissionWorkSpaceRepository.insert(permissionInsert));
                    }
                    workSpaceInserted.setPermission(permissionGenerated);
                    workSpaceInserted= workSpaceRepository.save(workSpaceInserted);
                }

                userVO.get().getWorkSpace().add(workSpaceInserted);
                Optional<ObjectId> userUpdated = userService.updateUser(userVO.get());
                if(userUpdated.isPresent()){
                    Optional<File> fileWorkSpace = filesManager.createDir(
                            workSpaceInserted.getUser().toString(),
                            workSpaceInserted.getObjectId().toString()
                    );
                }

            }


        }catch (Exception e){
            logger.logWarning(getClass(),"Cant create workspace to userId: "+managerAttributesSession.getAttributesInHttpSession().getObjectId(),e);
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
    public Optional< File > createFile(String workSpace, String name) {
        Optional< File > file = Optional.empty();
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();
        try {
            if(userHavePermissionOrIsOwner(new ObjectId(workSpace), userId,"write")){
                file = filesManager.createNewFile(
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

        Optional< PermissionWorkSpaceVO > spaceVO = workSpaceUser.getPermission()
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
