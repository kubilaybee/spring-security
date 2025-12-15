package com.springprojects.spring_security.resources.config;

import com.springprojects.spring_security.resources.entity.dto.BasicUserRequestDTO;
import com.springprojects.spring_security.resources.service.BasicUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class DataSeeder {
    @Bean
    public CommandLineRunner initializeUsers(BasicUserService userService) {
        return args -> {
            try {
                userService.findUserByUsername("admin");
            } catch (UsernameNotFoundException e) {
                BasicUserRequestDTO adminDto = new BasicUserRequestDTO("admin", "admin", "ADMIN");

                userService.saveNewUser(adminDto);
                System.out.println("Başlangıç kullanıcısı (admin) veritabanına başarıyla eklendi.");
            }
        };
    }
}
