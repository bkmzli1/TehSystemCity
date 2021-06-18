package ru.nti.tehsystem.services.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nti.tehsystem.domain.Roles;
import ru.nti.tehsystem.model.in.RoleServiceModel;
import ru.nti.tehsystem.repo.RoleRepo;
import ru.nti.tehsystem.services.impl.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepo roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        Roles roles = this.roleRepository.findByAuthority(authority);
        RoleServiceModel roleModel = null;
        if (roles != null) {
            roleModel = this.modelMapper.map(roles, RoleServiceModel.class);
        }
        return roleModel;
    }

    @Override
    public void addRole(RoleServiceModel roleServiceModel) {
        Roles roles = this.modelMapper.map(roleServiceModel, Roles.class);
        this.roleRepository.save(roles);
    }
}
