package com.onsystem.ftpserver.controllers;

import com.onsystem.ftpserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {


    @Autowired
    private IUserService iUserService;



}
