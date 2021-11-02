package com.management.user.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.management.user.exceptions.BusinessException;
import com.management.user.exceptions.ExceptionResponse;
import com.management.user.exceptions.InvalidInputException;
import com.management.user.exceptions.ResourceAlreadyExists;
import com.management.user.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.NOT_FOUND);
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());
        response.setDetailedMessage(ex.getStackTrace()[0]);
        logger.error("Resource not found Exception: "+ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { ResourceAlreadyExists.class })
    public ResponseEntity<ExceptionResponse> resourceAlreadyExists(ResourceAlreadyExists ex) {
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode(HttpStatus.CONFLICT);
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());
        response.setDetailedMessage(ex.getStackTrace()[0]);
        logger.error("Resource Already exists Exception: "+ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> customException(BusinessException ex) {
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());
        response.setDetailedMessage(ex.getStackTrace()[0]);
        logger.error("Business Exception: ",ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(UnauthorizedException.class)
//    public ResponseEntity<ExceptionResponse> unauthorizedException(UnauthorizedException ex) {
//        ExceptionResponse response=new ExceptionResponse();
//        response.setErrorCode(HttpStatus.UNAUTHORIZED);
//        response.setErrorMessage(ex.getMessage());
//        response.setTimeStamp(LocalDateTime.now());
//        response.setDetailedMessage(ex.getStackTrace()[0]);
//        logger.error("Unauthourized Exception: "+ex.getMessage());
//        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(value = { InvalidInputException.class })
    public ResponseEntity<ExceptionResponse> handleInvalidInputException(InvalidInputException ex) {
        ExceptionResponse response=new ExceptionResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST);
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());
        response.setDetailedMessage(ex.getStackTrace()[0]);
        logger.error("Invalid Input Exception: "+ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }
}

