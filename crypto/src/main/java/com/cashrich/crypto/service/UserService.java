package com.cashrich.crypto.service;

import com.cashrich.crypto.constants.ResponseMessages;
import com.cashrich.crypto.controller.ResponseConstants;
import com.cashrich.crypto.dto.request.UpdateUserRequest;
import com.cashrich.crypto.entity.User;
import com.cashrich.crypto.helper.UserServiceHelper;
import com.cashrich.crypto.repository.UserRepository;
import com.cashrich.crypto.wrapper.ResponseEntityWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceHelper userServiceHelper;

    public ResponseEntity<?> updateUser(UserDetails userDetails, UpdateUserRequest updateUserRequest) {
        log.info("Update user : validating user");
        userServiceHelper.validateUserUpdateRequest(updateUserRequest);

        log.info("Update user : searching user in database");
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(updateUserRequest.getFirstName()!=null && !updateUserRequest.getFirstName().isEmpty()){
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if(updateUserRequest.getLastName()!=null && !updateUserRequest.getLastName().isEmpty()){
            user.setLastName(updateUserRequest.getLastName());
        }
        if(updateUserRequest.getMobile()!=null && !updateUserRequest.getMobile().isEmpty()){
            user.setMobile(updateUserRequest.getMobile());
        }

        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        log.info("Update user : updating user in database");
        userRepository.save(user);
        log.info("Update user : User updated successfully");

        return ResponseEntityWrapper.successResponseBuilder(ResponseMessages.USER_UPDATED_SUCCESSFULLY);
    }
}
