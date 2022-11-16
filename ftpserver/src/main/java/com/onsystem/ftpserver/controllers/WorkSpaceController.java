package com.onsystem.ftpserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.WorkSpaceVO;
import com.onsystem.ftpserver.model.dto.StructFile;
import com.onsystem.ftpserver.model.dto.WorkSpaceDto;
import com.onsystem.ftpserver.model.request.InsertFileMultipart;
import com.onsystem.ftpserver.model.request.WorkSpaceCreateRequest;
import com.onsystem.ftpserver.service.FileStorageService;
import com.onsystem.ftpserver.service.WorkSpaceService;
import com.onsystem.ftpserver.utils.AttributeSession;
import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.utils.ManagerAttributesSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/workspace/")
public class WorkSpaceController {
    @Autowired
    private ILogger logger;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ManagerAttributesSession managerAttributesSession;
    @Autowired
    private WorkSpaceService workSpaceService;
    @Autowired
    private FileStorageService fileStorageService;


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
                new ResponseEntity<>(objectId.get().toString(), HttpStatus.OK) :
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
        Optional< List <WorkSpaceDto> > workSpaceResult = Optional.empty();

        try {
            AttributeSession attributeSession = managerAttributesSession.getAttributesInHttpSession();
            Optional< List < WorkSpaceVO > > workSpaceVOS = workSpaceService.findByIdUser(attributeSession.getObjectId());

            if(workSpaceVOS.isPresent())
                workSpaceResult = Optional.of(
                        Arrays.asList(
                                objectMapper.convertValue(workSpaceVOS.get(), WorkSpaceDto[].class)
                        )
                );

        }catch (Exception e){
            logger.logWarning(getClass(), "Error endpoint getWorkSpace");
        }


        return workSpaceResult.isPresent()?
                new ResponseEntity<>(workSpaceResult.get(), HttpStatus.OK) :
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
            @RequestParam String workSpace,
            @RequestParam String pathName
    ){
        Optional< File > file = workSpaceService.createFile(workSpace,pathName);

        return file.isPresent()?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @GetMapping("listDirectories/{idWorkSpace}")
    @Operation(
            description = "List all files or directories in workSpace",
            responses = {
                    @ApiResponse( responseCode = "200", description = "Can list directories" ),
                    @ApiResponse( responseCode = "500", description = "Cant list directories" )
            }
    )
    public ResponseEntity< ? > listDirectories( @PathVariable String idWorkSpace ){
        Optional< StructFile > structFile = Optional.empty();
        Optional< WorkSpaceVO > workSpaceVO = workSpaceService.findById(new ObjectId(idWorkSpace));

        if( workSpaceVO.isPresent() )
            structFile = fileStorageService.listAllFiles(workSpaceVO.get().getUser().toHexString(),idWorkSpace);

        return structFile.isPresent()
                ? new ResponseEntity<>(structFile.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "insertFile", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    } )
    @Operation(
            description = "Insert file in workSpace"
    )
    public ResponseEntity< ? > insertFile(@ModelAttribute InsertFileMultipart insertFileMultipart){
        Optional< WorkSpaceVO > workSpaceVO = workSpaceService.findById(new ObjectId(insertFileMultipart.getWorkSpaceId()));
        boolean resultInsertOperation = false;

        if(workSpaceVO.isPresent())
            resultInsertOperation = fileStorageService.save(
                    insertFileMultipart.getFile(),
                    workSpaceVO.get().getUser().toHexString(),
                    workSpaceVO.get().getObjectId().toHexString(),
                    insertFileMultipart.getPath(),
                    insertFileMultipart.getFile().getOriginalFilename()
            );


        return resultInsertOperation
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
