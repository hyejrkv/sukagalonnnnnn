package com.impal.sukagalon.services;


import com.impal.sukagalon.models.User;
import com.impal.sukagalon.repositories.UserRepository;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        // Validate input
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Check if user already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already registered");
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("BUYER"); // Default role
            
            // Save user to database
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while registering user: " + e.getMessage());
        }
    }

    
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }
}

