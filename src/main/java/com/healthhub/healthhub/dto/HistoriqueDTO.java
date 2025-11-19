package com.healthhub.healthhub.dto;

import com.healthhub.healthhub.model.Historique;
import java.time.Instant;

public class HistoriqueDTO {
    private Long id;
    private Instant dateAction;
    private String actionType;
    private String details;
    private UtilisateurSimpleDTO utilisateur;

    public HistoriqueDTO() {
    }

    public HistoriqueDTO(Historique historique) {
        this.id = historique.getId();
        this.dateAction = historique.getDateAction();
        this.actionType = historique.getActionType();
        this.details = historique.getDetails();

        if (historique.getUtilisateur() != null) {
            this.utilisateur = new UtilisateurSimpleDTO(
                    historique.getUtilisateur().getId(),
                    historique.getUtilisateur().getNom(),
                    historique.getUtilisateur().getPrenom(),
                    historique.getUtilisateur().getEmail(),
                    historique.getUtilisateur().getRole()
            );
        }
    }

    // Classe interne pour les infos utilisateur
    public static class UtilisateurSimpleDTO {
        private Long id;
        private String nom;
        private String prenom;
        private String email;
        private String role;

        public UtilisateurSimpleDTO(Long id, String nom, String prenom, String email, Object role) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.role = role != null ? role.toString() : null;
        }

        // Getters et Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Instant getDateAction() { return dateAction; }
    public void setDateAction(Instant dateAction) { this.dateAction = dateAction; }
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public UtilisateurSimpleDTO getUtilisateur() { return utilisateur; }
    public void setUtilisateur(UtilisateurSimpleDTO utilisateur) { this.utilisateur = utilisateur; }
}