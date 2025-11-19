package com.healthhub.healthhub.repository;

import com.healthhub.healthhub.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {


    List<Medecin> findByDisponibilite(Boolean disponibilite);

    List<Medecin> findBySpecialite(String specialite);

    List<Medecin> findBySpecialiteAndDisponibilite(String specialite, Boolean disponibilite);


}