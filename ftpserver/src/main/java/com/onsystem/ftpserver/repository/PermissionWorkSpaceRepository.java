package com.onsystem.ftpserver.repository;

import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermissionWorkSpaceRepository extends MongoRepository<PermissionWorkSpaceVO, ObjectId> {

    @Query(value = " { workSpace : $0 }")
     List < PermissionWorkSpaceVO > findByWorkSpace(ObjectId objectId);
    @Query(value = " { user : $0 }")
    List < PermissionWorkSpaceVO > findByUserAssignedPermission(ObjectId objectId);


}
