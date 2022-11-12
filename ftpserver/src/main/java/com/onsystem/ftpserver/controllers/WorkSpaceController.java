package com.onsystem.ftpserver.controllers;

import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.model.request.WorkSpaceCreateRequest;
import com.onsystem.ftpserver.service.WorkSpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/workspace/")
public class WorkSpaceController {

    @Autowired
    private WorkSpaceService workSpaceService;


    @PutMapping("insert")
    @Operation(
            description = "Create WorkSpace",
            responses = {
                    @ApiResponse( responseCode = "200", description = "WorkSpace create"),
                    @ApiResponse( responseCode = "500", description = "Cant create workspace")
            }
    )
    public ResponseEntity < ? > insertWorkSpace(@RequestBody WorkSpaceCreateRequest workSpaceCreateRequest){
        Optional< ObjectId > objectId = workSpaceService.insert(workSpaceCreateRequest);

        return objectId.isPresent() ?
                new ResponseEntity<>(objectId.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("getWorkSpace")
    @Operation(
            description = "Get All WorkSpace by User Owner",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Return User WorkSpace"),
                    @ApiResponse( responseCode = "500", description = "Cant return User WorkSpace")
            }
    )
    public ResponseEntity < ? > getWorkSpace(){
        Optional< List < WorkSpaceVO > > workSpaceVO = workSpaceService.findByIdUser();
        return workSpaceVO.isPresent()?
                new ResponseEntity<>(workSpaceVO.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @PutMapping("createDirectoryInWorkSpace")
    @Operation(
            description = "Create directory in WorkSpace",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Can create Directory"),
                    @ApiResponse( responseCode = "500", description = "Cant create Directory")
            }
    )
    public ResponseEntity < ? > createDirectoryInWorkSpace(
            @RequestParam String workSpaceDirectory,
            @RequestParam String name
    ){
        Optional< File > directory = workSpaceService.createDirectory(workSpaceDirectory, name);

        return directory.isPresent()?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @PutMapping("createFileInWorkSpace")
    @Operation(
            description = "Create file in WorkSpace",
            responses = {
                    @ApiResponse( responseCode = "200", description = "File created"),
                    @ApiResponse( responseCode = "500", description = "Cant create file")
            }
    )
    public ResponseEntity < ? > createFileInWorkSpace(
            @RequestParam ObjectId workSpace,
            @RequestParam String name
    ){
        Optional< File > file = workSpaceService.createFile(workSpace,name);

        return file.isPresent()?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
}