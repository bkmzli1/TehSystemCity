package ru.nti.tehsystem.services.impl;

import  ru.nti.tehsystem.domain.Task;
import  ru.nti.tehsystem.domain.User;
import ru.nti.tehsystem.model.in.MassageModel;

public interface MassageService {
   Task massageCrate(MassageModel massageModel, User principal);

}
