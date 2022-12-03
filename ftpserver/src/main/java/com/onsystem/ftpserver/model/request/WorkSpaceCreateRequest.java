package com.onsystem.ftpserver.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkSpaceCreateRequest {

    private String name;
    private List<PermissionWorkSpaceCreateRequest> permission;

}
