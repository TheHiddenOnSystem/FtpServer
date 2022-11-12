package com.onsystem.ftpserver.repository;

import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkSpaceRepository extends MongoRepository< WorkSpaceVO, ObjectId > {

    @Query("{ user : $0 }")
    Optional< List < WorkSpaceVO > > findByUserId(ObjectId objectIdUser);

}
