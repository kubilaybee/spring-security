package com.springprojects.spring_security.resources.controller;

import com.springprojects.spring_security.resources.entity.BasicUser;
import com.springprojects.spring_security.resources.entity.dto.BasicUserRequestDTO;
import com.springprojects.spring_security.resources.service.BasicUserService;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class MockUsersController {
    private final BasicUserService basicUserService;

    public MockUsersController(BasicUserService basicUserService) {
        this.basicUserService = basicUserService;
    }

    @GetMapping
    @PreAuthorize("hasRole('TESTER')")
    public ResponseEntity<List<BasicUser>> retrieveAllUsers() {
        return new ResponseEntity<>(basicUserService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicUser> retrieveUser(@PathVariable UUID uuid) {
        return new ResponseEntity<>(basicUserService.findUserByUuid(uuid), HttpStatus.OK);
    }

    @PostMapping
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<BasicUser> createUsers(@RequestBody BasicUserRequestDTO basicUserRequestDTO) {
        return new ResponseEntity<>(basicUserService.saveNewUser(basicUserRequestDTO), HttpStatus.CREATED);
    }

}
