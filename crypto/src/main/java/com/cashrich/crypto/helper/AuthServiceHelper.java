package com.cashrich.crypto.helper;

import com.cashrich.crypto.dto.request.SignupRequest;
import com.cashrich.crypto.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHelper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User prepareUser(SignupRequest signupRequest){
        User  user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setMobile(signupRequest.getMobile());
        return user;
    }
}
