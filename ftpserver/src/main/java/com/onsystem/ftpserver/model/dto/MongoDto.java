package com.onsystem.ftpserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
public class MongoDto {

    @Getter
    private String objectId;

    @Tolerate
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId.toString();
    }

}
