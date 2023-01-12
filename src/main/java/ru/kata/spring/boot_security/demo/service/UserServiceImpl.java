package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByLogin(String username) {
        return userRepository.findByLogin(username);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        if (user != null) {
            user.setUserDetailsName(user.getUsername());
        }
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user with uch name or email: " + login
            );
        }
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleRepository.saveAll(user.getRoles());
        userRepository.save(user);
        System.out.println(user);
    }

    @Override
    public void updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            saveUser(user);
        }
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsersList() {
        return userRepository.findAll();
    }

    @Override
    public List<Long> getAllIds() {
        return userRepository.getAllIds();
    }

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
