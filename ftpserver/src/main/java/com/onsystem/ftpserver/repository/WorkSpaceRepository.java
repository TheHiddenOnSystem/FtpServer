package com.onsystem.ftpserver.repository;

import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkSpaceRepository extends MongoRepository< WorkSpaceVO, ObjectId > {

    @Query(value = " { user : $0 } ")
     List < WorkSpaceVO > findByUserId(ObjectId objectIdUser);

}
