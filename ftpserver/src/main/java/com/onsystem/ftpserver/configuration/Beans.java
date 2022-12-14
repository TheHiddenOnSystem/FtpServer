package com.onsystem.ftpserver.configuration;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.onsystem.ftpserver.utils.ILogger;
import com.onsystem.ftpserver.utils.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
public class Beans {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsSessionService(){
        return new AuthenticationService();
    }
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        mapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL,true);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES,false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        return mapper;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .and()
                .httpBasic()
                .disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                //.cors().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/*")
                .hasRole("user")
                .antMatchers("/api/v1/auth/**")
                .permitAll();

        http.formLogin().disable();

        return http.build();
    }


    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public ILogger logger(){
        return new Logger();
    }
}
