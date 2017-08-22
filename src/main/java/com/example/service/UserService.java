package com.example.service;

import com.example.model.Address;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.xml.ws.http.HTTPException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void addUsers() {
        for(int i = 1; i <= 30; i++){

            Address address = new Address();
            address.setAddressType("Home" + i);
            address.setStreet("4551 Kipling St., Apt# " + i);
            address.setCity("Wheat Ridge" + i);
            address.setState("Colorado" + i);
            address.setZipCode("80033-" + i);


            User user = new User();
            user.setFirstName("Test First Name " + i);
            user.setLastName("Test Last Name " + i);
            user.setDateOfBirth(i + "/08/2017");
            user.setGender("Male " + i);
            user.setEmail("test"+ i + "@gmail.com");
            user.setConsent("I agree" + i);
            user.setPassword("password" + i);

            user.setAddress(address);
            address.setUser(user);

            save(user);
        }
    }

    public List<User> getAllUsers()  {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User login(User user) {
        Optional<User> optional = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(!optional.isPresent()) {
            throw new ValidationException("Incorrect email/password. Please try again later.");
        }
        return optional.get();
    }
}
