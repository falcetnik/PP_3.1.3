package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;

@Component
public class InitUserAndAdmin {

    private final UserService userService;

    @Autowired
    public InitUserAndAdmin(UserService userService) {
        this.userService = userService;
    }

    public void createByBaseAdmin() {
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@mail.ru");
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));
        admin.setRoles(roles);
        userService.saveUser(admin);
    }

    public void createByBaseUser() {
        User user = new User();
        user.setLogin("user");
        user.setPassword("user");
        user.setEmail("user@mail.ru");
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role("ROLE_USER"));
        user.setRoles(roles);
        userService.saveUser(user);
    }
}
