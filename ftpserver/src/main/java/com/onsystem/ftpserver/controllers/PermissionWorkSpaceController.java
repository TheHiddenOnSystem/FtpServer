package com.onsystem.ftpserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.PermissionWorkSpaceVO;
import com.onsystem.ftpserver.model.dto.PermissionWorkSpaceDto;
import com.onsystem.ftpserver.model.request.PermissionWorkSpaceCreateRequest;
import com.onsystem.ftpserver.service.PermissionWorkSpaceService;
import com.onsystem.ftpserver.utils.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/permissionWorkSpace/")
public class PermissionWorkSpaceController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Logger logger;
    @Autowired
    private PermissionWorkSpaceService permissionWorkSpaceService;

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
            PermissionWorkSpaceVO permissionWorkSpaceVO = objectMapper.convertValue(permissionWorkSpaceCreateRequest, PermissionWorkSpaceVO.class);
            objectId = permissionWorkSpaceService.insertPermissionSpace(permissionWorkSpaceVO);
        }catch (Exception e){
            logger.logWarning(getClass(), "Error endpoint insert PermissionWorkSpaceController");
        }

        return objectId.isPresent() ?
                new ResponseEntity<>(objectId.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
    @PostMapping("update")
    @Operation(
            description = "Update permission",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Can update permission"),
                    @ApiResponse( responseCode = "500", description = "Cant update permission")
            }
    )
    public ResponseEntity< ? > updatePermission(PermissionWorkSpaceDto permissionWorkSpaceDto){
        boolean result = false;
        try {
            PermissionWorkSpaceVO permissionWorkSpaceToUpdate = objectMapper.convertValue(permissionWorkSpaceDto, PermissionWorkSpaceVO.class);
            result = permissionWorkSpaceService.update(permissionWorkSpaceToUpdate);
        }catch (Exception e){
            logger.logWarning(getClass(), "Error endpoint update PermissionWorkSpaceController");
        }

        return result?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @GetMapping("findPermissionByWorkSpace/{id}")
    @Operation(
            description = "Find by all permissions assigned workSpace",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Can find Permission to workSpace"),
                    @ApiResponse( responseCode = "500", description = "Cant find Permission to workSpace")
            }
    )
    public ResponseEntity< ? > findPermissionByWorkSpace(@PathVariable String id){
        Optional< List< PermissionWorkSpaceDto > > permissionResult = Optional.empty();

        try {
            Optional< List < PermissionWorkSpaceVO > > permissionByWorkSpace = permissionWorkSpaceService.findByWorkSpace(new ObjectId(id));
            if(permissionByWorkSpace.isPresent())
                permissionResult = Optional.of(
                        Arrays.asList(
                                objectMapper.convertValue(permissionByWorkSpace.get(),PermissionWorkSpaceDto[].class)
                        )
                );

        }catch (Exception e){
            logger.logWarning(getClass(), "Error endpoint findPermissionByWorkSpace/{id}");
        }
        return permissionResult.isPresent() ?
                new ResponseEntity<>(permissionResult.get(),HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @GetMapping("findByUserAssigned/{id}")
    @Operation(
            description = "Find by all permissions assigned user",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Can find Permission to user"),
                    @ApiResponse( responseCode = "500", description = "Cant find Permission to user")
            }
    )
    public ResponseEntity< ? > findAllByUserAssigned(@PathVariable String id){
        Optional< List< PermissionWorkSpaceDto > > permissionResult = Optional.empty();

        try {
            Optional< List < PermissionWorkSpaceVO > > permissionByUser = permissionWorkSpaceService.findByUserAssignPermission( new ObjectId(id) );
            if( permissionByUser.isPresent() )
                permissionResult = Optional.of(
                        Arrays.asList(
                                objectMapper.convertValue(permissionByUser.get(),PermissionWorkSpaceDto[].class)
                        )
                );

        }catch (Exception e){
            logger.logWarning(getClass(), "Error endpoint findByUserAssigned/{id}");
        }

        return permissionResult.isPresent() ?
                new ResponseEntity<>(permissionResult.get(),HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
}
