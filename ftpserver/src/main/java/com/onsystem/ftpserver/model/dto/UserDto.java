package com.onsystem.ftpserver.model.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserDto extends MongoDto{

    private String password;
    private String userName;
    private String email;
    private boolean isAccountExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private List<RoleDto> roles;
    private List<WorkSpaceDto> workSpace;
    private List<PermissionWorkSpaceDto> permissionWorkSpace;
}
