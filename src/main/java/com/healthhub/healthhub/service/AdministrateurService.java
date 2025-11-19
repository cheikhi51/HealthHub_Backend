package com.healthhub.healthhub.service;

import com.healthhub.healthhub.dto.StatistiquesGlobalesDTO;
import com.healthhub.healthhub.model.*;

import java.util.List;

public interface AdministrateurService {
    Administrateur ajouterAdmin(Administrateur admin);

    Administrateur getAdminById(Long id);

    String supprimerAdmin(Long id);

    List<Administrateur> getAllAdmins();

    List<Utilisateur> gererUtilisateurs();

    Utilisateur getUtilisateurById(Long id);

    Medecin ajouterMedecin(Medecin medecin);

    Patient ajouterPatient(Patient patient);

    String supprimerUtilisateur(Long id);

    String activerUtilisateur(Long id);

    String desactiverUtilisateur(Long id);

    List<Medecin> getAllMedecins();

    List<Medecin> getMedecinsBySpecialite(String specialite);

    List<Patient> getAllPatients();

    List<RendezVous> getAllRendezVous();

    String supprimerRendezVous(Long id);

    StatistiquesGlobalesDTO consulterStatistiquesGlobales();

    StatistiquesGlobalesDTO getStatistiquesRendezVousParStatut();
}
