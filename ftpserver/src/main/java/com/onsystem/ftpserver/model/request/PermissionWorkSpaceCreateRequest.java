package com.onsystem.ftpserver.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionWorkSpaceCreateRequest {
    private String workSpace;
    private String user;
    private List<String> permission;

}
