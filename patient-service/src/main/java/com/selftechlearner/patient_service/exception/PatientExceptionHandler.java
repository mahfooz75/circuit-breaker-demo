package com.selftechlearner.patient_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j

public class PatientExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomProblemDetail> exception(Exception ex, HttpServletRequest request) {
        log.error("Error occurred {}", ex.getMessage(), ex);
        Throwable cause = ex.getCause();
        String causeMsg = cause.toString();
        log.info("**************************************************");
        Arrays.stream(cause.getStackTrace()).forEach(ste -> {
            log.info("Class Loader:: {}", ste.getClassLoaderName());
            log.info("Class Name:: {}", ste.getClassName());
            log.info("File Name:: {}", ste.getFileName());
            log.info("Method Name:: {}", ste.getMethodName());
            log.info("Module Name:: {}", ste.getModuleName());
            log.info("Module Version:: {}", ste.getModuleVersion());
            log.info("Line Number:: {}", ste.getLineNumber());
            log.info("Class Name:: {}", ste.getClass().getName());
            log.info("**************************************************");
        });
        //ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        CustomProblemDetail problemDetail = new CustomProblemDetail();
        String requestURL = request.getRequestURL().toString();
        //problemDetail.setType(URI.create(requestURL));
        //problemDetail.setTitle(ex.getMessage()+" "+causeMsg);
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("errorMsg", causeMsg);
        problemDetail.setTitle("");
        problemDetail.setType(null);
        problemDetail.setDetail(""); // This will be ignored in JSON
        problemDetail.setInstance(URI.create("")); // This will be ignored in JSON
        //problemDetail.setStatus(404);
        /*properties.put("type", "");
        properties.put("title", "");
        properties.put("status", causeMsg);
        properties.put("detail", "");
        properties.put("instance", "");*/
        problemDetail.setProperties(properties);
        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}