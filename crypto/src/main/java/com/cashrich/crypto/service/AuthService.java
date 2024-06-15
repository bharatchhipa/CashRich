package com.cashrich.crypto.service;

import com.cashrich.crypto.constants.ResponseMessages;
import com.cashrich.crypto.dto.request.LoginRequest;
import com.cashrich.crypto.dto.request.SignupRequest;
import com.cashrich.crypto.dto.response.ResponseWrapper;
import com.cashrich.crypto.entity.User;
import com.cashrich.crypto.enums.StatusCode;
import com.cashrich.crypto.helper.AuthServiceHelper;
import com.cashrich.crypto.repository.UserRepository;
import com.cashrich.crypto.utility.jwt.CustomUserDetailsService;
import com.cashrich.crypto.utility.jwt.JwtUtil;
import com.cashrich.crypto.wrapper.ResponseEntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthServiceHelper authServiceHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> signUp(SignupRequest signUpRequest){

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            log.info("Sign Up : Email already exists");
            return ResponseEntityWrapper.badRequestException(ResponseMessages.EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            log.info("Sign Up : Username already exists");
            return ResponseEntityWrapper.badRequestException(ResponseMessages.USERNAME_ALREADY_EXISTS);
        }
        log.info("Sign Up : preparing user sign up request");
        User user  =  authServiceHelper.prepareUser(signUpRequest);
        userRepository.save(user);
        log.info("Sign Up : User created successfully.");
        return ResponseEntityWrapper.successResponseBuilder(ResponseMessages.USER_CREATED_SUCCESSFULLY);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            log.info("Login : authenticating user");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            log.info("Login : invalid credentials");
            return ResponseEntityWrapper.badRequestException(ResponseMessages.INVALID_CREDENTIALS);

        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntityWrapper.successResponseBuilder(ResponseMessages.USER_LOGGED_IN_SUCCESSFULLY,token);
    }
}
