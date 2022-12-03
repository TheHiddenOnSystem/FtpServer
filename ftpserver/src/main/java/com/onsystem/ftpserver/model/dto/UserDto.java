package com.onsystem.ftpserver.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto{

    private String objectId;
    private String password;
    @JsonProperty("username")
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
