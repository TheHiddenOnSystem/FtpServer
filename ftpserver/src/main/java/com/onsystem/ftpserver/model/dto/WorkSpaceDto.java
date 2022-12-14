package com.onsystem.ftpserver.model.dto;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkSpaceDto{
    private String objectId;
    private String user;
    private String name;
    private List<PermissionWorkSpaceDto> permissionWorkSpace;
}
