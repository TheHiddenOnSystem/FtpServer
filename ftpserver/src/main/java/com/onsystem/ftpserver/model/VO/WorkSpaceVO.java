package com.onsystem.ftpserver.model.VO;


import lombok.Data;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document( collection = "workSpace" )
public class WorkSpaceVO {

    private @MongoId ObjectId objectId;

    private ObjectId user;

    private String name;

    @DocumentReference
    private List< PermissionWorkSpaceVO > permissionWorkSpace;


}
