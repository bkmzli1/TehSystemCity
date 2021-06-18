package ru.nti.tehsystem.services.impl;

import  ru.nti.tehsystem.domain.Img;
import ru.nti.tehsystem.model.in.ImgCreateModel;

public interface ImgService {
   Img imgCrate(ImgCreateModel taskCreate);
   Img imgCrate(Img img);
}
