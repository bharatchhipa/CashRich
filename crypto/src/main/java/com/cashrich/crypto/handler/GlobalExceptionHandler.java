package com.cashrich.crypto.handler;

import com.cashrich.crypto.controller.ResponseConstants;
import com.cashrich.crypto.dto.response.ResponseWrapper;
import com.cashrich.crypto.enums.ErrorMessage;
import com.cashrich.crypto.enums.StatusCode;
import com.cashrich.crypto.exceptions.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper> handleFieldsException(MethodArgumentNotValidException mve) {
        log.error("Invalid request detected.", mve);
        Map<String, String> errors = new HashMap<>();
        mve.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(ResponseWrapper.builder().message(ErrorMessage.BAD_REQUEST.getMessage()).data(errors).code(StatusCode.BAD_REQUEST.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Bad Request exception : ", e);
        Set<String> violations = new HashSet<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            violations.add(violation.getMessage());
        }
        return new ResponseEntity<>(ResponseWrapper.builder().status(ResponseConstants.FAILURE).message(ErrorMessage.BAD_REQUEST.getMessage()).data(violations).code(StatusCode.BAD_REQUEST.getCode()).build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value= UsernameNotFoundException.class)
    public ResponseEntity<ResponseWrapper> userNameNotException(String message) {
        log.error("Bad Request exception : ", message);
        return new ResponseEntity<>(ResponseWrapper.builder().message(message).code(StatusCode.BAD_REQUEST.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseWrapper> handleBadRequestException(BadRequestException bde) {
        log.error("Bad Request exception : ", bde);
        return new ResponseEntity<>(ResponseWrapper.builder().message(bde.getMessage()).code(StatusCode.BAD_REQUEST.getCode()).data(bde.getData()).status(ResponseConstants.FAILURE).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleDataNotFoundException(DataNotFoundException dnfe) {
        log.error("Data Not Found exception : ", dnfe);
        return new ResponseEntity<>(ResponseWrapper.builder().message(dnfe.getMessage()).code(StatusCode.NOT_FOUND.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DataValidationException.class)
    public ResponseEntity<ResponseWrapper> handleDataValidationException(DataValidationException dve) {
        log.error("Data Validation exception : ", dve);
        return new ResponseEntity<>(ResponseWrapper.builder().message(dve.getMessage()).code(StatusCode.BAD_REQUEST.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResponseWrapper> handleAccessDeniedException(AccessDeniedException ade) {
        log.error("AccessDenied exception : ", ade);
        return new ResponseEntity<>(ResponseWrapper.builder().message(ade.getMessage()).code(StatusCode.FORBIDDEN.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({TooManyRequestException.class})
    public ResponseEntity<ResponseWrapper> handleTooManyRequestException(TooManyRequestException tme) {
        log.error("Too many request exception : ", tme);
        return new ResponseEntity<>(ResponseWrapper.builder().message(tme.getMessage()).code(StatusCode.TOO_MANY_REQUESTS.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ResponseWrapper> handleConflictException(ConflictException ce) {
        log.error("Conflict exception : ", ce);
        return new ResponseEntity<>(ResponseWrapper.builder().message(ce.getMessage()).code(StatusCode.CONFLICT.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({OptimisticLockingFailureException.class})
    public ResponseEntity<ResponseWrapper> handleOptimisticLockingFailureException(OptimisticLockingFailureException ce) {
        log.error("OptimisticLockingFailureException exception : ", ce);
        return new ResponseEntity<>(ResponseWrapper.builder().message(ce.getMessage()).code(StatusCode.CONFLICT.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ServerException.class)
    public ResponseEntity<ResponseWrapper> handleServerException(ServerException se) {
        log.error("Server exception detected : ", se);
        return new ResponseEntity<>(ResponseWrapper.builder().message(se.getMessage()).code(StatusCode.INTERNAL_SERVER_ERROR.getCode()).status(ResponseConstants.FAILURE).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ResponseWrapper> handleRuntimeException(RuntimeException e) {
        log.error("Runtime exception detected : ", e);
        return new ResponseEntity<>(ResponseWrapper.builder().message(ErrorMessage.GENERIC.getMessage()).code(StatusCode.INTERNAL_SERVER_ERROR.getCode()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseWrapper> handleGenericException(Exception e) {
        log.error("An exception detected : ", e);
        return new ResponseEntity<>(ResponseWrapper.builder().message(ErrorMessage.GENERIC.getMessage()).code(StatusCode.INTERNAL_SERVER_ERROR.getCode()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseWrapper> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Http Request Method Not Supported exception detected : ",e);
        return new ResponseEntity<>(ResponseWrapper.builder().message(ErrorMessage.METHOD_NOT_ALLOWED_ERROR.getMessage()).code(StatusCode.METHOD_NOT_ALLOWED.getCode()).build(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Http Message Not Readable exception detected : ",e);
        return new ResponseEntity<>(ResponseWrapper.builder().message(ErrorMessage.BAD_REQUEST.getMessage()).code(StatusCode.BAD_REQUEST.getCode()).build(), HttpStatus.BAD_REQUEST);
    }
}
