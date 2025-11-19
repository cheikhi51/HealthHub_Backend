package com.healthhub.healthhub.dto;

import java.time.Instant;

public class ReserverParSpecialiteDTO {
    private String specialite;
    private Instant dateDebut;
    private Instant dateFin;
    private String motif;

    public ReserverParSpecialiteDTO() {
    }

    public ReserverParSpecialiteDTO(String specialite, Instant dateDebut, Instant dateFin, String motif) {
        this.specialite = specialite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.motif = motif;
    }

    // Getters et Setters
    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}