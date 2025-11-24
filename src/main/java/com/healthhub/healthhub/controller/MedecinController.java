package com.healthhub.healthhub.controller;

import com.healthhub.healthhub.model.Medecin;
import com.healthhub.healthhub.model.Patient;
import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.dto.StatistiquesMedecinDTO;
import com.healthhub.healthhub.service.MedecinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Medecin> ajouterMedecin(@RequestBody Medecin medecin) {
        Medecin nouveauMedecin = medecinService.ajouterMedecin(medecin);
        return new ResponseEntity<>(nouveauMedecin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medecin> getMedecinById(@PathVariable Long id) {
        Medecin medecin = medecinService.getMedecinById(id);
        return ResponseEntity.ok(medecin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medecin> modifierProfil(@PathVariable Long id, @RequestBody Medecin medecin) {
        Medecin medecinModifie = medecinService.modifierProfil(id, medecin);
        return ResponseEntity.ok(medecinModifie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerMedecin(@PathVariable Long id) {
        String message = medecinService.supprimerMedecin(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<Medecin>> getAllMedecins() {
        List<Medecin> medecins = medecinService.getAllMedecins();
        return ResponseEntity.ok(medecins);
    }

    // Fonctionnalités métier (use case)
    @GetMapping("/{medecinId}/rendez-vous")
    public ResponseEntity<List<RendezVous>> gererRendezVous(@PathVariable Long medecinId) {
        List<RendezVous> rendezVous = medecinService.gererRendezVous(medecinId);
        return ResponseEntity.ok(rendezVous);
    }

    @PutMapping("/{medecinId}/rendez-vous/{rdvId}/valider")
    public ResponseEntity<String> validerRendezVous(
            @PathVariable Long medecinId,
            @PathVariable Long rdvId) {
        String message = medecinService.validerRendezVous(medecinId, rdvId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{medecinId}/rendez-vous/{rdvId}/refuser")
    public ResponseEntity<String> refuserRendezVous(
            @PathVariable Long medecinId,
            @PathVariable Long rdvId) {
        String message = medecinService.refuserRendezVous(medecinId, rdvId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{medecinId}/rendez-vous/{rdvId}/completer")
    public ResponseEntity<String> completerRendezVous(
            @PathVariable Long medecinId,
            @PathVariable Long rdvId) {
        String message = medecinService.completerRendezVous(medecinId, rdvId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{medecinId}/patients")
    public ResponseEntity<List<Patient>> getMesPatients(@PathVariable Long medecinId) {
        List<Patient> patients = medecinService.getMesPatients(medecinId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{medecinId}/statistiques")
    public ResponseEntity<StatistiquesMedecinDTO> consulterStatistiques(@PathVariable Long medecinId) {
        StatistiquesMedecinDTO stats = medecinService.consulterStatistiques(medecinId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{medecinId}/rendez-vous/en-attente")
    public ResponseEntity<List<RendezVous>> getRendezVousEnAttente(@PathVariable Long medecinId) {
        List<RendezVous> rendezVous = medecinService.getRendezVousEnAttente(medecinId);
        return ResponseEntity.ok(rendezVous);
    }

    @GetMapping("/{medecinId}/rendez-vous/confirmes")
    public ResponseEntity<List<RendezVous>> getRendezVousConfirmes(@PathVariable Long medecinId) {
        List<RendezVous> rendezVous = medecinService.getRendezVousConfirmes(medecinId);
        return ResponseEntity.ok(rendezVous);
    }
}