package com.healthhub.healthhub.controller;

import com.healthhub.healthhub.dto.RendezVousCreatedDTO;
import com.healthhub.healthhub.dto.ReserverParSpecialiteDTO;
import com.healthhub.healthhub.model.Patient;
import com.healthhub.healthhub.model.Medecin;
import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.model.Notification;
import com.healthhub.healthhub.service.NotificationService;
import com.healthhub.healthhub.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:5173/")
public class PatientController {

    @Autowired
    private PatientService patientService;
    private NotificationService notificationService;

    // CRUD de base
    @PostMapping("/inscription")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<Patient> inscrire(@RequestBody Patient patient) {
        Patient nouveauPatient = patientService.inscrire(patient);
        return new ResponseEntity<>(nouveauPatient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<Patient> modifierProfil(@PathVariable Long id, @RequestBody Patient patient) {
        Patient patientModifie = patientService.modifierProfil(id, patient);
        return ResponseEntity.ok(patientModifie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<String> supprimerCompte(@PathVariable Long id) {
        String message = patientService.supprimerCompte(id);
        return ResponseEntity.ok(message);
    }

    // Fonctionnalités métier (use case)
    @GetMapping("/medecins")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<List<Medecin>> consulterListeMedecins() {
        List<Medecin> medecins = patientService.consulterListeMedecins();
        return ResponseEntity.ok(medecins);
    }

    @PostMapping("/{patientId}/rendez-vous")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<RendezVous> reserverRendezVous(
            @PathVariable Long patientId,
            @RequestBody RendezVous rendezVous) {
        RendezVous nouveauRdv = patientService.reserverRendezVous(patientId, rendezVous);
        return new ResponseEntity<>(nouveauRdv, HttpStatus.CREATED);
    }

    @DeleteMapping("/{patientId}/rendez-vous/{rdvId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<String> annulerRendezVous(
            @PathVariable Long patientId,
            @PathVariable Long rdvId) {
        String message = patientService.annulerRendezVous(patientId, rdvId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{patientId}/rendez-vous")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<List<RendezVous>> consulterHistoriqueRendezVous(
            @PathVariable Long patientId) {
        List<RendezVous> historique = patientService.consulterHistoriqueRendezVous(patientId);
        return ResponseEntity.ok(historique);
    }

    @GetMapping("/{patientId}/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<List<Notification>> recevoirNotifications(
            @PathVariable Long patientId) {
        List<Notification> notifications = patientService.recevoirNotifications(patientId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping("/rendez-vous/par-specialite")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<RendezVousCreatedDTO> reserverParSpecialite(
            @RequestBody ReserverParSpecialiteDTO dto) {
        RendezVousCreatedDTO rendezVous = patientService.reserverParSpecialite(dto);
        return new ResponseEntity<>(rendezVous, HttpStatus.CREATED);
    }

    @GetMapping("/specialites")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<List<String>> getSpecialitesDisponibles() {
        List<String> specialites = patientService.getSpecialitesDisponibles();
        return ResponseEntity.ok(specialites);
    }

    @PutMapping("/{patientId}/notifications/{notificationId}/lire")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public ResponseEntity<Notification> marquerNotificationCommeLue(
            @PathVariable Long patientId,
            @PathVariable Long notificationId) {
        Notification notification = patientService.marquerNotificationCommeLue(patientId, notificationId);
        return ResponseEntity.ok(notification);
    }
    @DeleteMapping("/{patientId}/notifications/{notificationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT')")
    public String deleteNotification(@PathVariable("patientId") Long patientId,@PathVariable("notificationId") Long notificationId){
        patientService.SupprimerNotification(patientId,notificationId);
        return "Notification supprimé avec succès";
    }
}