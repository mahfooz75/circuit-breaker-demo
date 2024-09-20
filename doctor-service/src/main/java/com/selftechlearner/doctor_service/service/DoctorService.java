package com.selftechlearner.doctor_service.service;

import com.selftechlearner.doctor_service.model.Doctor;

import java.util.List;
import java.util.Map;

public interface DoctorService {
    List<Doctor> getDoctorsBySpecialization(String specialization);
    Map<String, List<Doctor>> getAllSpecializedDoctorsDetails();
}
