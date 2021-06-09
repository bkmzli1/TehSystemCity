package ru.nti.tehsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nti.tehsystem.domain.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {

    User findUserById(String id);

    User findOneByUsername(String username);

    User findByEmail(String email);

    User findOneById(String id);

    User findByAuthoritiesAuthorityAndId(String authorities_authority, String id);
    List<User> findByAuthoritiesAuthority(String authorities_authority);


    List<User> findUsersByAuthoritiesAuthority(String authorities);
}
