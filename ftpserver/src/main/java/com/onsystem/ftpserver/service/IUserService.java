package com.onsystem.ftpserver.service;

import com.onsystem.ftpserver.dto.UserRegister;
import com.onsystem.ftpserver.model.VO.User;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface IUserService {

    Optional<ObjectId> insertUser(UserRegister userRegister);

    Optional<User> findById(ObjectId id);
    Optional<User> findByUserName(String userName);

    Optional<Integer> authenticateUser(String name,String password);

}
