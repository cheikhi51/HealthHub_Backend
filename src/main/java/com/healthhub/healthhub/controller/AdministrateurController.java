package com.healthhub.healthhub.controller;

import com.healthhub.healthhub.dto.StatistiquesGlobalesDTO;
import com.healthhub.healthhub.model.*;
import com.healthhub.healthhub.service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
@CrossOrigin(origins = "http://localhost:5173/")
public class AdministrateurController {

    @Autowired
    private AdministrateurService administrateurService;

    // ===== GESTION DES ADMINISTRATEURS =====

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Administrateur> ajouterAdmin(@RequestBody Administrateur admin) {
        Administrateur nouvelAdmin = administrateurService.ajouterAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelAdmin);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Administrateur> getAdminById(@PathVariable Long id) {
        Administrateur admin = administrateurService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Administrateur>> getAllAdmins() {
        List<Administrateur> admins = administrateurService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> supprimerAdmin(@PathVariable Long id) {
        String message = administrateurService.supprimerAdmin(id);
        return ResponseEntity.ok(message);
    }

    // ===== GESTION DES UTILISATEURS =====

    @GetMapping("/utilisateurs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Utilisateur>> gererUtilisateurs() {
        List<Utilisateur> utilisateurs = administrateurService.gererUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/utilisateurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Utilisateur utilisateur = administrateurService.getUtilisateurById(id);
        return ResponseEntity.ok(utilisateur);
    }

    @DeleteMapping("/utilisateurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> supprimerUtilisateur(@PathVariable Long id) {
        String message = administrateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/utilisateurs/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activerUtilisateur(@PathVariable Long id) {
        String message = administrateurService.activerUtilisateur(id);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/utilisateurs/{id}/desactiver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> desactiverUtilisateur(@PathVariable Long id) {
        String message = administrateurService.desactiverUtilisateur(id);
        return ResponseEntity.ok(message);
    }

    // ===== GESTION DES MÉDECINS =====

    @PostMapping("/medecins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Medecin> ajouterMedecin(@RequestBody Medecin medecin) {
        Medecin nouveauMedecin = administrateurService.ajouterMedecin(medecin);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauMedecin);
    }

    @GetMapping("/medecins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Medecin>> getAllMedecins() {
        List<Medecin> medecins = administrateurService.getAllMedecins();
        return ResponseEntity.ok(medecins);
    }

    @GetMapping("/medecins/specialite/{specialite}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Medecin>> getMedecinsBySpecialite(@PathVariable String specialite) {
        List<Medecin> medecins = administrateurService.getMedecinsBySpecialite(specialite);
        return ResponseEntity.ok(medecins);
    }

    // ===== GESTION DES PATIENTS =====

    @PostMapping("/patients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> ajouterPatient(@RequestBody Patient patient) {
        Patient nouveauPatient = administrateurService.ajouterPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauPatient);
    }

    @GetMapping("/patients")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = administrateurService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    // ===== GESTION DES RENDEZ-VOUS =====

    @GetMapping("/rendez-vous")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RendezVous>> getAllRendezVous() {
        List<RendezVous> rendezVous = administrateurService.getAllRendezVous();
        return ResponseEntity.ok(rendezVous);
    }

    @DeleteMapping("/rendez-vous/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> supprimerRendezVous(@PathVariable Long id) {
        String message = administrateurService.supprimerRendezVous(id);
        return ResponseEntity.ok(message);
    }

    // ===== STATISTIQUES =====

    @GetMapping("/statistiques")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatistiquesGlobalesDTO> consulterStatistiquesGlobales() {
        StatistiquesGlobalesDTO stats = administrateurService.consulterStatistiquesGlobales();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/statistiques/rendez-vous")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatistiquesGlobalesDTO> getStatistiquesRendezVousParStatut() {
        StatistiquesGlobalesDTO stats = administrateurService.getStatistiquesRendezVousParStatut();
        return ResponseEntity.ok(stats);
    }
}