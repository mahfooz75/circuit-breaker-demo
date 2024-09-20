package com.selftechlearner.doctor_service.controller;

import com.selftechlearner.doctor_service.model.Doctor;
import com.selftechlearner.doctor_service.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/details")
    public Map<String, List<Doctor>> details() {
        return doctorService.getAllSpecializedDoctorsDetails();
    }

    @GetMapping("/details/{specialization}")
    public List<Doctor> details(@PathVariable String specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);
    }
}
