package com.onsystem.ftpserver.controllers;

import com.onsystem.ftpserver.dto.UserRegister;
import com.onsystem.ftpserver.model.request.UserLoggingRequest;
import com.onsystem.ftpserver.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(
        value = "/api/v1/auth"
)
public class AuthenticationController {


    @Autowired
    private IUserService iUserService;


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
    public ResponseEntity < ? > loginUser(@RequestBody UserLoggingRequest userLoggingRequest
                                        ){



        return new ResponseEntity<>(iUserService.authenticateUser(userLoggingRequest.getName(), userLoggingRequest.getPassword()).isPresent()?
                HttpStatus.ACCEPTED:
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/register")
    @Operation(
            description = "Register User to Start Experience",
            responses = {
                    @ApiResponse( responseCode = "200", description = "If Is register" ),
                    @ApiResponse( responseCode = "500", description = "Cant Register")
            }
    )
    public ResponseEntity < ? > register(@RequestBody UserRegister userRegister){

        boolean isCreate = iUserService.insertUser(userRegister).isPresent();

        return new ResponseEntity<>(isCreate?
                HttpStatus.OK:
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping(value = "/aaaa")
    @Operation
    public ResponseEntity < ? > aaaaaaaaa(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
