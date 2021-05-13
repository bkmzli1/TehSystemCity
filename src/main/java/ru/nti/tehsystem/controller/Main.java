package ru.nti.tehsystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nti.tehsystem.domain.EmailConfirmed;
import ru.nti.tehsystem.domain.User;
import ru.nti.tehsystem.repo.EmailConfirmedRepo;
import ru.nti.tehsystem.repo.UserRepo;

@Controller
@RequestMapping("")
public class Main {
    private final UserRepo userRepository;
    private final EmailConfirmedRepo emailConfirmedRepo;

    public Main(UserRepo userRepository, EmailConfirmedRepo emailConfirmedRepo) {
        this.userRepository = userRepository;
        this.emailConfirmedRepo = emailConfirmedRepo;
    }

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

    @GetMapping("/activate/{code}")
    public Object activate(Authentication authentication, @PathVariable String code) {
        try {
            User user = userRepository.findUserById(((User) authentication.getPrincipal()).getId());
            EmailConfirmed byCodeAndUser = emailConfirmedRepo.findByCodeAndUser(code, user);
            if (byCodeAndUser != null){
                user.setEmailConfirmed(true);
                userRepository.save(user);
            }
            return "redirect:/usere/"+user.getId();
        }catch (NullPointerException nullPointerException){
            return "redirect:/login";
        }

    }

}