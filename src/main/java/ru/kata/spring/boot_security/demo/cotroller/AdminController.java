package ru.kata.spring.boot_security.demo.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserValidator userValidator, UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getTemplateForAdminList(ModelMap model) {
        model.addAttribute("users", userService.getAllUsersList());
        model.addAttribute("user", new User());
        model.addAttribute("ids", userService.getAllIds());
        return "admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") String id) {
        userService.deleteUserById(Integer.parseInt(id));
        return "redirect:/admin";
    }


    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam("role") String role) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        user.setRoles(roleService.createRoles(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        user.setRoles(roleService.createRoles(role));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}