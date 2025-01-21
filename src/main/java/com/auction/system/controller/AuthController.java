package com.auction.system.controller;

import com.auction.system.entity.User;
import com.auction.system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model, HttpSession session) {
        userService.registerUser(user);
        return loginUser(user, model, session);
    }

    @GetMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }


    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        try {
            userService.loginUser(user.getUsername(), user.getPassword());
            session.setAttribute("username", user.getUsername());
        } catch (RuntimeException e) {
            model.addAttribute("loginError", e.getMessage());
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:/login?logout=true";
    }

}
