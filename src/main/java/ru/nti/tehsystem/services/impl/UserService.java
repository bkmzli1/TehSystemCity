package ru.nti.tehsystem.services.impl;


import org.springframework.security.core.userdetails.UserDetailsService;
import  ru.nti.tehsystem.domain.User;
import ru.nti.tehsystem.model.in.UserEditBindingModel;
import ru.nti.tehsystem.model.in.UserRegisterBindingModel;

public interface UserService extends UserDetailsService {
    void create(UserRegisterBindingModel userServiceModele);
    void edit(UserEditBindingModel userServiceModele);



    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    UserRegisterBindingModel findByUsername(String username);
    UserRegisterBindingModel findById(String id);

    void emailAtive(User user);



}