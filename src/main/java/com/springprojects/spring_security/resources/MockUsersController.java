package com.springprojects.spring_security.resources;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockUsersController {

    private static final List<BasicUser> BASIC_USERS =
            List.of(
                    new BasicUser(UUID.randomUUID(), "first", "password1"),
                    new BasicUser(UUID.randomUUID(), "second", "password2")
            );

    @GetMapping("/users")
    public List<BasicUser> retrieveAllUsers() {
        return BASIC_USERS;
    }

    @GetMapping("/users/{uuid}")
    public BasicUser retrieveUser(@PathVariable UUID uuid) {

        return BASIC_USERS.stream().filter(user -> user.getUuid().equals(uuid)).findFirst().get();
    }

    @PostMapping("/users")
    public List<BasicUser> createUsers(String username, String password) {
        BASIC_USERS.add(new BasicUser(UUID.randomUUID(), username, password));
        return BASIC_USERS;
    }

}
