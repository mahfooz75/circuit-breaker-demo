package com.selftechlearner.patient_service.adapter.impl;

import com.selftechlearner.patient_service.adapter.DoctorAdapter;
import com.selftechlearner.patient_service.exception.DoctorNotFoundException;
import com.selftechlearner.patient_service.exception.DoctorServiceException;
import com.selftechlearner.patient_service.model.Doctor;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DoctorAdapterImpl implements DoctorAdapter {
    public static final String UNABLE_TO_REACH_DOCTOR_SERVICE = "Unable to reach doctor service";
    private final RestClient.Builder restClientBuilder;
    private RestClient restClient;
    private static final String BASE_URL = "http://localhost:8080/doctor";

    @PostConstruct
    public void init() {
        restClient = restClientBuilder.baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    @CircuitBreaker(name = "doctorServiceCircuitBreaker", fallbackMethod = "doctorFallBack")
    public Map<String, List<Doctor>> getAllSpecializedDoctorsDetails() {
        log.info("Calling doctor service using REST CLIENT to get Doctor Information");
        try {
            return restClient.get()
                    .uri("/details")
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, ((request, response) -> {
                        logMessage(response);
                        throw new DoctorServiceException(UNABLE_TO_REACH_DOCTOR_SERVICE);
                    }))
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        logMessage(response);
                        throw new DoctorServiceException("Something went wrong");
                    }))
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception e) {
            throw new DoctorServiceException(UNABLE_TO_REACH_DOCTOR_SERVICE);
        }
    }

    @Override
    @CircuitBreaker(name = "doctorServiceCircuitBreaker", fallbackMethod = "doctorNotFoundFallBack")
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        log.info("Calling doctor service to get Doctors by specialization");
        try {
            return restClient.get()
                    .uri("/details/" + specialization)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        logMessage(response);
                        throw new DoctorNotFoundException("Doctor Not Found");
                    }))
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        logMessage(response);
                        throw new DoctorServiceException("Something went wrong");
                    }))
                    .body(new ParameterizedTypeReference<>() {
                    });
        } catch (DoctorNotFoundException e) {
            log.error("Doctor Not Found: {}", e.getMessage());
            throw new DoctorNotFoundException(e.getMessage());
        } catch (HttpClientErrorException e) {
            logError(e);
            throw new DoctorServiceException(e.getMessage());
        }
    }

    private static void logError(HttpClientErrorException e) {
        log.error("Client error: {}", e.getStatusCode() + " " + e.getResponseBodyAsString());
    }

    private void logMessage(ClientHttpResponse response) throws IOException {
        log.info("Status code:: {} \nStatus text:: {} \nResponse body:: {}", response.getStatusCode(), response.getStatusText(), inputStreamToString(response.getBody()));
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public List<Doctor> doctorNotFoundFallBack(Throwable throwable) {
        log.error("Calling fallback {}", throwable.getMessage(), throwable);
        return Collections.emptyList();
    }

    public Map<String, List<Doctor>> doctorFallBack(Throwable throwable) {
        log.error("Calling fallback while using RestTemplate {}", throwable.getMessage(), throwable);
        return Map.of("Try after some time", Collections.emptyList());
    }
}
