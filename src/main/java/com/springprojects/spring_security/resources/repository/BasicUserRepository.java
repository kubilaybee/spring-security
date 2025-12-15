package com.springprojects.spring_security.resources.repository;

import com.springprojects.spring_security.resources.entity.BasicUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicUserRepository extends JpaRepository<BasicUser, UUID> {
    Optional<BasicUser> findByUsername(String username);
}
