package com.healthhub.healthhub.controller;

import com.healthhub.healthhub.dto.HistoriqueDTO;
import com.healthhub.healthhub.model.Historique;
import com.healthhub.healthhub.service.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historiques")
@CrossOrigin(origins = "http://localhost:5173/")
public class HistoriqueController {

    @Autowired
    private HistoriqueService historiqueService;

    // ============ CRUD de Base ============

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoriqueDTO> getHistoriqueById(@PathVariable Long id) {
        Historique historique = historiqueService.getHistoriqueById(id);
        return ResponseEntity.ok(new HistoriqueDTO(historique));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HistoriqueDTO>> getAllHistorique() {
        List<HistoriqueDTO> historiques = historiqueService.getAllHistorique()
                .stream()
                .map(HistoriqueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiques);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoriqueDTO> createHistorique(@RequestBody Historique historique) {
        Historique nouvelHistorique = historiqueService.AjouterHistorique(historique);
        return new ResponseEntity<>(new HistoriqueDTO(nouvelHistorique), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HistoriqueDTO> updateHistorique(
            @PathVariable Long id,
            @RequestBody Historique historique) {
        Historique historiqueModifie = historiqueService.ModifierHistoriqueById(id, historique);
        return ResponseEntity.ok(new HistoriqueDTO(historiqueModifie));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteHistorique(@PathVariable Long id) {
        historiqueService.SupprimerHistoriqueById(id);
        return ResponseEntity.ok("Historique supprimé avec succès");
    }

    // ============ Endpoints Métier ============

    @GetMapping("/utilisateur/{utilisateurId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public ResponseEntity<List<HistoriqueDTO>> getHistoriqueUtilisateur(
            @PathVariable Long utilisateurId) {
        List<HistoriqueDTO> historiques = historiqueService.getHistoriqueUtilisateur(utilisateurId)
                .stream()
                .map(HistoriqueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiques);
    }


    @GetMapping("/utilisateur/{utilisateurId}/action/{actionType}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public ResponseEntity<List<HistoriqueDTO>> getHistoriqueParAction(
            @PathVariable Long utilisateurId,
            @PathVariable String actionType) {
        List<HistoriqueDTO> historiques = historiqueService
                .getHistoriqueParAction(utilisateurId, actionType)
                .stream()
                .map(HistoriqueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiques);
    }

    @GetMapping("/utilisateur/{utilisateurId}/periode")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public ResponseEntity<List<HistoriqueDTO>> getHistoriqueParPeriode(
            @PathVariable Long utilisateurId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fin) {
        List<HistoriqueDTO> historiques = historiqueService
                .getHistoriqueParPeriode(utilisateurId, debut, fin)
                .stream()
                .map(HistoriqueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiques);
    }


    @GetMapping("/utilisateur/{utilisateurId}/dernieres")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public ResponseEntity<List<HistoriqueDTO>> getDernieresActions(
            @PathVariable Long utilisateurId,
            @RequestParam(defaultValue = "10") int limite) {
        List<HistoriqueDTO> historiques = historiqueService
                .getDernieresActions(utilisateurId, limite)
                .stream()
                .map(HistoriqueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiques);
    }

    @GetMapping("/utilisateur/{utilisateurId}/count")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public ResponseEntity<Map<String, Object>> compterActionsUtilisateur(
            @PathVariable Long utilisateurId) {
        long count = historiqueService.compterActionsUtilisateur(utilisateurId);
        Map<String, Object> response = new HashMap<>();
        response.put("utilisateurId", utilisateurId);
        response.put("nombreActions", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/utilisateur/{utilisateurId}/recent")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public ResponseEntity<List<HistoriqueDTO>> getHistoriqueRecent(
            @PathVariable Long utilisateurId) {
        Instant maintenant = Instant.now();
        Instant il7Jours = maintenant.minus(7, ChronoUnit.DAYS);

        List<HistoriqueDTO> historiques = historiqueService
                .getHistoriqueParPeriode(utilisateurId, il7Jours, maintenant)
                .stream()
                .map(HistoriqueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiques);
    }

    @GetMapping("/utilisateur/{utilisateurId}/mois-courant")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public ResponseEntity<List<HistoriqueDTO>> getHistoriqueMoisCourant(
            @PathVariable Long utilisateurId) {
        Instant maintenant = Instant.now();
        Instant debutMois = maintenant.truncatedTo(ChronoUnit.DAYS)
                .minus(maintenant.atZone(java.time.ZoneId.systemDefault()).getDayOfMonth() - 1, ChronoUnit.DAYS);

        List<HistoriqueDTO> historiques = historiqueService
                .getHistoriqueParPeriode(utilisateurId, debutMois, maintenant)
                .stream()
                .map(HistoriqueDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiques);
    }
}