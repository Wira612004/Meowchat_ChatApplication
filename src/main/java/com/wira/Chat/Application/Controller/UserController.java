package com.wira.Chat.Application.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/getUsername")
    public Map<String, String> getUsername(HttpSession session) {
        String username = (String) session.getAttribute("username");
        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        return response;
    }
}
