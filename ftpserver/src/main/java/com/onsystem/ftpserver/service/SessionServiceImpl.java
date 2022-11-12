package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.repository.UserRepository;
import com.onsystem.ftpserver.utils.ILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionServiceImpl implements SessionService{
    @Autowired
    private ILogger logger;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public boolean login(String username, String password) {
        try {
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }catch (Exception e){
            logger.logWarning(getClass(),"No se ha podido logear el usuario: "+username);
        }

        return false;
    }

    @Override
    public boolean logout() {
        try{
            SecurityContextHolder.clearContext();
            httpSession.invalidate();
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
