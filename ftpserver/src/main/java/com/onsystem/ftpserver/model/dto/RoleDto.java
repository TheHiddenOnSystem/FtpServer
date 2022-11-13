package com.onsystem.ftpserver.model.dto;

import lombok.*;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RoleDto extends MongoDto{
    private String name;
}
