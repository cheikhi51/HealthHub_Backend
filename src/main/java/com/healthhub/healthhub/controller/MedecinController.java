package com.healthhub.healthhub.controller;

import com.healthhub.healthhub.model.Medecin;
import com.healthhub.healthhub.model.Patient;
import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.dto.StatistiquesMedecinDTO;
import com.healthhub.healthhub.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@CrossOrigin(origins = "http://localhost:5173/")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;

    // CRUD de base
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<Medecin> ajouterMedecin(@RequestBody Medecin medecin) {
        Medecin nouveauMedecin = medecinService.ajouterMedecin(medecin);
        return new ResponseEntity<>(nouveauMedecin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<Medecin> getMedecinById(@PathVariable Long id) {
        Medecin medecin = medecinService.getMedecinById(id);
        return ResponseEntity.ok(medecin);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<Medecin> modifierProfil(@PathVariable Long id, @RequestBody Medecin medecin) {
        Medecin medecinModifie = medecinService.modifierProfil(id, medecin);
        return ResponseEntity.ok(medecinModifie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<String> supprimerMedecin(@PathVariable Long id) {
        String message = medecinService.supprimerMedecin(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<List<Medecin>> getAllMedecins() {
        List<Medecin> medecins = medecinService.getAllMedecins();
        return ResponseEntity.ok(medecins);
    }

    // Fonctionnalités métier (use case)
    @GetMapping("/{medecinId}/rendez-vous")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<List<RendezVous>> gererRendezVous(@PathVariable Long medecinId) {
        List<RendezVous> rendezVous = medecinService.gererRendezVous(medecinId);
        return ResponseEntity.ok(rendezVous);
    }

    @PutMapping("/{medecinId}/rendez-vous/{rdvId}/valider")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<String> validerRendezVous(
            @PathVariable Long medecinId,
            @PathVariable Long rdvId) {
        String message = medecinService.validerRendezVous(medecinId, rdvId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{medecinId}/rendez-vous/{rdvId}/refuser")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<String> refuserRendezVous(
            @PathVariable Long medecinId,
            @PathVariable Long rdvId) {
        String message = medecinService.refuserRendezVous(medecinId, rdvId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{medecinId}/rendez-vous/{rdvId}/completer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<String> completerRendezVous(
            @PathVariable Long medecinId,
            @PathVariable Long rdvId) {
        String message = medecinService.completerRendezVous(medecinId, rdvId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{medecinId}/patients")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<List<Patient>> getMesPatients(@PathVariable Long medecinId) {
        List<Patient> patients = medecinService.getMesPatients(medecinId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{medecinId}/statistiques")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<StatistiquesMedecinDTO> consulterStatistiques(@PathVariable Long medecinId) {
        StatistiquesMedecinDTO stats = medecinService.consulterStatistiques(medecinId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{medecinId}/rendez-vous/en-attente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<List<RendezVous>> getRendezVousEnAttente(@PathVariable Long medecinId) {
        List<RendezVous> rendezVous = medecinService.getRendezVousEnAttente(medecinId);
        return ResponseEntity.ok(rendezVous);
    }

    @GetMapping("/{medecinId}/rendez-vous/confirmes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEDCIN')")
    public ResponseEntity<List<RendezVous>> getRendezVousConfirmes(@PathVariable Long medecinId) {
        List<RendezVous> rendezVous = medecinService.getRendezVousConfirmes(medecinId);
        return ResponseEntity.ok(rendezVous);
    }
}