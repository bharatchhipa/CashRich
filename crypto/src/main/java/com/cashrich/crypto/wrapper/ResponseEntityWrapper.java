package com.cashrich.crypto.wrapper;

import com.cashrich.crypto.controller.ResponseConstants;
import com.cashrich.crypto.dto.response.ResponseWrapper;
import com.cashrich.crypto.enums.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityWrapper {
    public static ResponseEntity<ResponseWrapper> genericException(String message) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.FAILURE)
                .message(message)
                .code(StatusCode.INTERNAL_SERVER_ERROR.getCode())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseWrapper> badRequestException(String message) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.FAILURE)
                .message(message)
                .code(StatusCode.BAD_REQUEST.getCode())
                .build(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ResponseWrapper> badRequestException(String message, Object data) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.FAILURE)
                .message(message)
                .data(data)
                .code(StatusCode.BAD_REQUEST.getCode())
                .build(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ResponseWrapper> accessDeniedException(String message) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.FAILURE)
                .message(message)
                .code(StatusCode.FORBIDDEN.getCode())
                .build(), HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<ResponseWrapper> authenticationFailureException(String message) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.FAILURE)
                .message(message)
                .code(StatusCode.UNAUTHORIZED.getCode())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<ResponseWrapper> successResponseBuilder(String message) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.SUCCESS)
                .message(message)
                .code(StatusCode.OK.getCode())
                .build(), HttpStatus.OK);
    }
    public static ResponseEntity<ResponseWrapper> successResponseBuilder(String message,Object data) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.SUCCESS)
                .message(message)
                .data(data)
                .code(StatusCode.OK.getCode())
                .build(), HttpStatus.OK);
    }

    public static ResponseEntity<ResponseWrapper> failureResponseBuilder(String message,Object data, HttpStatus httpStatus) {
        return new ResponseEntity<>(ResponseWrapper.builder()
                .status(ResponseConstants.FAILURE)
                .message(message)
                .data(data)
                .code(StatusCode.INTERNAL_SERVER_ERROR.getCode())
                .build(), httpStatus);
    }



}


