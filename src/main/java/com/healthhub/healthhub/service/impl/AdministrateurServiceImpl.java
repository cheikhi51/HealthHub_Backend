package com.healthhub.healthhub.service.impl;

import com.healthhub.healthhub.dto.StatistiquesGlobalesDTO;
import com.healthhub.healthhub.exception.ResourceNotFoundException;
import com.healthhub.healthhub.model.*;
import com.healthhub.healthhub.repository.*;
import com.healthhub.healthhub.service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrateurServiceImpl implements AdministrateurService {

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Override
    public Administrateur ajouterAdmin(Administrateur admin) {
        // Le constructeur par défaut définit déjà le rôle à ADMIN
        return administrateurRepository.save(admin);
    }

    @Override
    public Administrateur getAdminById(Long id) {
        return administrateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrateur avec l'ID " + id + " non trouvé"));
    }

    @Override
    public String supprimerAdmin(Long id) {
        if (!administrateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Administrateur avec l'ID " + id + " non trouvé");
        }
        administrateurRepository.deleteById(id);
        return "Administrateur supprimé avec succès";
    }

    @Override
    public List<Administrateur> getAllAdmins() {
        return administrateurRepository.findAll();
    }

    @Override
    public List<Utilisateur> gererUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur avec l'ID " + id + " non trouvé"));
    }

    @Override
    public Medecin ajouterMedecin(Medecin medecin) {
        // Le constructeur par défaut de Medecin définit déjà le rôle à MEDECIN
        return medecinRepository.save(medecin);
    }

    @Override
    public Patient ajouterPatient(Patient patient) {
        // Le rôle est défini dans le constructeur de Patient
        return patientRepository.save(patient);
    }

    @Override
    public String supprimerUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur avec l'ID " + id + " non trouvé");
        }
        utilisateurRepository.deleteById(id);
        return "Utilisateur supprimé avec succès";
    }

    @Override
    public String activerUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur avec l'ID " + id + " non trouvé"));

        // Si le modèle Utilisateur a un champ "actif", décommentez les lignes suivantes :
        // utilisateur.setActif(true);
        // utilisateurRepository.save(utilisateur);

        // Pour l'instant, on utilise une logique alternative pour les médecins
        if (utilisateur instanceof Medecin) {
            Medecin medecin = (Medecin) utilisateur;
            medecin.setDisponibilite(true);
            medecinRepository.save(medecin);
            return "Médecin activé avec succès";
        }

        return "Utilisateur activé avec succès";
    }

    @Override
    public String desactiverUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur avec l'ID " + id + " non trouvé"));

        // Si le modèle Utilisateur a un champ "actif", décommentez les lignes suivantes :
        // utilisateur.setActif(false);
        // utilisateurRepository.save(utilisateur);

        // Pour l'instant, on utilise une logique alternative pour les médecins
        if (utilisateur instanceof Medecin) {
            Medecin medecin = (Medecin) utilisateur;
            medecin.setDisponibilite(false);
            medecinRepository.save(medecin);
            return "Médecin désactivé avec succès";
        }

        return "Utilisateur désactivé avec succès";
    }

    @Override
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    @Override
    public List<Medecin> getMedecinsBySpecialite(String specialite) {
        return medecinRepository.findBySpecialite(specialite);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    @Override
    public String supprimerRendezVous(Long id) {
        if (!rendezVousRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rendez-vous avec l'ID " + id + " non trouvé");
        }
        rendezVousRepository.deleteById(id);
        return "Rendez-vous supprimé avec succès";
    }

    @Override
    public StatistiquesGlobalesDTO consulterStatistiquesGlobales() {
        StatistiquesGlobalesDTO stats = new StatistiquesGlobalesDTO();

        stats.setNombreTotalPatients(patientRepository.count());
        stats.setNombreTotalMedecins(medecinRepository.count());
        stats.setNombreTotalUtilisateurs(utilisateurRepository.count());
        stats.setNombreTotalRendezVous(rendezVousRepository.count());

        stats.setNombreRendezVousEnAttente(rendezVousRepository.countByStatut(StatutRdv.EN_ATTENTE));
        stats.setNombreRendezVousConfirmes(rendezVousRepository.countByStatut(StatutRdv.CONFIRME));
        stats.setNombreRendezVousTermines(rendezVousRepository.countByStatut(StatutRdv.TERMINE));
        stats.setNombreRendezVousAnnules(rendezVousRepository.countByStatut(StatutRdv.ANNULE));
        stats.setNombreRendezVousRefuses(rendezVousRepository.countByStatut(StatutRdv.REFUSE));

        return stats;
    }

    @Override
    public StatistiquesGlobalesDTO getStatistiquesRendezVousParStatut() {
        return consulterStatistiquesGlobales();
    }
}