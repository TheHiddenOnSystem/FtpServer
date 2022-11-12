package com.onsystem.ftpserver.controllers;

import com.onsystem.ftpserver.model.request.UserRegisterRequest;
import com.onsystem.ftpserver.model.request.UserLoggingRequest;
import com.onsystem.ftpserver.service.UserService;
import com.onsystem.ftpserver.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(
        value = "/api/v1/auth"
)
public class AuthenticationController {


    @Autowired
    private SessionService authenticateUser;
    @Autowired
    private UserService iUserService;


    @Operation(
            description = "Logging User",
            responses = {
                    @ApiResponse( responseCode = "200", description = "If Is Authenticate"),
                    @ApiResponse( responseCode = "500", description = "Not Authenticate")
            }
    )
    @PostMapping(
            value = "/loginUser"
    )
    public ResponseEntity < ? > loginUser(@RequestBody UserLoggingRequest userLoggingRequest){

        return new ResponseEntity<>(
                authenticateUser.login(userLoggingRequest.getName(), userLoggingRequest.getPassword())?
                HttpStatus.ACCEPTED:
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PutMapping("/register")
    @Operation(
            description = "Register User to Start Experience",
            responses = {
                    @ApiResponse( responseCode = "200", description = "If Is register" ),
                    @ApiResponse( responseCode = "500", description = "Cant Register")
            }
    )
    public ResponseEntity < ? > register(@RequestBody UserRegisterRequest userRegister){

        boolean isCreate = iUserService.insertUser(userRegister).isPresent();

        return new ResponseEntity<>(isCreate ?
                HttpStatus.OK :
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @GetMapping("/logout")
    @Operation(
            description = "Log Out Session",
            responses = {
                    @ApiResponse( responseCode = "200", description = "If Is logged" ),
                    @ApiResponse( responseCode = "500", description = "Cant logout")
            }
    )
    public ResponseEntity < ? > logOut(){
        boolean isLogOut = authenticateUser.logout();

        return new ResponseEntity<>(isLogOut ?
                HttpStatus.OK :
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
