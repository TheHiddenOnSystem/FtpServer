package com.onsystem.ftpserver.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "user")
public class UserVO implements UserDetails {
    @JsonSerialize(using= ToStringSerializer.class)
    private @MongoId ObjectId objectId;
    private String password;
    @JsonProperty("username")
    private String username;
    private String email;
    private boolean isAccountExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    @DocumentReference
    private List<RoleVO> roles;
    @DocumentReference
    private List< WorkSpaceVO > workSpace;
    @DocumentReference
    private List< PermissionWorkSpaceVO > permissionWorkSpace;

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    @Tolerate
    public void setObjectId(String objectId) {
        this.objectId = new ObjectId(objectId);
    }


    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isAccountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isEnabled;
    }
}
