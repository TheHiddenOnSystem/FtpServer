package com.onsystem.ftpserver.model.VO;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using= ToStringSerializer.class)
    private @MongoId ObjectId objectId;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId user;
    private String name;

    @DocumentReference
    private List< PermissionWorkSpaceVO > permission;

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    @Tolerate
    public void setObjectId(String objectId) {
        this.objectId = new ObjectId(objectId);
    }
    public void setUser(ObjectId user) {
        this.user = user;
    }
    @Tolerate
    public void setUser(String user) {
        this.user = new ObjectId(user);
    }
}
