package ru.nti.tehsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import  ru.nti.tehsystem.domain.Img;

public interface ImgRepo extends JpaRepository<Img, String> {


}
