package ru.nti.tehsystem.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nti.tehsystem.domain.*;
import ru.nti.tehsystem.domain.enums.NotificationType;
import ru.nti.tehsystem.model.in.MassageModel;
import ru.nti.tehsystem.repo.*;
import ru.nti.tehsystem.services.impl.MassageService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class MassageServiceImpl implements MassageService {
    private final TaskRepo taskRepo;
    private final MassagesRepo massagesRepo;
    private final ImgRepo imgRepo;
    private final NotificationsRepo notificationsRepo;
    private final UserRepo userRepo;
    private final MailService mailService;
    private final EmailConfirmedRepo emailConfirmedRepo;
    @Value("${urlSever}")
    private String urlSever;
    @Autowired
    public MassageServiceImpl(TaskRepo taskRepo, MassagesRepo massagesRepo, ImgRepo imgRepo, NotificationsRepo notificationsRepo, UserRepo userRepo, MailService mailService, EmailConfirmedRepo emailConfirmedRepo) {
        this.taskRepo = taskRepo;
        this.massagesRepo = massagesRepo;
        this.imgRepo = imgRepo;
        this.notificationsRepo = notificationsRepo;
        this.userRepo = userRepo;
        this.mailService = mailService;
        this.emailConfirmedRepo = emailConfirmedRepo;
    }

    @Override
    public Task massageCrate(MassageModel massageModel, User user) {
        Task task = taskRepo.findById(massageModel.getId()).get();
        Massages massage = new Massages();
        massage.setUser(user);
        massage.setDateTime(LocalDateTime.now());
        massage.setImgs(new HashSet<>());
        try {
            Arrays.stream(massageModel.getImgs()).forEach(imgS -> {
                massage.getImgs().add(imgRepo.findById(imgS).get());
            });
        } catch (NullPointerException nullPointerException) {

        }
        massage.setText(massageModel.getText());
        massagesRepo.save(massage);
        Set<Massages> massagesTask = task.getMassages();
        massagesTask.add(massage);
        task.setMassages(massagesTask);
        for (User userNot : task.getExecutor()) {
            if (user.getId().equals(userNot.getId())) {
                System.out.println(user.getId());
                continue;
            }
            Notifications notifications = new Notifications();
//            notifications.setMassages(massage);
            notifications.setTaskId(task);
            notifications.setLevel(task.getLevel());
            String str = massage.getText();
            int start = 1;
            int end = 11;
            if (end >= massage.getText().length())
                end = massage.getText().length();
            char[] dst = new char[end - start];
            str.getChars(start, end, dst, 0);

            notifications.setText("Вам сообщение:" + String.copyValueOf(dst) + "...\"");
            notifications.setData(LocalDateTime.now());
            notifications.setNotificationType(NotificationType.MASSAGES);
            try {
                Set<Notifications> notifications1 = userNot.getNotifications();
                notifications1.add(notifications);
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
        taskRepo.save(task);

            EmailConfirmed emailConfirmed = new EmailConfirmed();
            emailConfirmed.setUser(user);
            emailConfirmedRepo.save(emailConfirmed);
            if (emailConfirmed.getCode() == null) {
                emailConfirmed.setCode(UUID.randomUUID().toString());
                emailConfirmedRepo.save(emailConfirmed);
            }
            mailService.send(user.getEmail(), "Вам сообщение", massage.getText()+"\n" +"id задачи"+urlSever +"/order/" +task.getId());

        return task;
    }
}
