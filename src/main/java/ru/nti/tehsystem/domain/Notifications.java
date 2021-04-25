package ru.nti.tehsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import ru.nti.tehsystem.domain.enums.Level;
import ru.nti.tehsystem.domain.enums.NotificationType;
import ru.nti.tehsystem.repo.MassagesRepo;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView(Views.Id.class)
    private String id;
    @JsonView(Views.NotificationsBasic.class)
    private String text;
    private boolean close = false;
    @JsonView(Views.NotificationsBasic.class)
    private Level level;

    @JsonView(Views.NotificationsBasic.class)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Task taskId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.NotificationsBasic.class)
    private LocalDateTime data;
    @JsonView(Views.NotificationsBasic.class)
    private NotificationType notificationType;
    @JsonView(Views.NotificationsBasic.class)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)

    private Massages massages;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Task getTaskId() {
        return taskId;
    }

    public void setTaskId(Task taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Massages getMassages() {
        return massages;
    }

    public void setMassages(Massages massages) {
        this.massages = massages;
    }
}
