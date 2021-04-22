package ru.nti.tehsystem.services.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nti.tehsystem.domain.Massages;
import ru.nti.tehsystem.domain.Task;
import ru.nti.tehsystem.domain.User;
import ru.nti.tehsystem.model.MassageModel;
import ru.nti.tehsystem.repo.ImgRepo;
import ru.nti.tehsystem.repo.MassagesRepo;
import ru.nti.tehsystem.repo.TaskRepo;
import ru.nti.tehsystem.services.impl.MassageService;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class MassageServiceImpl implements MassageService {
    private final TaskRepo taskRepo;
    private final MassagesRepo massagesRepo;
    private final ImgRepo imgRepo;

    @Autowired
    public MassageServiceImpl(TaskRepo taskRepo, MassagesRepo massagesRepo, ImgRepo imgRepo) {
        this.taskRepo = taskRepo;
        this.massagesRepo = massagesRepo;
        this.imgRepo = imgRepo;
    }

    @Override
    public Task massageCrate(MassageModel massageModel, User user) {
        Task task = taskRepo.findById(massageModel.getId()).get();
        Massages massages = new Massages();
        massages.setUser(user);
        massages.setDateTime(LocalDateTime.now());
        massages.setImgs(new HashSet<>());
        try {
            Arrays.stream(massageModel.getImgs()).forEach(imgS -> {
                massages.getImgs().add(imgRepo.findById(imgS).get());
            });
        } catch (NullPointerException nullPointerException) {

        }
        massages.setText(massageModel.getText());
        task.getMassages().add(massages);
        massagesRepo.save(massages);
        Set<Massages> projects = new TreeSet<>(Comparator.comparing(Massages::getDateTime));
        projects.addAll(task.getMassages()) ;
        task.setMassages(projects);
        taskRepo.save(task);

        return task;
    }
}
