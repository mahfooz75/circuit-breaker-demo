package com.selftechlearner.doctor_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
@Slf4j
public class DoctorExceptionHandler {
    @ExceptionHandler(DoctorServiceException.class)
    public ResponseEntity<ProblemDetail> onException(DoctorServiceException ex, HttpServletRequest request) {
        log.error("Error occurred {}", ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        String requestURL = request.getRequestURL().toString();
        problemDetail.setType(URI.create(requestURL));
        problemDetail.setTitle(ex.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ProblemDetail> onException(DoctorNotFoundException ex, HttpServletRequest request) {
        log.error("Error occurred {}", ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        String requestURL = request.getRequestURL().toString();
        problemDetail.setType(URI.create(requestURL));
        problemDetail.setTitle(ex.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> onException(Exception ex, HttpServletRequest request) {
        log.error("Error occurred {}", ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        String requestURL = request.getRequestURL().toString();
        problemDetail.setType(URI.create(requestURL));
        problemDetail.setTitle(ex.getMessage());
        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
