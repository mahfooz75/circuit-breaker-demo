package com.selftechlearner.patient_service.service.impl;

import com.selftechlearner.patient_service.adapter.DoctorAdapter;
import com.selftechlearner.patient_service.exception.DoctorNotFoundException;
import com.selftechlearner.patient_service.exception.DoctorServiceException;
import com.selftechlearner.patient_service.model.Doctor;
import com.selftechlearner.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final DoctorAdapter doctorAdapter;

    @Override
    public Map<String, List<Doctor>> getAllSpecializedDoctorsDetails() {
        try {
            return doctorAdapter.getAllSpecializedDoctorsDetails();
        } catch (Exception e) {
            throw new DoctorServiceException("UNABLE_TO_REACH_DOCTOR_SERVICE");
        }
    }


    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        try {
            return doctorAdapter.getDoctorsBySpecialization(specialization);
        } catch (DoctorNotFoundException e) {
            throw new DoctorNotFoundException("Doctor Not Found");
        } catch (Exception e) {
            throw new DoctorServiceException("UNABLE_TO_REACH_DOCTOR_SERVICE");
        }
    }

}
