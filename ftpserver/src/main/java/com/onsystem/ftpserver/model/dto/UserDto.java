package com.onsystem.ftpserver.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto{
    private String objectId;
    private String password;
    private String username;
    private String email;
    private boolean isAccountExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private List<RoleDto> roles;
    private List<WorkSpaceDto> workSpace;
    private List<PermissionWorkSpaceDto> permissionWorkSpace;
}
