package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.RoleVO;
import com.onsystem.ftpserver.model.dto.UserDto;
import com.onsystem.ftpserver.utils.FilesManager;
import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.model.request.UserRegisterRequest;
import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.repository.UserRepository;
import com.onsystem.ftpserver.utils.ManagerAttributesSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private ILogger logger;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ManagerAttributesSession managerAttributesSession;

    @Autowired
    private RoleService roleService;
    @Autowired
    private FilesManager filesManager;

    @Override
    public Optional<ObjectId> insertUser(UserRegisterRequest userRegister) {
        Optional< ObjectId > objectId = Optional.empty();

        try{
            findByUserName(userRegister.getUsername()).ifPresent(user1 -> {
                throw new IllegalArgumentException();
            });

            UserVO user = objectMapper.convertValue(userRegister, UserVO.class);
            user.setPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
            user.setEnabled(true);
            user.setAccountExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            RoleVO roleVO = roleService.findByName("user")
                    .orElseThrow();
            user.setRoles(List.of(roleVO));

            user.setPermissionWorkSpace(List.of());
            user.setWorkSpace(List.of());

            objectId = Optional.of(userRepository.save(user).getObjectId());

            filesManager.createDir(objectId.get().toString());
        }catch ( IllegalArgumentException e){
            this.logger.logWarning(this.getClass(),"Cant create user",e);
        }
        return objectId;
    }

    @Override
    public Optional<ObjectId> updateUser(UserVO userVO) {
        Optional< ObjectId > objectId = Optional.empty();
        try {
            if (findById(userVO.getObjectId())
                    .isPresent()
            )
                objectId = Optional.of(userRepository.save(userVO).getObjectId());
        }catch (Exception e){

        }

        return objectId;
    }

    @Override
    public Optional< UserVO > findById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional< UserVO > findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional< UserVO > findByUserLogged() {
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();
        return findById(userId);
    }

    @Override
    public Optional<List < UserDto > > findAllUserDto() {
        Optional< List < UserDto > > listUser = Optional.empty();
        try {
            listUser = Optional.of(
                    Arrays.asList(
                            objectMapper.convertValue(userRepository.findAll(), UserDto[].class)
                    )
            );
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant getAll users dto in findAllUserDto");
        }
        return listUser;
    }


}
