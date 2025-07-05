package com.example.visitor.service;

import com.example.visitor.model.Admin;
import com.example.visitor.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Optional<Admin> authenticate(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }
}
