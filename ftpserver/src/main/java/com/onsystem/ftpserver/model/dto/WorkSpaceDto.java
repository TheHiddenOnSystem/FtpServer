package com.onsystem.ftpserver.model.dto;

import lombok.*;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkSpaceDto extends MongoDto{

    private String user;
    @Setter
    private String name;
    @Setter
    private List<PermissionWorkSpaceDto> permissionWorkSpace;

    @Tolerate
    public void setUser(ObjectId user) {
        this.user = user.toString();
    }
}
