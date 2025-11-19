package com.healthhub.healthhub.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "utilisateur_id")
    private Long utilisateurId;
    private String message;
    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi;
    @Column(name = "date_lecture")
    private LocalDateTime dateLecture;
    private Boolean lu;

    public Notification() {
    }

    public Notification(Long id, Long utilisateurId, String message, LocalDateTime dateEnvoi, LocalDateTime dateLecture, Boolean lu) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
        this.dateLecture = dateLecture;
        this.lu = lu;
    }

    public LocalDateTime getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(LocalDateTime dateLecture) {
        this.dateLecture = dateLecture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Boolean getLu() {
        return lu;
    }

    public void setLu(Boolean lu) {
        this.lu = lu;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

}
