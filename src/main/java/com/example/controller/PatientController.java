package com.example.controller;

import com.example.service.PatientService;
import com.example.model.AllergyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patient/allergyTypes")
    public List<AllergyType> getAllergies() {
        return patientService.getAllergies();
    }

}
