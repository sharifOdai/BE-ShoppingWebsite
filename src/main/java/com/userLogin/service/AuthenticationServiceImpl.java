package com.userLogin.service;

import com.userLogin.model.User;
import com.userLogin.security.CustomUserDetailsService;
import com.userLogin.security.model.AuthenticationRequest;
import com.userLogin.security.model.AuthenticationResponse;
import com.userLogin.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private CustomUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception exception) {
            throw new Exception("Incorrect Username Or Password");
        }

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        User user = userService.findUserByUsername(authenticationRequest.getUsername());

        if (user == null) {
            throw new Exception("User not found");
        }

        Long userId = user.getId();

        return new AuthenticationResponse(jwtUtil.generateToken(userId, userDetails.getUsername()));
    }
}



