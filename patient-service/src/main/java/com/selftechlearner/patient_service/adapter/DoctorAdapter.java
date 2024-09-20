package com.selftechlearner.patient_service.adapter;

import com.selftechlearner.patient_service.model.Doctor;

import java.util.List;
import java.util.Map;

public interface DoctorAdapter {
    Map<String, List<Doctor>> getAllSpecializedDoctorsDetails();

    List<Doctor> getDoctorsBySpecialization(String specialization);
}
