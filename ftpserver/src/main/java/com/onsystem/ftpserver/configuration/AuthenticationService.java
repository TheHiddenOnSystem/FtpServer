package com.onsystem.ftpserver.configuration;

import com.onsystem.ftpserver.model.VO.User;
import com.onsystem.ftpserver.service.IRoleService;
import com.onsystem.ftpserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AuthenticationService implements UserDetailsService {

    @Autowired
    private ILogger logger;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserService.findByUserName(username)
                .orElseThrow();

        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.authenticated(user.getUsername(),user.getPassword(),user.getAuthorities())
        );

        return user;
    }
}
