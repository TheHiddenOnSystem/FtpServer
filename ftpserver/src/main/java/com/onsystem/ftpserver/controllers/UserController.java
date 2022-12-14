package com.onsystem.ftpserver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.model.dto.UserDto;
import com.onsystem.ftpserver.service.UserService;
import com.onsystem.ftpserver.utils.ILogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/user/")
public class UserController {

    @Autowired
    private ILogger logger;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService iUserService;


    @GetMapping("isAuthenticate")
    @Operation(
            description = "Test if isAuthenticate",
            responses = {
                    @ApiResponse(responseCode = "200", description = "If is Authenticate"),
                    @ApiResponse(responseCode = "403", description = "If isn't Authenticate"),
                    @ApiResponse(responseCode = "500", description = "If isn't authentication")
            }
    )
    public ResponseEntity< ? > isAuthenticate (){
        boolean isAuthenticate = iUserService.isAuthenticate();
        return isAuthenticate
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("getMyInfo")
    @Operation(
            description = "Get Info user logged",
            responses = {
                    @ApiResponse(responseCode = "200", description = "If can return info"),
                    @ApiResponse(responseCode = "500", description = "Cant return info logged because if you not logged")
            }
    )
    public ResponseEntity < ? > userLoggedInfo(){
        Optional < UserDto > userDto = Optional.empty();

        try {
            Optional<UserVO> userVO = iUserService.findByUserLogged();
            if(userVO.isPresent())
                userDto = Optional.of(
                        objectMapper.convertValue(userVO.get(), UserDto.class)
                );
        }catch (Exception e){
            logger.logWarning(getClass(), "Cant cast Vo to Dto in userLoggedInfo ", e);
        }

        return userDto.isPresent()
                ? new ResponseEntity<>(userDto.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("allUsers")
    @Operation(
            description = "Get All Users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "If can return users info"),
                    @ApiResponse(responseCode = "500", description = "Cant return users info")
            }
    )
    public ResponseEntity < ? > getAllUsers(){


        try {
            List < UserVO > allUsers = iUserService.findAll();

            List < UserDto > allUsersDto = Arrays.asList(
                    objectMapper.convertValue(allUsers, UserDto[].class)
            );

            return new ResponseEntity<>(allUsersDto,HttpStatus.OK);

        }catch (Exception e){
            this.logger.logWarning(getClass(),"Error get all user register",e);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
