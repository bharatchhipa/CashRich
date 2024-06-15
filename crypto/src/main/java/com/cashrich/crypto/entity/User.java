package com.cashrich.crypto.entity;

import com.cashrich.crypto.validator.Validator;
import com.cashrich.crypto.constants.ValidationMessages;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
public class User extends BaseEntity{
    @NotBlank(message = ValidationMessages.FIST_NAME_REQUIRED)
    private String firstName;
    @NotBlank(message = ValidationMessages.LAST_NAME_REQUIRED)
    private String lastName;
    @Column(unique = true)
    @Pattern(regexp = Validator.EMAIL_PATTERN, message = ValidationMessages.MOBILE_INVALID)
    @NotBlank
    private String email;
    @NotBlank(message=ValidationMessages.MOBILE_REQUIRED)
    @Pattern(regexp = Validator.MOBILE_PATTERN, message = ValidationMessages.MOBILE_INVALID)
    private String mobile;
    @Pattern(regexp = Validator.USERNAME, message = ValidationMessages.USERNAME_INVALID)
    @NotBlank(message=ValidationMessages.USERNAME_REQUIRED)
    private String username;
    @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
    private String password;
}


