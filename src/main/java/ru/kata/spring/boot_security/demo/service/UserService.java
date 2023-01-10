package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    @Transactional(readOnly = true)
    User findByLogin(String username) throws UsernameNotFoundException;

    @Transactional(readOnly = true)
    User findByEmail(String email) throws UsernameNotFoundException;

    @Transactional(readOnly = true)
    User findByLoginOrEmail(String loginOrEmail) throws UsernameNotFoundException;

    @Override
    @Transactional(readOnly = true)
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Transactional
    void saveUser(User user);

    @Transactional
    void updateUser(User user);

    @Transactional
    void deleteAllUsers();

    @Transactional(readOnly = true)
    User getUserById(long id);

    @Transactional
    void deleteUserById(long id);

    @Transactional(readOnly = true)
    List<User> getAllUsersList();

    @Transactional(readOnly = true)
    List<Long> getAllIds();
}