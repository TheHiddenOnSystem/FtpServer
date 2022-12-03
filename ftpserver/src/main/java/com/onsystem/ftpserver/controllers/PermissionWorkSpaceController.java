package com.onsystem.ftpserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.dto.PermissionWorkSpaceDto;
import com.onsystem.ftpserver.model.request.PermissionWorkSpaceCreateRequest;
import com.onsystem.ftpserver.repository.PermissionWorkSpaceRepository;
import com.onsystem.ftpserver.service.PermissionWorkSpaceService;
import com.onsystem.ftpserver.utils.ILogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/v1/permissionWorkSpace/")
public class PermissionWorkSpaceController {
    @Autowired
    private ILogger logger;
    @Autowired
    private PermissionWorkSpaceService workSpaceService;
    @Autowired
    private ObjectMapper objectMapper;

    @PutMapping("insert")
    @Operation(
            description = "Insert Permission in workspace to user",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Can create Permission to user"),
                    @ApiResponse( responseCode = "500", description = "Cant create Permission to user")
            }
    )
    public ResponseEntity< ? > insertPermissionInWorkSpace(@RequestBody PermissionWorkSpaceCreateRequest permissionWorkSpaceCreateRequest){
        Optional< ObjectId > objectId = Optional.empty();

        try {
            objectId = workSpaceService.insertPermissionSpace(
                    objectMapper.convertValue(permissionWorkSpaceCreateRequest,PermissionWorkSpaceVO.class)
            );
        }catch (Exception e){
            logger.logWarning( getClass(), " Cant cast request to VO ");
        }

        return objectId.isPresent() ?
                new ResponseEntity<>(objectId.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

}
