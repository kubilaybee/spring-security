package com.springprojects.spring_security.resources.service;

import com.springprojects.spring_security.resources.entity.BasicUser;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final BasicUserService basicUserService;

    public CustomUserDetailsService(BasicUserService basicUserService) {
        this.basicUserService = basicUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BasicUser basicUser = basicUserService.findUserByUsername(username);
        Collection<GrantedAuthority> authorities = basicUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return User.builder()
                .username(basicUser.getUsername())
                .password(basicUser.getPassword())
                .authorities(authorities)
                .disabled(!basicUser.isEnabled())
                .build();
    }
}
