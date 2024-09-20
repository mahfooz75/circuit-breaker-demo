package com.selftechlearner.doctor_service.service.impl;

import com.selftechlearner.doctor_service.exception.DoctorNotFoundException;
import com.selftechlearner.doctor_service.model.Doctor;
import com.selftechlearner.doctor_service.service.DoctorService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private Map<String, List<Doctor>> map;

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return Optional.ofNullable(map.get(specialization))
                .orElseThrow(() -> {
                    log.error("Doctor not found for Specialization {}", specialization);
                    return new DoctorNotFoundException("Doctor not found for Specialization " + specialization);
                });
    }

    @Override
    public Map<String, List<Doctor>> getAllSpecializedDoctorsDetails() {
        return map;
    }

    @PostConstruct
    public void init() {
        Doctor doctor1 = new Doctor("Aarav", "Mehta", "Cardiology");
        Doctor doctor2 = new Doctor("Isha", "Patel", "Neurology");
        Doctor doctor3 = new Doctor("Sanya", "Desai", "Cardiology");

        map = Stream.of(doctor1, doctor2, doctor3)
                .collect(Collectors.groupingBy(Doctor::specialization, Collectors.toList()));
    }


}
