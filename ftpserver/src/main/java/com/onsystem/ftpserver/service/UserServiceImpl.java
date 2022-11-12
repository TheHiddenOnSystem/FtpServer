package com.onsystem.ftpserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onsystem.ftpserver.configuration.ILogger;
import com.onsystem.ftpserver.dto.UserRegister;
import com.onsystem.ftpserver.model.VO.User;
import com.onsystem.ftpserver.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private ILogger logger;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<ObjectId> insertUser(UserRegister userRegister) {
        try{
            findByUserName(userRegister.getUserName()).ifPresent(user1 -> {
                throw new IllegalArgumentException();
            });

            User user = objectMapper.convertValue(userRegister, User.class);
            user.setPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
            user.setEnabled(true);
            user.setAccountExpired(false);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            return Optional.of(userRepository.save(user).getId());
        }catch (Exception e){
            this.logger.logInfo(this.getClass(),"Cant create user");
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<Integer> authenticateUser(String name, String password) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return Optional.empty();
    }
}
