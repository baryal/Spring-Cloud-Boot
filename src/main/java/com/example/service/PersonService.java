package com.example.service;

import com.example.model.hibernateInheritance.tableperclass.Employee1;
import com.example.model.hibernateInheritance.tableperclass.Owner1;
import com.example.model.hibernateInheritance.tableperclass.Person1;
import com.example.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public void save() {
        Person1 person1 = new Person1("Steve", "Balmer");
        //person1.setPersonId(1L);
        personRepository.save(person1);

        Employee1 employee = new Employee1("James", "Gosling", "Marketing", new Date());
        //employee.setPersonId(2L);
        personRepository.save(employee);

        Owner1 owner1 = new Owner1("Bill", "Gates", 300L, 20L);
        owner1.setPartnershipStake(200L);//Lombok
        personRepository.save(owner1);
    }
}
