package com.onsystem.ftpserver.model.dto;

import lombok.*;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PermissionWorkSpaceDto extends MongoDto{

    private String workSpace;
    private String users;
    @Setter
    private List<String> permission;


    @Tolerate
    public void setWorkSpace(ObjectId workSpace) {
        this.workSpace = workSpace.toString();
    }
    @Tolerate
    public void setUsers(ObjectId users) {
        this.users = users.toString();
    }


}
