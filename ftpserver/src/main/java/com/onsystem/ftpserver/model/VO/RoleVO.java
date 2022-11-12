package com.onsystem.ftpserver.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Data
@Document( collection = "role" )
public class RoleVO implements GrantedAuthority {

    private @MongoId ObjectId objectId;
    private String name;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
}
