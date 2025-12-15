package com.springprojects.spring_security.resources.service;

import com.springprojects.spring_security.resources.entity.BasicUser;
import com.springprojects.spring_security.resources.entity.Role;
import com.springprojects.spring_security.resources.entity.dto.BasicUserRequestDTO;
import com.springprojects.spring_security.resources.repository.BasicUserRepository;
import com.springprojects.spring_security.resources.repository.RoleRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BasicUserService {
    private final BasicUserRepository basicUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public BasicUserService(BasicUserRepository basicUserRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.basicUserRepository = basicUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<BasicUser> findAllUsers() {
        return basicUserRepository.findAll();
    }

    public BasicUser findUserByUsername(String username) {
        Optional<BasicUser> byUsername = basicUserRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return byUsername.get();
    }

    public BasicUser findUserByUuid(UUID uuid) {
        Optional<BasicUser> byId = basicUserRepository.findById(uuid);
        if (byId.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + byId);
        }
        return byId.get();
    }

    @Transactional
    public BasicUser saveNewUser(BasicUserRequestDTO basicUserRequestDTO) {
        if (basicUserRepository.findByUsername(basicUserRequestDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exist");
        }
        String rawPassword = basicUserRequestDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Role userRole = roleRepository.findByName(basicUserRequestDTO.getRole())
                .orElseGet(() -> {
                    Role newRole = Role.builder().name(basicUserRequestDTO.getRole()).build();
                    return roleRepository.saveAndFlush(newRole);
                });
        Set<Role> roles = new HashSet<>(Collections.singleton(userRole));

        BasicUser newUser = BasicUser.builder()
                .username(basicUserRequestDTO.getUsername())
                .password(encodedPassword)
                .roles(roles)
                .enabled(true)
                .build();

        return basicUserRepository.saveAndFlush(newUser);
    }
}
