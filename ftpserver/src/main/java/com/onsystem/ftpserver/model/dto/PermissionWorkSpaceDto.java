package com.onsystem.ftpserver.model.dto;

import com.onsystem.ftpserver.model.VO.PermissionVO;
import lombok.*;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionWorkSpaceDto {
    private String objectId;
    private String workSpace;
    private String user;
    private List<PermissionVO> permission;
}
