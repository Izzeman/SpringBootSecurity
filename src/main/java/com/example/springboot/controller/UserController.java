package com.example.springboot.controller;

import com.example.springboot.model.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@EnableWebSecurity
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("auth/login")
    public String getLogin(){
        return "login";
    }


    @GetMapping("/user")
    public String getSuccess(Principal principal, Model model){
        String userMail = principal.getName();
        model.addAttribute("user", userService.getByEmail(userMail));
        return "user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public String index(Model model){
        model.addAttribute("users", userService.readUsers());
        return "allUsers";
    }

    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public String showUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.readUser(id));
        return "show";
    }

    @GetMapping("/admin/new")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/admin/new")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public String createUser(@ModelAttribute("user") User user){
        String BCryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(BCryptPassword);
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public String editUser(Model model, @PathVariable("id") Long id){
        model.addAttribute("user", userService.readUser(id));
        return "edit";
    }

    @PatchMapping("/admin/{id}/edit")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id){
        User encryptedPasswordUser = user;
        encryptedPasswordUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(id, encryptedPasswordUser);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}/remove")
    @PreAuthorize("hasAnyAuthority('developers:write')")
    public String deleteUser(@PathVariable("id") Long id, Model model){
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
