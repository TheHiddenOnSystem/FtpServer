package com.onsystem.ftpserver.controllers;

import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.model.dto.UserDto;
import com.onsystem.ftpserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/user/")
public class UserController {


    @Autowired
    private UserService iUserService;

    @GetMapping("getMyInfo")
    @Operation(
            description = "Get Info user logged",
            responses = {
                    @ApiResponse(responseCode = "200", description = "If can return info"),
                    @ApiResponse(responseCode = "500", description = "Cant return info logged because if you not logged")
            }
    )
    public ResponseEntity < ? > userLoggedInfo(){
        Optional < UserDto > userVO = iUserService.findByUserLoggedDto();

        return userVO.isPresent() ?
                new ResponseEntity<>(userVO.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
        Optional < List < UserDto > > allUsers = iUserService.findAllUserDto();

        return allUsers.isPresent() ?
                new ResponseEntity<>(allUsers.get(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
