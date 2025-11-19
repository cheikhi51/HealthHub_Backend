package com.healthhub.healthhub.service;

import com.healthhub.healthhub.model.Medecin;
import com.healthhub.healthhub.model.Patient;
import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.dto.StatistiquesMedecinDTO;

import java.util.List;

public interface MedecinService {
    Medecin ajouterMedecin(Medecin medecin);
    Medecin getMedecinById(Long id);
    Medecin modifierProfil(Long id, Medecin medecin);
    String supprimerMedecin(Long id);
    List<Medecin> getAllMedecins();
    List<RendezVous> gererRendezVous(Long medecinId);
    String validerRendezVous(Long medecinId, Long rdvId);
    String refuserRendezVous(Long medecinId, Long rdvId);
    String completerRendezVous(Long medecinId, Long rdvId);
    List<Patient> getMesPatients(Long medecinId);
    StatistiquesMedecinDTO consulterStatistiques(Long medecinId);
    List<RendezVous> getRendezVousEnAttente(Long medecinId);
    List<RendezVous> getRendezVousConfirmes(Long medecinId);
}