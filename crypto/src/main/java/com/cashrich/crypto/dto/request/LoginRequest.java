package com.cashrich.crypto.dto.request;

import com.cashrich.crypto.validator.Validator;
import com.cashrich.crypto.constants.ValidationMessages;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LoginRequest {
    @Pattern(regexp = Validator.USERNAME, message = ValidationMessages.USERNAME_INVALID)
    @NotBlank(message= ValidationMessages.USERNAME_REQUIRED)
    private String username;
    @NotBlank(message=ValidationMessages.PASSWORD_REQUIRED)
    private String password;
}
