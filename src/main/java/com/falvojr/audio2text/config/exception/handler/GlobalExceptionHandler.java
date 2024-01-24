package com.falvojr.audio2text.config.exception.handler;

import com.falvojr.audio2text.config.exception.ApplicationBusinessException;
import com.falvojr.audio2text.config.exception.EnterpriseBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for centralized management of exceptions in the application.
 * Catches and handles various types of exceptions, providing appropriate HTTP responses. <br>
 * <br>
 * Responsibilities: <br>
 * - Define the application's response to different types of exceptions. <br>
 * - Log the exceptions for audit and debugging purposes.
 *
 * @author falvojr
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ ApplicationBusinessException.class, EnterpriseBusinessException.class })
    public ResponseEntity<ApiError> handleApplicationBusinessException(RuntimeException e) {
        logger.debug("Business exception occurred: {}", e.getMessage());
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {
        logger.error("Unexpected exception occurred: ", e);
        return new ResponseEntity<>(new ApiError("An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ApiError(String message) {}
}

