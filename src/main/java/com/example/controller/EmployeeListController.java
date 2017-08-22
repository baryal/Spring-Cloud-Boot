package com.example.controller;

import com.example.service.EmployeeService;
import com.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class EmployeeListController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello world spring boot.";
    }



    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployee();
    }

    @PostMapping("/employee/add")
    public String addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") int id) {
        return employeeService.getEmployee(id);
    }


}
