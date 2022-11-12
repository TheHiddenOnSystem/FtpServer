package com.onsystem.ftpserver.repository;

import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionWorkSpaceRepository extends MongoRepository<PermissionWorkSpaceVO, ObjectId> {


}
