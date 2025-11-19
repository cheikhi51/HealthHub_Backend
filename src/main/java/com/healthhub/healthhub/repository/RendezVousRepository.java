package com.healthhub.healthhub.repository;

import com.healthhub.healthhub.model.Medecin;
import com.healthhub.healthhub.model.Patient;
import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.model.StatutRdv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByMedecinId(Long medecinId);
    List<RendezVous> findByPatient(Patient patient);

    List<RendezVous> findByMedecin(Medecin medecin);

    List<RendezVous> findByStatut(StatutRdv statut);

    List<RendezVous> findByMedecinAndStatutIn(Medecin medecin, List<StatutRdv> statuts);
    long countByStatut(StatutRdv statut);

    List<RendezVous> findByMedecinIdAndStatut(Long medecinId, StatutRdv statutRdv);
}