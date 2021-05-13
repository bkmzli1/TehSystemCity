package ru.nti.tehsystem.services.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nti.tehsystem.domain.EmailConfirmed;
import ru.nti.tehsystem.domain.Img;
import ru.nti.tehsystem.domain.Roles;
import ru.nti.tehsystem.domain.User;
import ru.nti.tehsystem.model.RoleServiceModel;
import ru.nti.tehsystem.model.UserEditBindingModel;
import ru.nti.tehsystem.model.UserRegisterBindingModel;
import ru.nti.tehsystem.repo.EmailConfirmedRepo;
import ru.nti.tehsystem.repo.ImgRepo;
import ru.nti.tehsystem.repo.UserRepo;
import ru.nti.tehsystem.services.impl.RoleService;
import ru.nti.tehsystem.services.impl.UserService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final ImgRepo imgRepository;
    private final MailService mailService;
    private final EmailConfirmedRepo emailConfirmedRepo;

    @Value("${urlSever}")
    private String urlSever;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper, RoleService roleService, ImgRepo imgRepository, MailService mailService, EmailConfirmedRepo emailConfirmedRepo) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.imgRepository = imgRepository;
        this.mailService = mailService;
        this.emailConfirmedRepo = emailConfirmedRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong");
        }

        return user;
    }

    @Override
    public void create(UserRegisterBindingModel userServiceModel) {
        User userEntity = this.modelMapper.map(userServiceModel, User.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setPs(userEntity.getPassword());
        if (userServiceModel.getImg() == null) {
            Img img = new Img();
            img.setImg("static/user.png");
            img.setName("userImg");
            userEntity.setImg(img);

            imgRepository.save(img);

        }
        if (userServiceModel.getImg() == null) {
            Img imgFon = new Img();
            imgFon.setImg("static/vk.png");
            imgFon.setName("fon");
            userEntity.setImgFon(imgFon);
            imgRepository.save(imgFon);
        }

        Set<Roles> authorities = new HashSet<>();
        RoleServiceModel roleServiceModel = null;

        roleServiceModel = this.roleService.findByAuthority("USER");
        Roles roles = this.modelMapper.map(roleServiceModel, Roles.class);

        authorities.add(roles);

        if (userServiceModel.isAdmin()) {
            roleServiceModel = this.roleService.findByAuthority("ADMIN");
            Roles rolesAdmin = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesAdmin);
        }
        if (userServiceModel.isExecutor()) {
            roleServiceModel = this.roleService.findByAuthority("EXECUTOR");
            Roles rolesExecutor = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesExecutor);
        }


        userEntity.setAuthorities(authorities);
        this.userRepository.save(userEntity);
        if (!userServiceModel.getEmail().isEmpty() & !userServiceModel.getEmail().equals("-")) {
            EmailConfirmed emailConfirmed = new EmailConfirmed();
            emailConfirmed.setUser(userEntity);
            emailConfirmedRepo.save(emailConfirmed);
            if (emailConfirmed.getCode() == null) {
                emailConfirmed.setCode(UUID.randomUUID().toString());
                emailConfirmedRepo.save(emailConfirmed);
            }
            mailService.send(userEntity.getEmail(), "Activation code", "URL: http://" +urlSever+"/activate/" +emailConfirmed.getCode());
        }
    }

    @Override
    public void edit(UserEditBindingModel userServiceModel) {
        User userEntity = this.modelMapper.map(userServiceModel, User.class);
        if (!userServiceModel.isPassordof())
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        else {
            userEntity.setPassword(userRepository.findOneById(userServiceModel.getId()).getPassword());
        }

        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setPs(userEntity.getPassword());
        if (userServiceModel.getImg() == null) {
            Img img = new Img();
            img.setImg("static/user.png");
            img.setName("userImg");
            userEntity.setImg(img);

            imgRepository.save(img);

        }
        if (userServiceModel.getImg() == null) {
            Img imgFon = new Img();
            imgFon.setImg("static/vk.png");
            imgFon.setName("fon");
            userEntity.setImgFon(imgFon);
            imgRepository.save(imgFon);
        }

        Set<Roles> authorities = new HashSet<>();
        RoleServiceModel roleServiceModel = null;

        roleServiceModel = this.roleService.findByAuthority("USER");
        Roles roles = this.modelMapper.map(roleServiceModel, Roles.class);

        authorities.add(roles);

        if (userServiceModel.isAdmin()) {
            roleServiceModel = this.roleService.findByAuthority("ADMIN");
            Roles rolesAdmin = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesAdmin);
        }
        if (userServiceModel.isExecutor()) {
            roleServiceModel = this.roleService.findByAuthority("EXECUTOR");
            Roles rolesExecutor = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesExecutor);
        }


        userEntity.setAuthorities(authorities);
        this.userRepository.save(userEntity);
    }

    @Override
    public void edit(User userServiceModele) {

    }

    @Override
    public boolean isUsernameTaken(String username) {
        return this.userRepository.findOneByUsername(username) != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return this.userRepository.findByEmail(email) != null;
    }

    @Override
    public UserRegisterBindingModel findByUsername(String username) {
        User userEntity = this.userRepository.findOneByUsername(username);
        UserRegisterBindingModel userModel = null;
        if (userEntity != null) {
            userModel = this.modelMapper.map(userEntity, UserRegisterBindingModel.class);
        }
        return userModel;
    }

    @Override
    public User findById(String id) {
        User userEntity = this.userRepository.findOneById(id);
        return userEntity;
    }


}