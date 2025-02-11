package com.todolist.ToDoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todolist.ToDoList.model.User;
import com.todolist.ToDoList.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String RegisterPage() {
        return "register";
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, 
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/tasks"; // Redirect to To-Do List
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login"; // Show error on the same page
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroy session
        return "redirect:/login";
    }
}
