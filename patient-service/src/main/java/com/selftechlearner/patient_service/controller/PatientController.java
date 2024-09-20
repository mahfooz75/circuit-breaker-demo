package com.selftechlearner.patient_service.controller;

import com.selftechlearner.patient_service.model.Doctor;
import com.selftechlearner.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@Slf4j
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/appointment")
    public Map<String, List<Doctor>> getDoctorDetails() {
        return patientService.getAllSpecializedDoctorsDetails();
    }

    @GetMapping("/appointment/{specialization}")
    public List<Doctor> getDoctorDetailsBySpecialization(@PathVariable String specialization) {
        return patientService.getDoctorsBySpecialization(specialization);
    }
}
