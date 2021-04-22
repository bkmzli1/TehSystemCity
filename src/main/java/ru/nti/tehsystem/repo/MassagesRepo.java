package ru.nti.tehsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import  ru.nti.tehsystem.domain.Massages;

public interface MassagesRepo extends JpaRepository<Massages, String> {

}
