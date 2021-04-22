package ru.nti.tehsystem.services.impl;


import org.springframework.security.core.userdetails.UserDetailsService;
import  ru.nti.tehsystem.domain.User;
import  ru.nti.tehsystem.model.UserEditBindingModel;
import  ru.nti.tehsystem.model.UserRegisterBindingModel;

public interface UserService extends UserDetailsService {
    void create(UserRegisterBindingModel userServiceModele);
    void edit(UserEditBindingModel userServiceModele);

    void edit(User userServiceModele);

    boolean isUsernameTaken(String username);

    boolean isEmailTaken(String email);

    UserRegisterBindingModel findByUsername(String username);

    User findById(String id);

}