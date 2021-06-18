package ru.nti.tehsystem.domain;


public final class Views {

    public interface Id {}

    //User
    public interface UserBasic extends Id ,NotificationsBasic{}

    public interface UserAll extends UserBasic, ImgAll {}

    //Img
    public interface ImgBasic extends Id {}

    public interface ImgAll extends ImgBasic {}

    //Task
    public interface TaskBasic extends UserBasic,TaskUpdate,MassagesBasic{}

    public interface TaskAll extends TaskBasic,  ImgAll, MassagesBasic {}
    public interface TaskUpdate extends  UserBasic, ImgAll, MassagesBasic {}

    //Massages
    public interface MassagesBasic extends Id, ImgBasic {}

    //Notifications
    public interface NotificationsBasic extends Id ,MassagesBasic {}
}

