package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.dto.UserDto;
import com.onsystem.ftpserver.model.request.UserRegisterRequest;
import com.onsystem.ftpserver.model.VO.UserVO;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<ObjectId> insertUser(UserRegisterRequest userRegister);
    Optional<ObjectId> updateUser(UserVO userVO);
    Optional<UserVO> findById(ObjectId id);
    Optional<UserVO> findByUserName(String userName);
    Optional<UserVO> findByUserLogged();
    List < UserVO > findAll();

    boolean isAuthenticate();

}
