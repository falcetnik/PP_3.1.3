package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;


public interface UserService extends UserDetailsService {

    @Transactional(readOnly = true)
    User findByLogin(String username) throws UsernameNotFoundException;

    @Override
    @Transactional(readOnly = true)
    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;

    @Transactional
    void saveUser(User user);

    @Transactional
    void updateUser(User user);

    @Transactional
    void deleteUserById(long id);

    @Transactional(readOnly = true)
    List<User> getAllUsersList();

    @Transactional(readOnly = true)
    List<Long> getAllIds();
}