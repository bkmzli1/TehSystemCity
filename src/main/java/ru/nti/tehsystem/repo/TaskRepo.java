package ru.nti.tehsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nti.tehsystem.domain.Massages;
import  ru.nti.tehsystem.domain.Task;
import  ru.nti.tehsystem.domain.User;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, String> {
    List<Task> findByCreator(User creator);
    List<Task> findByExecutor(User creator);
    Task findActionByMassages(Massages massages);
}
