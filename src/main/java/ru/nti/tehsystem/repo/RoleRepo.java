package ru.nti.tehsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import  ru.nti.tehsystem.domain.Roles;

public interface RoleRepo extends JpaRepository<Roles, String> {
    Roles findByAuthority(String authority);
}
