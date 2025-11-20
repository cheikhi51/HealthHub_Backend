package com.healthhub.healthhub.controller;

import com.healthhub.healthhub.model.Utilisateur;
import com.healthhub.healthhub.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    @GetMapping("/me")
    public ResponseEntity<Utilisateur> currentUser(Authentication authentication){
        String email = authentication.getName();
        Utilisateur user = utilisateurService.findByEmail(email);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/utilisateurs")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Utilisateur> getUtilisateurs(){
        return utilisateurService.getAllUtilisateur();
    }


    @GetMapping("/utilisateurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Utilisateur getUtilisateurById(@PathVariable("id") Long id){
        return utilisateurService.getUtilisateurById(id);
    }


    @PostMapping("/utilisateurs")
    @PreAuthorize("hasRole('ADMIN')")
    public  String createUtilisateur(@RequestBody Utilisateur utilisateur){
        utilisateurService.AjouterUtilisateur(utilisateur);
        return "Utilisateur créé avec succès";
    }
    @PutMapping("/utilisateurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUtilisateur(@PathVariable("id") Long id,@RequestBody Utilisateur utilisateur){
        utilisateurService.ModifierUtilisateurById(id,utilisateur);
        return "Utilisateur mis à jour avec succès";
    }
    @DeleteMapping("/utilisateurs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUtilisateur(@PathVariable("id") Long id){
        utilisateurService.SupprimerUtilisateurById(id);
        return "Utilisateur supprimé avec succès";
    }
}
