package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.RoleVO;
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
            findByUserName(userRegister.getUserName()).ifPresent(user1 -> {
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

            objectId = Optional.of(userRepository.save(user).getId());

            filesManager.createDir(objectId.get().toString()).orElseThrow(
                    () -> new IllegalArgumentException("Cant create directory")
            );
        }catch (Exception e){
            this.logger.logWarning(this.getClass(),"Cant create user");
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
        try {
            return findById(managerAttributesSession.getAttributesInHttpSession().getObjectId());
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant find user logged");
        }
        return Optional.empty();
    }


}
