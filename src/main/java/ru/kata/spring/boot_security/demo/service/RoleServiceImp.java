package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {

    @Override
    public Set<Role> createRoles(String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        if (role.equals("ROLE_ADMIN")) {
            roles.add(new Role("ROLE_ADMIN"));
        }
        return roles;
    }
}
