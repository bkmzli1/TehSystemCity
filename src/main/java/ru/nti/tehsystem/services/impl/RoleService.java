package ru.nti.tehsystem.services.impl;


import  ru.nti.tehsystem.model.RoleServiceModel;

public interface RoleService {

    RoleServiceModel findByAuthority(String authority);

    void addRole(RoleServiceModel roleServiceModel);
}
