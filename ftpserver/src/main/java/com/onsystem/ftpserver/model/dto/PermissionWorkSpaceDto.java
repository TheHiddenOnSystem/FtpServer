package com.onsystem.ftpserver.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionWorkSpaceDto {
    private String objectId;
    private String workSpace;
    private String user;
    private List<String> permission;
}
