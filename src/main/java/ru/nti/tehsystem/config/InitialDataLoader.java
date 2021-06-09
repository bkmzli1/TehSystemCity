package ru.nti.tehsystem.config;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import  ru.nti.tehsystem.model.RoleServiceModel;
import  ru.nti.tehsystem.model.UserRegisterBindingModel;
import  ru.nti.tehsystem.services.impl.RoleService;
import  ru.nti.tehsystem.services.impl.UserService;

@Component
@CrossOrigin(origins = "http://localhost:4200")
public class InitialDataLoader implements ApplicationRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public InitialDataLoader(RoleService roleService, UserService userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void run(ApplicationArguments args) {
        RoleServiceModel userRole = this.roleService.findByAuthority("USER");
        RoleServiceModel executorRole = this.roleService.findByAuthority("EXECUTOR");
        RoleServiceModel executorAllRole = this.roleService.findByAuthority("EXECUTOR_ALL");
        RoleServiceModel adminRole = this.roleService.findByAuthority("ADMIN");
        RoleServiceModel superAdminAdminRole = this.roleService.findByAuthority("SUPER_ADMIN");

        UserRegisterBindingModel userRoot = null;
        try {
            userRoot= this.userService.findByUsername("root");
        }catch (Exception e){}

        UserRegisterBindingModel userExecutor= null;

        try {
            userExecutor = this.userService.findByUsername("root");
        }catch (Exception e){}
        if (userRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("USER");
            this.roleService.addRole(roleServiceModel);
        }
        if (executorRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("executor".toUpperCase());
            this.roleService.addRole(roleServiceModel);

        }

        if (adminRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("ADMIN");
            this.roleService.addRole(roleServiceModel);
        }

        if (superAdminAdminRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("SUPER_ADMIN");
            this.roleService.addRole(roleServiceModel);
        }
        if (executorAllRole == null) {
            RoleServiceModel roleServiceModel = new RoleServiceModel();
            roleServiceModel.setAuthority("EXECUTOR_ALL");
            this.roleService.addRole(roleServiceModel);
        }

        if (userRoot == null){
            UserRegisterBindingModel user = new UserRegisterBindingModel();
            user.setPassword("root");
            user.setFirstName("Илья");
            user.setLastName("Егорушкин");
            user.setMiddleName("Андреевич");
            user.setEmail("ntiniaymifi@gmail.com");
            user.setUsername("root");
            user.setAdmin(true);
            user.setSuperAdmin(true);
            this.userService.create(user);
        }
        if (userExecutor == null){
            UserRegisterBindingModel user = new UserRegisterBindingModel();
            user.setPassword("dqwfdwsfdwfws");
            user.setEmail("-");
            user.setUsername("Все исполнители");
            user.setLastName("Все исполнители");
            user.setFirstName(" ");
            user.setMiddleName(" ");
            user.setExecutor(true);
            user.setExecutorAll(true);
            user.setSuperAdmin(true);
            this.userService.create(user);
        }
    }
}