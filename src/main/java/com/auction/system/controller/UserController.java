package com.auction.system.controller;
import com.auction.system.entity.User;
import com.auction.system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUser(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("username", userService.getUserByUsername(username));
        return "user/profile";
    }

    @PostMapping
    public String updateUser(@ModelAttribute User user, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        userService.updateUser(user);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deleteAccount(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            userService.deleteAccount(username);
            session.removeAttribute("username");
        }
        return "redirect:/login?logout=true";
    }
}
