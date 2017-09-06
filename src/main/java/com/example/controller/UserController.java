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

    @GetMapping("/user/get/{id}")
    public User getUser(@PathVariable("id") long id) {
        return userService.getUser(id);
    }

    @PostMapping("/user/add")
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping ("/user/update")
    public User updateUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/user/delete/{id}")
    public List<User> deleteUser(@PathVariable("id") long id) {
        return userService.deleteUser(id);
    }

}
