package com.healthhub.healthhub.service.impl;

import com.healthhub.healthhub.dto.RendezVousCreatedDTO;
import com.healthhub.healthhub.dto.ReserverParSpecialiteDTO;
import com.healthhub.healthhub.exception.ResourceNotFoundException;
import com.healthhub.healthhub.exception.UnauthorizedException;
import com.healthhub.healthhub.model.*;
import com.healthhub.healthhub.repository.*;
import com.healthhub.healthhub.service.HistoriqueService;
import com.healthhub.healthhub.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private HistoriqueService historiqueService;

    public PatientServiceImpl(PatientRepository patientRepository, RendezVousRepository rendezVousRepository, NotificationRepository notificationRepository, MedecinRepository medecinRepository, UtilisateurRepository utilisateurRepository) {
        this.patientRepository = patientRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.notificationRepository = notificationRepository;
        this.medecinRepository = medecinRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    private void tracerActionSafe(Long utilisateurId, String actionType, String details) {
        try {
            historiqueService.enregistrerAction(utilisateurId, actionType, details);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur traçage historique: " + e.getMessage());
        }
    }

    private void tracerActionSafe(Utilisateur utilisateur, String actionType, String details) {
        try {
            historiqueService.enregistrerAction(utilisateur, actionType, details);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur traçage historique: " + e.getMessage());
        }
    }

    @Override
    public Patient inscrire(Patient patient) {
        patient.setRole(Role.PATIENT);
        Patient savedPatient = patientRepository.save(patient);
        try {
            historiqueService.enregistrerAction(
                    savedPatient,
                    "CREATION_COMPTE",
                    "Nouveau compte patient créé"
            );
        } catch (Exception e) {
            System.err.println("Erreur traçage: " + e.getMessage());
        }
        return savedPatient;
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient avec l'ID " + id + " non trouvé"));
    }

    @Override
    @Transactional
    public Patient modifierProfil(Long id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient avec l'ID " + id + " non trouvé"));

        StringBuilder modifications = new StringBuilder();

        if (patient.getNom() != null) {
            existingPatient.setNom(patient.getNom());
            modifications.append("nom, ");
        }
        if (patient.getPrenom() != null) {
            existingPatient.setPrenom(patient.getPrenom());
            modifications.append("prénom, ");
        }
        if (patient.getEmail() != null) {
            existingPatient.setEmail(patient.getEmail());
            modifications.append("email, ");
        }
        if (patient.getDateNaissance() != null) {
            existingPatient.setDateNaissance(patient.getDateNaissance());
            modifications.append("date de naissance, ");
        }
        if (patient.getAdresse() != null) {
            existingPatient.setAdresse(patient.getAdresse());
            modifications.append("adresse, ");
        }
        if (patient.getTelephone() != null) {
            existingPatient.setTelephone(patient.getTelephone());
            modifications.append("téléphone, ");
        }

        Patient savedPatient = patientRepository.save(existingPatient);

        if (modifications.length() > 0) {
            String details = "Profil modifié : " + modifications.substring(0, modifications.length() - 2);
            tracerActionSafe(id, "MODIFICATION_PROFIL", details);
        }

        return savedPatient;
    }

    @Override
    public String supprimerCompte(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient avec l'ID " + id + " non trouvé");
        }

        // 📝 Tracer AVANT la suppression
        try {
            historiqueService.enregistrerAction(
                    id,
                    "SUPPRESSION_COMPTE",
                    "Compte patient supprimé définitivement"
            );
        } catch (Exception e) {
            // Log mais ne bloque pas
            System.err.println("Erreur traçage: " + e.getMessage());
        }
        patientRepository.deleteById(id);
        return "Compte patient supprimé avec succès";
    }

    @Override
    public List<Medecin> consulterListeMedecins() {
        return medecinRepository.findAll();
    }

    @Override
    public RendezVous reserverRendezVous(Long patientId, RendezVous rendezVous) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient avec l'ID " + patientId + " non trouvé"));

        rendezVous.setPatient(patient);
        rendezVous.setStatut(StatutRdv.EN_ATTENTE);

        return rendezVousRepository.save(rendezVous);
    }

    @Override
    @Transactional
    public RendezVousCreatedDTO reserverParSpecialite(ReserverParSpecialiteDTO dto) {
        // 1. Récupérer le patient connecté
        Patient patient = getPatientConnecte();

        // 2. Valider les données
        validerDonneesReservation(dto);

        // 3. Valider les dates
        validerDates(dto.getDateDebut(), dto.getDateFin());

        // 4. Trouver un médecin disponible pour cette spécialité et ce créneau
        Medecin medecin = trouverMedecinDisponible(dto.getSpecialite(), dto.getDateDebut(), dto.getDateFin());

        // 5. Créer le rendez-vous
        RendezVous rendezVous = new RendezVous();
        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);
        rendezVous.setDateDebut(dto.getDateDebut());
        rendezVous.setDateFin(dto.getDateFin());
        rendezVous.setStatut(StatutRdv.EN_ATTENTE);
        rendezVous.setMotif(dto.getMotif());

        RendezVous saved = rendezVousRepository.save(rendezVous);

        // 📝 Enregistrer dans l'historique
        String details = String.format(
                "Rendez-vous créé avec Dr. %s %s (Spécialité: %s) le %s",
                medecin.getPrenom(),
                medecin.getNom(),
                dto.getSpecialite(),
                dto.getDateDebut()
        );

        historiqueService.enregistrerAction(patient, "CREATION_RDV", details);

        // 6. Retourner le DTO avec les infos du médecin assigné
        return convertToRendezVousCreatedDTO(saved);
    }

    @Override
    public String annulerRendezVous(Long patientId, Long rdvId) {
        RendezVous rdv = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous avec l'ID " + rdvId + " non trouvé"));

        if (!rdv.getPatient().getId().equals(patientId)) {
            throw new UnauthorizedException("Ce rendez-vous ne vous appartient pas");
        }

        rdv.setStatut(StatutRdv.ANNULE);
        rendezVousRepository.save(rdv);
        // 📝 Enregistrer dans l'historique
        String details = String.format(
                "Rendez-vous annulé (Dr. %s %s, prévu le %s)",
                rdv.getMedecin().getPrenom(),
                rdv.getMedecin().getNom(),
                rdv.getDateDebut()
        );

        historiqueService.enregistrerAction(patientId, "ANNULATION_RDV", details);

        return "Rendez-vous annulé avec succès";
    }

    @Override
    public List<RendezVous> consulterHistoriqueRendezVous(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient avec l'ID " + patientId + " non trouvé");
        }
        return rendezVousRepository.findByPatientId(patientId);
    }

    @Override
    public List<Notification> recevoirNotifications(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient avec l'ID " + patientId + " non trouvé");
        }

        return notificationRepository.findByUtilisateurIdOrderByDateEnvoiDesc(patientId);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<String> getSpecialitesDisponibles() {
        return  medecinRepository.findByDisponibilite(true).stream()
                .map(Medecin::getSpecialite)
                .filter(s -> s != null && !s.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Patient getPatientConnecte() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        if (!(utilisateur instanceof Patient)) {
            throw new UnauthorizedException("Seuls les patients peuvent effectuer cette action");
        }

        return (Patient) utilisateur;
    }

    // ========== MÉTHODES PRIVÉES DE VALIDATION ==========

    private void validerDonneesReservation(ReserverParSpecialiteDTO dto) {
        if (dto.getSpecialite() == null || dto.getSpecialite().trim().isEmpty()) {
            throw new IllegalArgumentException("La spécialité est obligatoire");
        }

        if (dto.getDateDebut() == null) {
            throw new IllegalArgumentException("La date de début est obligatoire");
        }

        if (dto.getDateFin() == null) {
            throw new IllegalArgumentException("La date de fin est obligatoire");
        }

        if (dto.getMotif() == null || dto.getMotif().trim().isEmpty()) {
            throw new IllegalArgumentException("Le motif de consultation est obligatoire");
        }
    }

    private void validerDates(Instant dateDebut, Instant dateFin) {
        Instant maintenant = Instant.now();

        // 1. Vérifier que la date de début n'est pas dans le passé
        if (dateDebut.isBefore(maintenant)) {
            throw new IllegalArgumentException("La date de début ne peut pas être dans le passé");
        }

        // 2. Vérifier que la date de fin est après la date de début
        if (dateFin.isBefore(dateDebut) || dateFin.equals(dateDebut)) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }

        // 3. Vérifier la durée du rendez-vous (entre 15 min et 2h)
        long dureeMinutes = ChronoUnit.MINUTES.between(dateDebut, dateFin);
        if (dureeMinutes < 15) {
            throw new IllegalArgumentException("La durée du rendez-vous doit être d'au moins 15 minutes");
        }
        if (dureeMinutes > 120) {
            throw new IllegalArgumentException("La durée du rendez-vous ne peut pas dépasser 2 heures");
        }

        // 4. Vérifier que le rendez-vous n'est pas trop loin dans le futur (max 3 mois)
        Instant dateLimite = maintenant.plus(90, ChronoUnit.DAYS);
        if (dateDebut.isAfter(dateLimite)) {
            throw new IllegalArgumentException("Les rendez-vous ne peuvent être pris que 3 mois à l'avance maximum");
        }
    }

    private Medecin trouverMedecinDisponible(String specialite, Instant dateDebut, Instant dateFin) {
        // 1. Récupérer tous les médecins disponibles de cette spécialité
        List<Medecin> medecinsDisponibles = medecinRepository
                .findBySpecialiteAndDisponibilite(specialite, true);

        if (medecinsDisponibles.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Aucun médecin disponible pour la spécialité: " + specialite
            );
        }

        // 2. Chercher le premier médecin sans conflit sur ce créneau
        for (Medecin medecin : medecinsDisponibles) {
            if (medecinEstLibreSurCreneau(medecin, dateDebut, dateFin)) {
                return medecin;
            }
        }

        // 3. Si aucun médecin n'est libre
        throw new IllegalStateException(
                "Aucun médecin de spécialité « " + specialite + " » n'est disponible pour le créneau demandé. " +
                        "Veuillez choisir un autre horaire."
        );
    }

    private boolean medecinEstLibreSurCreneau(Medecin medecin, Instant dateDebut, Instant dateFin) {
        // Récupérer tous les rendez-vous confirmés ou en attente du médecin
        List<RendezVous> rendezVousExistants = rendezVousRepository
                .findByMedecinAndStatutIn(
                        medecin,
                        List.of(StatutRdv.EN_ATTENTE, StatutRdv.CONFIRME)
                );

        // Vérifier qu'il n'y a pas de chevauchement
        for (RendezVous rdv : rendezVousExistants) {
            if (rendezVousSeChevauchent(dateDebut, dateFin, rdv.getDateDebut(), rdv.getDateFin())) {
                return false; // Médecin occupé
            }
        }

        return true; // Médecin libre
    }

    private boolean rendezVousSeChevauchent(Instant debut1, Instant fin1, Instant debut2, Instant fin2) {
        return debut1.isBefore(fin2) && fin1.isAfter(debut2);
    }

    private RendezVousCreatedDTO convertToRendezVousCreatedDTO(RendezVous rdv) {
        RendezVousCreatedDTO dto = new RendezVousCreatedDTO();
        dto.setId(rdv.getId());
        dto.setDateDebut(rdv.getDateDebut());
        dto.setDateFin(rdv.getDateFin());
        dto.setMotif(rdv.getMotif());
        dto.setStatut(rdv.getStatut());

        RendezVousCreatedDTO.MedecinAssigneDTO medecinDTO =
                new RendezVousCreatedDTO.MedecinAssigneDTO(
                        rdv.getMedecin().getId(),
                        rdv.getMedecin().getNom(),
                        rdv.getMedecin().getPrenom(),
                        rdv.getMedecin().getSpecialite(),
                        rdv.getMedecin().getTelephone()
                );
        dto.setMedecinAssigne(medecinDTO);

        return dto;
    }

    @Transactional
    @Override
    public Notification marquerNotificationCommeLue(Long patientId, Long notificationId) {
        // 1. Vérifier que le patient existe
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient avec l'ID " + patientId + " non trouvé");
        }

        // 2. Récupérer la notification
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Notification avec l'ID " + notificationId + " non trouvée"
                ));

        // 3. Vérifier que la notification appartient bien au patient
        if (!notification.getUtilisateurId().equals(patientId)) {
            throw new UnauthorizedException("Cette notification ne vous appartient pas");
        }

        // 4. Marquer comme lue si ce n'est pas déjà fait
        if (!notification.getLu()) {
            notification.setLu(true);
            notification.setDateLecture(LocalDateTime.now());
            return notificationRepository.save(notification);
        }

        return notification;
    }
}