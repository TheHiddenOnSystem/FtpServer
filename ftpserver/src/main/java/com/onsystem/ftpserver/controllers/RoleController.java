package com.onsystem.ftpserver.controllers;

import com.onsystem.ftpserver.model.VO.RoleVO;
import com.onsystem.ftpserver.model.dto.RoleDto;
import com.onsystem.ftpserver.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/role/")
public class RoleController {
    @Autowired
    private RoleService iRoleService;


    @PutMapping("/insert")
    @Operation(
            description = "Insert role",
            responses = {
                    @ApiResponse( responseCode = "200", description = "If Is Create" ),
                    @ApiResponse( responseCode = "500", description = "Cant create")
            }
    )
    public ResponseEntity < ? > insertRole(@RequestParam String name){
        Optional< ObjectId > objectId = iRoleService.insertRole(name);

        return objectId.isPresent()?
                new ResponseEntity<>(objectId.get().toString(),HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAll")
    @Operation(
            description = "Get all role exist",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Can return elements" ),
                    @ApiResponse( responseCode = "500", description = "Cant return elements")
            }
    )
    public ResponseEntity < ? > getAllRole(){
        Optional< List<RoleDto> > roles = iRoleService.getAllRoleDto();

        return  roles.isPresent()?
                new ResponseEntity<>(roles.get(),HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
}
