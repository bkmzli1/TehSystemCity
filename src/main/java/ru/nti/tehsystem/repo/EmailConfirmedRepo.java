package ru.nti.tehsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nti.tehsystem.domain.EmailConfirmed;
import ru.nti.tehsystem.domain.User;

public interface EmailConfirmedRepo extends JpaRepository<EmailConfirmed, String> {

    EmailConfirmed findByCodeAndUser(String code, User user);
}
