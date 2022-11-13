package com.onsystem.ftpserver.repository;

import com.onsystem.ftpserver.model.VO.UserVO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserVO, ObjectId> {

    @Query( "{ username: ?0 }" )
    Optional<UserVO> findByUserName(String username);

}
