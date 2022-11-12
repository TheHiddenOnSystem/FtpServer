package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.model.request.UserRegisterRequest;
import com.onsystem.ftpserver.model.VO.UserVO;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface UserService {

    Optional<ObjectId> insertUser(UserRegisterRequest userRegister);

    Optional<UserVO> findById(ObjectId id);
    Optional<UserVO> findByUserName(String userName);
    Optional<UserVO> findByUserLogged();

}
