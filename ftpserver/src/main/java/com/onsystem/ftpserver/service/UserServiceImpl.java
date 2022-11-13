package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.RoleVO;
import com.onsystem.ftpserver.model.dto.UserDto;
import com.onsystem.ftpserver.model.dto.WorkSpaceDto;
import com.onsystem.ftpserver.utils.FilesManager;
import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.model.request.UserRegisterRequest;
import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.repository.UserRepository;
import com.onsystem.ftpserver.utils.ManagerAttributesSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
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

            filesManager.createDir(objectId.get().toString()).orElseThrow(
                    () -> new IllegalArgumentException("Cant create directory")
            );
        }catch (Exception e){
            this.logger.logWarning(this.getClass(),"Cant create user");
        }
        return objectId;
    }

    @Override
    public Optional<ObjectId> updateUser(UserVO userVO) {
        try {
            if( findById( userVO.getObjectId())
                    .isPresent()
            )
                return Optional.of(userRepository.save(userVO).getObjectId());
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant update user ");
        }
        return Optional.empty();
    }

    @Override
    public Optional< UserVO > findById(ObjectId id) {
        return userRepository.findById(id);
    }


    @Override
    public Optional<UserDto> findByIdDto(ObjectId id) {
        try {
            Optional< UserVO > userVO = findById(id);
            if(userVO.isPresent()){
                UserDto userDto = objectMapper.convertValue(userVO.get(),UserDto.class);
                return Optional.of(userDto);
            }
        }catch (Exception e){
            logger.logWarning(getClass(), "Can convert UserVo to dto in findByIdDto");
        }
        return Optional.empty();
    }

    @Override
    public Optional< UserVO > findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    @Override
    public Optional<UserDto> findByUserNameDto(String userName) {
        try {
            Optional< UserVO > userVO = findByUserName(userName);
            if(userVO.isPresent()){
                UserDto userDto = objectMapper.convertValue(userVO.get(),UserDto.class);
                return Optional.of(userDto);
            }
        }catch (Exception e){
            logger.logWarning(getClass(), "Can convert UserVo to dto in findByUserNameDto");
        }
        return Optional.empty();
    }

    @Override
    public Optional< UserVO > findByUserLogged() {
        ObjectId userId = managerAttributesSession.getAttributesInHttpSession().getObjectId();
        try {
            return findById(userId);
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant find user logged userId: "+userId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findByUserLoggedDto() {

        try {
            Optional< UserVO > userVO = findByUserLogged();
            if(userVO.isPresent()){
                UserDto userDto = objectMapper.convertValue(userVO.get(),UserDto.class);
                return Optional.of(userDto);
            }
        }catch (Exception e){
            logger.logWarning(getClass(), "Can convert UserVo to dto in findByUserLoggedDto");
        }
        return Optional.empty();
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
