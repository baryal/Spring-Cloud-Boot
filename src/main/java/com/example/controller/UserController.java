package com.example.controller;

import com.example.model.Employee;
import com.example.model.User;
import com.example.service.EmployeeService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public User login(User user) {
        return userService.login(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/user/add")
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    /*@GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") int id) {
        return employeeService.getEmployee(id);
    }
*/

}
