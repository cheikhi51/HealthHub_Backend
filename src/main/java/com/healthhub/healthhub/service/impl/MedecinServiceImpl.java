package com.healthhub.healthhub.service.impl;

import com.healthhub.healthhub.dto.StatistiquesMedecinDTO;
import com.healthhub.healthhub.exception.ResourceNotFoundException;
import com.healthhub.healthhub.exception.UnauthorizedException;
import com.healthhub.healthhub.model.*;
import com.healthhub.healthhub.repository.*;
import com.healthhub.healthhub.service.HistoriqueService;
import com.healthhub.healthhub.service.MedecinService;
import com.healthhub.healthhub.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedecinServiceImpl implements MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private HistoriqueService historiqueService;

    // ========== MÉTHODES HELPER POUR TRAÇAGE SÉCURISÉ ==========

    private void tracerActionSafe(Long utilisateurId, String actionType, String details) {
        try {
            historiqueService.enregistrerAction(utilisateurId, actionType, details);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur traçage historique médecin: " + e.getMessage());
        }
    }

    private void tracerActionSafe(Utilisateur utilisateur, String actionType, String details) {
        try {
            historiqueService.enregistrerAction(utilisateur, actionType, details);
        } catch (Exception e) {
            System.err.println("⚠️ Erreur traçage historique médecin: " + e.getMessage());
        }
    }

    // ========== MÉTHODES CRUD AVEC TRAÇAGE ==========

    @Override
    @Transactional
    public Medecin ajouterMedecin(Medecin medecin) {
        Medecin savedMedecin = medecinRepository.save(medecin);

        tracerActionSafe(
                savedMedecin,
                "CREATION_COMPTE_MEDECIN",
                String.format("Compte médecin créé - Dr. %s %s (Spécialité: %s)",
                        savedMedecin.getPrenom(),
                        savedMedecin.getNom(),
                        savedMedecin.getSpecialite())
        );

        return savedMedecin;
    }

    @Override
    public Medecin getMedecinById(Long id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin avec l'ID " + id + " non trouvé"));
    }

    @Override
    @Transactional
    public Medecin modifierProfil(Long id, Medecin medecin) {
        Medecin existingMedecin = medecinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médecin avec l'ID " + id + " non trouvé"));

        StringBuilder modifications = new StringBuilder();

        // Mise à jour des champs de Utilisateur
        if (medecin.getNom() != null && !medecin.getNom().equals(existingMedecin.getNom())) {
            existingMedecin.setNom(medecin.getNom());
            modifications.append("nom, ");
        }
        if (medecin.getPrenom() != null && !medecin.getPrenom().equals(existingMedecin.getPrenom())) {
            existingMedecin.setPrenom(medecin.getPrenom());
            modifications.append("prénom, ");
        }
        if (medecin.getEmail() != null && !medecin.getEmail().equals(existingMedecin.getEmail())) {
            existingMedecin.setEmail(medecin.getEmail());
            modifications.append("email, ");
        }
        if (medecin.getMotDePasse() != null) {
            existingMedecin.setMotDePasse(medecin.getMotDePasse());
            modifications.append("mot de passe, ");
        }

        // Mise à jour des champs spécifiques à Medecin
        if (medecin.getTelephone() != null && !medecin.getTelephone().equals(existingMedecin.getTelephone())) {
            existingMedecin.setTelephone(medecin.getTelephone());
            modifications.append("téléphone, ");
        }
        if (medecin.getSpecialite() != null && !medecin.getSpecialite().equals(existingMedecin.getSpecialite())) {
            existingMedecin.setSpecialite(medecin.getSpecialite());
            modifications.append("spécialité, ");
        }
        if (medecin.getDisponibilite() != null && !medecin.getDisponibilite().equals(existingMedecin.getDisponibilite())) {
            existingMedecin.setDisponibilite(medecin.getDisponibilite());
            modifications.append("disponibilité, ");
        }

        Medecin savedMedecin = medecinRepository.save(existingMedecin);

        // Tracer si des modifications ont été faites
        if (modifications.length() > 0) {
            String details = "Profil médecin modifié : " + modifications.substring(0, modifications.length() - 2);
            tracerActionSafe(id, "MODIFICATION_PROFIL_MEDECIN", details);
        }

        return savedMedecin;
    }

    @Override
    @Transactional
    public String supprimerMedecin(Long id) {
        if (!medecinRepository.existsById(id)) {
            throw new ResourceNotFoundException("Médecin avec l'ID " + id + " non trouvé");
        }

        tracerActionSafe(id, "SUPPRESSION_COMPTE_MEDECIN", "Compte médecin supprimé définitivement");

        medecinRepository.deleteById(id);
        return "Médecin supprimé avec succès";
    }

    @Override
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    // ========== GESTION DES RENDEZ-VOUS AVEC TRAÇAGE ==========

    @Override
    public List<RendezVous> gererRendezVous(Long medecinId) {
        if (!medecinRepository.existsById(medecinId)) {
            throw new ResourceNotFoundException("Médecin avec l'ID " + medecinId + " non trouvé");
        }
        return rendezVousRepository.findByMedecinId(medecinId);
    }

    @Override
    @Transactional
    public String validerRendezVous(Long medecinId, Long rdvId) {
        RendezVous rdv = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous avec l'ID " + rdvId + " non trouvé"));

        if (!rdv.getMedecin().getId().equals(medecinId)) {
            throw new UnauthorizedException("Ce rendez-vous ne vous appartient pas");
        }

        rdv.setStatut(StatutRdv.CONFIRME);
        rendezVousRepository.save(rdv);

        // Envoyer notification au patient
        notificationService.envoyerNotification(
                rdv.getPatient(),
                "Votre rendez-vous du " + rdv.getDateDebut() + " a été confirmé par le Dr. " +
                        rdv.getMedecin().getNom()
        );

        // 📝 Tracer l'action
        String details = String.format(
                "Rendez-vous confirmé (ID: %d, Patient: %s %s, Date: %s)",
                rdvId,
                rdv.getPatient().getPrenom(),
                rdv.getPatient().getNom(),
                rdv.getDateDebut()
        );
        tracerActionSafe(medecinId, "CONFIRMATION_RDV", details);

        return "Rendez-vous validé avec succès";
    }

    @Override
    @Transactional
    public String refuserRendezVous(Long medecinId, Long rdvId) {
        RendezVous rdv = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous avec l'ID " + rdvId + " non trouvé"));

        if (!rdv.getMedecin().getId().equals(medecinId)) {
            throw new UnauthorizedException("Ce rendez-vous ne vous appartient pas");
        }

        rdv.setStatut(StatutRdv.REFUSE);
        rendezVousRepository.save(rdv);

        // Envoyer notification au patient
        notificationService.envoyerNotification(
                rdv.getPatient(),
                "Votre rendez-vous du " + rdv.getDateDebut() + " a été refusé. Veuillez choisir un autre créneau."
        );

        // 📝 Tracer l'action
        String details = String.format(
                "Rendez-vous refusé (ID: %d, Patient: %s %s, Date: %s)",
                rdvId,
                rdv.getPatient().getPrenom(),
                rdv.getPatient().getNom(),
                rdv.getDateDebut()
        );
        tracerActionSafe(medecinId, "REFUS_RDV", details);

        return "Rendez-vous refusé";
    }

    @Override
    @Transactional
    public String completerRendezVous(Long medecinId, Long rdvId) {
        RendezVous rdv = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new ResourceNotFoundException("Rendez-vous avec l'ID " + rdvId + " non trouvé"));

        if (!rdv.getMedecin().getId().equals(medecinId)) {
            throw new UnauthorizedException("Ce rendez-vous ne vous appartient pas");
        }

        rdv.setStatut(StatutRdv.TERMINE);
        rendezVousRepository.save(rdv);

        // 📝 Tracer l'action
        String details = String.format(
                "Rendez-vous terminé (ID: %d, Patient: %s %s, Date: %s)",
                rdvId,
                rdv.getPatient().getPrenom(),
                rdv.getPatient().getNom(),
                rdv.getDateDebut()
        );
        tracerActionSafe(medecinId, "COMPLETION_RDV", details);

        return "Rendez-vous marqué comme terminé";
    }

    @Override
    public List<Patient> getMesPatients(Long medecinId) {
        if (!medecinRepository.existsById(medecinId)) {
            throw new ResourceNotFoundException("Médecin avec l'ID " + medecinId + " non trouvé");
        }

        List<RendezVous> rendezVous = rendezVousRepository.findByMedecinId(medecinId);
        return rendezVous.stream()
                .map(RendezVous::getPatient)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public StatistiquesMedecinDTO consulterStatistiques(Long medecinId) {
        if (!medecinRepository.existsById(medecinId)) {
            throw new ResourceNotFoundException("Médecin avec l'ID " + medecinId + " non trouvé");
        }

        StatistiquesMedecinDTO stats = new StatistiquesMedecinDTO();

        List<RendezVous> tousRendezVous = rendezVousRepository.findByMedecinId(medecinId);
        stats.setNombreTotalRendezVous((long) tousRendezVous.size());

        long enAttente = tousRendezVous.stream()
                .filter(rdv -> rdv.getStatut() == StatutRdv.EN_ATTENTE)
                .count();
        stats.setNombreRendezVousEnAttente(enAttente);

        long confirmes = tousRendezVous.stream()
                .filter(rdv -> rdv.getStatut() == StatutRdv.CONFIRME)
                .count();
        stats.setNombreRendezVousConfirmes(confirmes);

        long termines = tousRendezVous.stream()
                .filter(rdv -> rdv.getStatut() == StatutRdv.TERMINE)
                .count();
        stats.setNombreRendezVousTermines(termines);

        long annules = tousRendezVous.stream()
                .filter(rdv -> rdv.getStatut() == StatutRdv.ANNULE)
                .count();
        stats.setNombreRendezVousAnnules(annules);

        long refuses = tousRendezVous.stream()
                        .filter(rdv -> rdv.getStatut() == StatutRdv.REFUSE)
                        .count();
        stats.setNombreRendezVousRefuses(refuses);

        stats.setNombrePatientsUniques((long) getMesPatients(medecinId).size());

        // 📝 Tracer la consultation des statistiques (optionnel)
        tracerActionSafe(medecinId, "CONSULTATION_STATS", "Consultation des statistiques personnelles");

        return stats;
    }

    @Override
    public List<RendezVous> getRendezVousEnAttente(Long medecinId) {
        if (!medecinRepository.existsById(medecinId)) {
            throw new ResourceNotFoundException("Médecin avec l'ID " + medecinId + " non trouvé");
        }
        return rendezVousRepository.findByMedecinIdAndStatut(medecinId, StatutRdv.EN_ATTENTE);
    }

    @Override
    public List<RendezVous> getRendezVousConfirmes(Long medecinId) {
        if (!medecinRepository.existsById(medecinId)) {
            throw new ResourceNotFoundException("Médecin avec l'ID " + medecinId + " non trouvé");
        }
        return rendezVousRepository.findByMedecinIdAndStatut(medecinId, StatutRdv.CONFIRME);
    }
}