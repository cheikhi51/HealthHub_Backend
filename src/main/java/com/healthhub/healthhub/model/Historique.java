package com.healthhub.healthhub.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique")
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant dateAction = Instant.now();

    @Column(nullable = false)
    private String actionType;

    @Column(length = 2000)
    private String details;

    @ManyToOne
    private Utilisateur utilisateur;

    public Historique() {
    }

    public Historique(Long id, Instant dateAction, String actionType, String details, Utilisateur utilisateur) {
        this.id = id;
        this.dateAction = dateAction;
        this.actionType = actionType;
        this.details = details;
        this.utilisateur = utilisateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateAction() {
        return dateAction;
    }

    public void setDateAction(Instant dateAction) {
        this.dateAction = dateAction;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
