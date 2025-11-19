package com.healthhub.healthhub.service;

import com.healthhub.healthhub.dto.RendezVousCreatedDTO;
import com.healthhub.healthhub.dto.ReserverParSpecialiteDTO;
import com.healthhub.healthhub.model.*;

import java.util.List;

public interface PatientService {
    Patient inscrire(Patient patient);
    Patient getPatientById(Long id);
    Patient modifierProfil(Long id, Patient patient);
    String supprimerCompte(Long id);

    List<Medecin> consulterListeMedecins();

    RendezVous reserverRendezVous(Long patientId, RendezVous rendezVous);
    RendezVousCreatedDTO reserverParSpecialite(ReserverParSpecialiteDTO dto);

    String annulerRendezVous(Long patientId, Long rdvId);

    List<RendezVous> consulterHistoriqueRendezVous(Long patientId);

    List<Notification> recevoirNotifications(Long patientId);

    List<Patient> getAllPatients();

    List<String> getSpecialitesDisponibles();

    Patient getPatientConnecte();

    Notification marquerNotificationCommeLue(Long patientId, Long notificationId);
}