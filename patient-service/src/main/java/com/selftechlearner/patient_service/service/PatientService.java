package com.selftechlearner.patient_service.service;

import com.selftechlearner.patient_service.model.Doctor;

import java.util.List;
import java.util.Map;

public interface PatientService {
    Map<String, List<Doctor>> getAllSpecializedDoctorsDetails();

    List<Doctor> getDoctorsBySpecialization(String specialization);
}
