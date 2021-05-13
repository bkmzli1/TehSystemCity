package ru.nti.tehsystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.nti.tehsystem.domain.User;
import ru.nti.tehsystem.model.UserRegisterBindingModel;
import ru.nti.tehsystem.repo.UserRepo;
import ru.nti.tehsystem.services.impl.UserService;


@AutoConfigureMockMvc
@SpringBootTest
public class UserTest extends Assertions {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Test
    public void UserRepoTest()
            throws Exception {

    }

    @Test
    public void UserServiceTest()
            throws Exception {
        Assertions.assertNotNull(userService);
    }


    @Test
    public void user() {
        crateUser();
        editUser("123");
        deleteUser();
    }
    @Test
    public void Notification(){

    }

    private void crateUser(){
        User userTest = this.userRepo.findOneByUsername("Test");
        if (userTest == null) {
            UserRegisterBindingModel user = new UserRegisterBindingModel();
            user.setPassword("Test");
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setMiddleName("Test");
            user.setEmail("Test");
            user.setUsername("Test");
            user.setAdmin(true);
            user.setExecutor(true);
            this.userService.create(user);
        } else {
            userRepo.delete(userTest);
            crateUser();
        }
    }
    private void deleteUser(){
        User userTest = this.userRepo.findOneByUsername("Test");
        userRepo.delete(userTest);
    }
    private void editUser(String email){
        crateUser();
        User userTest = this.userRepo.findOneByUsername("Test");
        userTest.setEmail(email);
        userRepo.save(userTest);
    }

}