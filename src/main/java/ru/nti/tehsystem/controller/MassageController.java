package ru.nti.tehsystem.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import  ru.nti.tehsystem.domain.Task;
import  ru.nti.tehsystem.domain.User;
import  ru.nti.tehsystem.domain.Views;
import ru.nti.tehsystem.model.in.MassageModel;
import  ru.nti.tehsystem.repo.TaskRepo;
import  ru.nti.tehsystem.repo.UserRepo;
import  ru.nti.tehsystem.services.impl.MassageService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/massage")
public class MassageController {
    private final TaskRepo taskRepo;
    private final MassageService massageService;
    private final UserRepo userRepo;

    public MassageController(TaskRepo taskRepo, MassageService massageService, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.massageService = massageService;
        this.userRepo = userRepo;
    }

    @JsonView(Views.TaskAll.class)
    @PostMapping
    @ResponseBody
    public Task ms(@RequestBody() @Valid MassageModel massageModel, Authentication authentication) {


        try {
            return massageService.massageCrate(massageModel, (User) authentication.getPrincipal());
        } catch (NullPointerException npe) {
            return massageService.massageCrate(massageModel, userRepo.findAll().get(0));
        }
    }

}
