package com.onsystem.ftpserver.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Data
@Document( collection = "role" )
public class RoleVO implements GrantedAuthority {
    @JsonSerialize(using= ToStringSerializer.class)

    private @MongoId ObjectId objectId;
    private String name;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
    @Tolerate
    public void setObjectId(String objectId) {
        this.objectId = new ObjectId(objectId);
    }
}
