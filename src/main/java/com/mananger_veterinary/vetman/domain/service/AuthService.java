package com.mananger_veterinary.vetman.domain.service;

import com.mananger_veterinary.vetman.domain.repository.OwnerRepository;
import com.mananger_veterinary.vetman.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(
            OwnerRepository ownerRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> login(String email, String password) {
        if (email == null || password == null) {
            return Optional.empty();
        }

        return ownerRepository.findByEmail(email)
                .filter(owner -> passwordEncoder.matches(password, owner.getPassword()))
                .map(owner -> jwtUtil.generateToken(owner.getEmail()));
    }
}