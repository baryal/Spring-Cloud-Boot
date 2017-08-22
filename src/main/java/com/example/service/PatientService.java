package com.example.service;

import com.example.model.AllergyType;
import com.example.repository.PatientAllergyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {


    @Autowired
    private PatientAllergyTypeRepository allergyTypeRepository;

    public List<AllergyType> getAllergies() {
        return allergyTypeRepository.findAll();
    }
}
