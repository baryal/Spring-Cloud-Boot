package com.example.service;

import com.example.model.Employee;
import com.example.repository.PatientAllergyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {


    @Autowired
    private PatientAllergyTypeRepository allergyRepository;

    private List<Employee> employees = new ArrayList<>();

    @PostConstruct
    public void init() {
        Employee employee1 = new Employee();
        employee1.setName("Babu");
        employee1.setLastName("Aryal");
        employee1.setId(1);
        employees.add(employee1);
        Employee employee2 = new Employee();
        employee2.setName("Sagun");
        employee2.setLastName("Aryal");
        employee2.setId(2);
        employees.add(employee2);
        Employee employee3 = new Employee();
        employee3.setName("Aaron");
        employee3.setLastName("Aryal");
        employee3.setId(3);
        employees.add(employee3);
    }

    public List<Employee> getAllEmployee() {
        return employees;
    }


    public String addEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return "Successfully added!!";
    }


    public Employee getEmployee(int id) {
        Employee employee = new Employee();
        employees.forEach(employee1 -> {
            if(employee1.getId() == id) {
                employee.setId(id);
                employee.setName(employee1.getName());
                employee.setLastName(employee1.getLastName());
            }
        });
        return employee;
    }
}
