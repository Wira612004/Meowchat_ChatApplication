package com.wira.Chat.Application.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String login() {
        return "login"; // Serve login.html from the static directory
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, HttpSession session) {
        if (username == null || username.trim().isEmpty()) {
            return "redirect:/login"; // Redirect back to login page if username is empty
        }

        // Check if username is already in session to avoid duplication
        if (session.getAttribute("username") != null) {
            // Username already exists in session, redirect to chat.html
            return "redirect:/chat.html";
        }

        // Store username in session
        session.setAttribute("username", username);
        logger.info("User '{}' logged in.", username);

        // Redirect to chat.html
        return "redirect:/chat.html";
    }
}
