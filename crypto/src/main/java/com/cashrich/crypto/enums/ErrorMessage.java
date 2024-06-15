package com.cashrich.crypto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    GENERIC("Oops! Something went wrong. Please try again."),
    UNEXPECTED_ERROR("Oops! An unexpected error has occurred. Please try after sometime."),
    BAD_REQUEST("Invalid request"),
    METHOD_NOT_ALLOWED_ERROR("Request method not supported."),
    REFRESH_REQUEST("Please refresh the page and try again."),
    ACCESS_DENIED("You are not authorised to access this portal, please login from correct portal."),
    RESPONSE_PARSING("Error in response parsing."),
    TOKEN_GENERATION_FAILURE("Please refresh the page and try again."),
    BAD_FILE_TYPE(" is not supported by the application, please refrain from crafting malicious requests!");
    private String message;
}

