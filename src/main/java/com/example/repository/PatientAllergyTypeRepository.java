package com.example.repository;


import com.example.model.AllergyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientAllergyTypeRepository extends JpaRepository<AllergyType, String> {

    List<AllergyType> findAll();
}
