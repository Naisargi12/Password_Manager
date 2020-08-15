package com.login.password.configuration;

import com.login.password.model.User;

import com.login.password.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (!Objects.isNull(name) && !Objects.isNull(password)) {
            User user = userService.findUserByEmail(name);
            if(!Objects.isNull(user) && bCryptPasswordEncoder.matches(password,user.getPassword())){
                return new UsernamePasswordAuthenticationToken(
                        name, password, new ArrayList<>());
            }
            // use the credentials
            // and authenticate against the third-party system
            throw new BadCredentialsException("Authentication failed");
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
