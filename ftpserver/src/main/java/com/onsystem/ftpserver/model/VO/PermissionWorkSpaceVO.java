package com.onsystem.ftpserver.model.VO;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document( collection = "permissionWorkSpace" )
public class PermissionWorkSpaceVO {
    @JsonSerialize(using= ToStringSerializer.class)
    private @MongoId ObjectId objectId;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId workSpace;
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId user;

    private List<String> permission;


    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public void setWorkSpace(ObjectId workSpace) {
        this.workSpace = workSpace;
    }

    public void setUser(ObjectId user) {
        this.user = user;
    }

    @Tolerate
    public void setObjectId(String objectId) {
        this.objectId = new ObjectId(objectId);
    }
    @Tolerate
    public void setWorkSpace(String workSpace) {
        if(workSpace!=null)
            this.workSpace = new ObjectId(workSpace);
    }
    @Tolerate
    public void setUser(String users) {
        if(users!=null)
            this.user = new ObjectId(users);
    }
}
