package ru.nti.tehsystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nti.tehsystem.domain.User;

@Controller
@RequestMapping("")
public class Main {
    @GetMapping("/reg")
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String loginGet(Model model, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return "redirect:/ordersCreate/";
        }catch (NullPointerException nullPointerException){
            return "index";
        }


    }

    @GetMapping("/profile")
    public String profileGet(Model model) {
        return "index";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}