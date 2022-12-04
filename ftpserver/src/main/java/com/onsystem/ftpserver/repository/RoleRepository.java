package com.onsystem.ftpserver.repository;

import com.onsystem.ftpserver.model.VO.RoleVO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<RoleVO, ObjectId> {

    @Query( value = "{ name: ?0 }" )
    Optional<RoleVO> findByName(String name);
}
