package com.onsystem.ftpserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.PermissionVO;
import com.onsystem.ftpserver.service.PermissionService;
import com.onsystem.ftpserver.utils.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/permission")
@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private Logger logger;
    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/all")
    @Operation(
            description = "Get All permission to assign in workSpace",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Return All permission" ),
                    @ApiResponse( responseCode = "500", description = "Cant Return All permission")
            }
    )
    public ResponseEntity< ? > getAllPermission() {
        ResponseEntity< ? > response = null;
        try {
            List<PermissionVO > permissionVO = this.permissionService.getPermissionAll();
            response = new ResponseEntity<>(permissionVO,HttpStatus.OK);
        }catch (Exception e){
            logger.logWarning(getClass(),"Error controller get all permission", e);

            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @PostMapping("/insert")
    @Operation(
            description = "Insert new permission to assign in workspace permission in future",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Created" ),
                    @ApiResponse( responseCode = "500", description = "Cant create")
            }
    )
    public ResponseEntity< ? > insertPermission (@RequestParam String name) {
        ResponseEntity < ? > response = null;

        try {
            PermissionVO permissionVO = new PermissionVO();
            permissionVO.setName(name);
            ObjectId objectId = this.permissionService.insertPermission(permissionVO);
            response = new ResponseEntity<>(objectId,HttpStatus.OK);
        }catch (Exception e){
            logger.logWarning(getClass(),"Error controller insert permission", e);

            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

}
