package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    private RoleRepository roleRepository;


    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByLogin(String username) {
        return userRepository.findByLogin(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByLoginOrEmail(String loginOrEmail) throws UsernameNotFoundException {
        User user;
        user = userRepository.findByLogin(loginOrEmail);
        if (user == null) {
            user = userRepository.findByEmail(loginOrEmail);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String nameOrEmail) throws UsernameNotFoundException {
        User user = findByLogin(nameOrEmail);
        if (user != null) {
            user.setUserDetailsName(user.getUsername());
        } else {
            user = findByEmail(nameOrEmail);
            if (user != null) {
                user.setUserDetailsName(user.getEmail());
            }
        }
        if (user == null) {
            throw new UsernameNotFoundException(
                    "No user with uch name or email: " + nameOrEmail
            );
        }
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Override
    public void saveUser(User user) {
        roleRepository.saveAll(user.getRoles());
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            saveUser(user);
        }
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
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
        return userRepository .getAllIds();
    }
}
