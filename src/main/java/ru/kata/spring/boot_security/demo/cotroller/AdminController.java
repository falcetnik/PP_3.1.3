package ru.kata.spring.boot_security.demo.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getTemplateForAdminList(ModelMap model) {
        model.addAttribute("users", userService.getAllUsersList());
        model.addAttribute("user", new User());
        model.addAttribute("ids", userService.getAllIds());
        return "admin-list";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") String id) {
        userService.deleteUserById(Integer.parseInt(id));
        return "redirect:/admin";
    }


    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        user.setRoles(userService.createRoles(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        user.setRoles(userService.createRoles(role));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}