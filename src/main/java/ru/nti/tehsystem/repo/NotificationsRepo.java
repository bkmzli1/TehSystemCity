package ru.nti.tehsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nti.tehsystem.domain.Notifications;

public interface NotificationsRepo extends JpaRepository<Notifications, String> {


}
