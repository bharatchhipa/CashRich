package com.cashrich.crypto.controller;

import com.cashrich.crypto.dto.request.UpdateUserRequest;
import com.cashrich.crypto.repository.UserRepository;
import com.cashrich.crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDetails userDetails,@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(userDetails,updateUserRequest);
    }
}
