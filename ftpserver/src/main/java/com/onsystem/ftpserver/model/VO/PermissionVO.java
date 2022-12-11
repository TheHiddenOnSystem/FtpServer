package com.onsystem.ftpserver.model.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "permission")
@Data
@NoArgsConstructor
public class PermissionVO {
    @JsonSerialize(using= ToStringSerializer.class)
    private @MongoId ObjectId objectId;
    private String name;
}
