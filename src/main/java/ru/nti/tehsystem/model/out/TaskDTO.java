package ru.nti.tehsystem.model.out;

import ru.nti.tehsystem.domain.Massages;
import ru.nti.tehsystem.domain.User;

import java.util.Set;

public class TaskDTO {

    private Set<Massages> massages;
    private boolean done = false;

    private String name;
    private User creator;
    private boolean deletes = false;
    private boolean adopted = false;
    private boolean rejected = false;

    public Set<Massages> getMassages() {
        return massages;
    }

    public void setMassages(Set<Massages> massages) {
        this.massages = massages;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean isDeletes() {
        return deletes;
    }

    public void setDeletes(boolean deletes) {
        this.deletes = deletes;
    }

    public boolean isAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
}
