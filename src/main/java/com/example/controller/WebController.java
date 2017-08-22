package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WebController {

    /*@Autowired
    @Qualifier(value = "authenticationManager")
    private AuthenticationManager authenticationManager;*/

    @PostMapping("/loginUser")
    public Map<String, String> login(@RequestParam("login") String username, @RequestParam("password") String password) {
        Map<String, String> response = new HashMap<String, String>();

        /*UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            response.put("status", "true");
            return response;
        } catch (BadCredentialsException ex) {
            response.put("status", "false");
            return response;
        }*/

        return null;
    }
}
