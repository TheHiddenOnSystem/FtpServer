package com.onsystem.ftpserver.model.VO;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document( collection = "permissionWorkSpace" )
public class PermissionWorkSpaceVO {

    private @MongoId ObjectId objectId;

    private ObjectId workSpace;

    private ObjectId users;

    private List<String> permission;
}
