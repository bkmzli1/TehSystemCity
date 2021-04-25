package ru.nti.tehsystem.services.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nti.tehsystem.domain.Img;
import ru.nti.tehsystem.domain.Notifications;
import ru.nti.tehsystem.domain.Task;
import ru.nti.tehsystem.domain.User;
import ru.nti.tehsystem.domain.enums.Level;
import ru.nti.tehsystem.domain.enums.NotificationType;
import ru.nti.tehsystem.model.TaskCreate;
import ru.nti.tehsystem.repo.ImgRepo;
import ru.nti.tehsystem.repo.NotificationsRepo;
import ru.nti.tehsystem.repo.TaskRepo;
import ru.nti.tehsystem.repo.UserRepo;
import ru.nti.tehsystem.services.impl.TaskService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final ImgRepo imgRepo;
    private final UserRepo userRepo;
    private final NotificationsRepo notificationsRepo;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo, ImgRepo imgRepo, UserRepo userRepo,
                           NotificationsRepo notificationsRepo) {
        this.taskRepo = taskRepo;
        this.imgRepo = imgRepo;
        this.userRepo = userRepo;
        this.notificationsRepo = notificationsRepo;
    }

    @Override
    public Task taskCrate(TaskCreate taskCreate, User user) {
        Task task = new Task();
        task.setExecutor(new HashSet<>());
        taskCreate.getExecutor().forEach(s -> {
            User userById = userRepo.findUserById(s);
            task.getExecutor().add(userById);
        });
        task.setLevel(Level.valueOf(taskCreate.getLevel()));
        task.setName(taskCreate.getName());
        task.setText(taskCreate.getText());
        task.setImgs(new HashSet<>());
        task.setCreator(user);
        task.setMassages(new HashSet<>());
        task.setCrate(LocalDateTime.now());
        try {
            taskCreate.getImgs().forEach(imgStr -> {
                Optional<Img> byId = imgRepo.findById(imgStr);
                Img img = byId.get();
                task.getImgs().add(img);
            });
        } catch (NullPointerException nullPointerException) {
        }
        task.setDeletes(false);
        taskRepo.save(task);
        for (User userNot : task.getExecutor()) {
            if (user.getId().equals(userNot.getId())) {
                System.out.println(user.getId());
                continue;
            }
            Notifications notifications = new Notifications();
            notifications.setTaskId(task);
            notifications.setLevel(task.getLevel());
            notifications.setText("Создано для вас задание.\nНазвание \"" + task.getName() + "\"");
            notifications.setData(LocalDateTime.now());
            notifications.setNotificationType(NotificationType.TASK);
            try {
                Set<Notifications> notifications1 = userNot.getNotifications();
                notifications1 .add(notifications);
                userNot.setNotifications(notifications1);
                notificationsRepo.save(notifications);
                userRepo.save(userNot);
            } catch (Exception e) {
                userNot.setNotifications(new HashSet<Notifications>());
                Set<Notifications> notificationsAdd = userNot.getNotifications();
                notificationsAdd.add(notifications);
                userNot.setNotifications(notificationsAdd);
                notificationsRepo.save(notifications);
                userRepo.save(userNot);
            }


        }
        return task;
    }

    @Override
    public Task taskFin(Task task, User user) {
        task.setExecuted(LocalDateTime.now());
        task.setPerformed(user);
        task.setDone(true);
        taskRepo.save(task);
        return task;

    }

    @Override
    public Task taskFinCrate(Task task, User user, boolean fin) {
        if (fin) {
            task.setDoneCrate(true);

        } else {
            task.setDoneCrate(false);
            task.setPerformed(null);
            task.setDone(false);
        }
        taskRepo.save(task);
        return task;
    }

    @Override
    public Task taskDelete(Task task, User user) {

        task.setDoneCrate(false);
        task.setPerformed(null);
        task.setDone(false);
        task.setDeletes(true);
        taskRepo.save(task);
        return null;
    }
}
