package ru.nti.tehsystem.services.impl;

import  ru.nti.tehsystem.domain.Task;
import  ru.nti.tehsystem.domain.User;
import  ru.nti.tehsystem.model.TaskCreate;

public interface TaskService {
    Task taskCrate(TaskCreate taskCreate, User user);
    Task taskFin(Task task, User user);
    Task taskFinCrate(Task task, User user, boolean fin);
    Task taskDelete(Task task, User user);
}
