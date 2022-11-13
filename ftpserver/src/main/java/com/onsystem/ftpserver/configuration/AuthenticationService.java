package com.onsystem.ftpserver.configuration;

import com.onsystem.ftpserver.model.VO.UserVO;
import com.onsystem.ftpserver.repository.RoleRepository;
import com.onsystem.ftpserver.repository.UserRepository;
import com.onsystem.ftpserver.utils.AttributeSession;
import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.utils.ManagerAttributesSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthenticationService implements UserDetailsService {

    @Autowired
    private ILogger logger;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository iRoleService;

    @Autowired
    private ManagerAttributesSession managerAttributesSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserVO user = userRepository.findByUserName(username)
                .orElseThrow();

        AttributeSession attributeSession = new AttributeSession(user.getObjectId());
        managerAttributesSession.setAttributesInHttpSession(attributeSession);

        return user;
    }
}
