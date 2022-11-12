package com.onsystem.ftpserver.model.VO;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;

@Document
public class Role implements GrantedAuthority {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private @MongoId ObjectId objectId;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
}
