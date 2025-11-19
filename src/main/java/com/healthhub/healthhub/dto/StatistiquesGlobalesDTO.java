package com.healthhub.healthhub.dto;

public class StatistiquesGlobalesDTO {
    private Long nombreTotalPatients;
    private Long nombreTotalMedecins;
    private Long nombreTotalUtilisateurs;
    private Long nombreTotalRendezVous;
    private Long nombreRendezVousEnAttente;
    private Long nombreRendezVousConfirmes;
    private Long nombreRendezVousTermines;
    private Long nombreRendezVousAnnules;
    private Long nombreRendezVousRefuses;

    public StatistiquesGlobalesDTO() {
    }

    // Getters et Setters
    public Long getNombreTotalPatients() {
        return nombreTotalPatients;
    }

    public void setNombreTotalPatients(Long nombreTotalPatients) {
        this.nombreTotalPatients = nombreTotalPatients;
    }

    public Long getNombreTotalMedecins() {
        return nombreTotalMedecins;
    }

    public void setNombreTotalMedecins(Long nombreTotalMedecins) {
        this.nombreTotalMedecins = nombreTotalMedecins;
    }

    public Long getNombreTotalUtilisateurs() {
        return nombreTotalUtilisateurs;
    }

    public void setNombreTotalUtilisateurs(Long nombreTotalUtilisateurs) {
        this.nombreTotalUtilisateurs = nombreTotalUtilisateurs;
    }

    public Long getNombreTotalRendezVous() {
        return nombreTotalRendezVous;
    }

    public void setNombreTotalRendezVous(Long nombreTotalRendezVous) {
        this.nombreTotalRendezVous = nombreTotalRendezVous;
    }

    public Long getNombreRendezVousEnAttente() {
        return nombreRendezVousEnAttente;
    }

    public void setNombreRendezVousEnAttente(Long nombreRendezVousEnAttente) {
        this.nombreRendezVousEnAttente = nombreRendezVousEnAttente;
    }

    public Long getNombreRendezVousConfirmes() {
        return nombreRendezVousConfirmes;
    }

    public void setNombreRendezVousConfirmes(Long nombreRendezVousConfirmes) {
        this.nombreRendezVousConfirmes = nombreRendezVousConfirmes;
    }

    public Long getNombreRendezVousTermines() {
        return nombreRendezVousTermines;
    }

    public void setNombreRendezVousTermines(Long nombreRendezVousTermines) {
        this.nombreRendezVousTermines = nombreRendezVousTermines;
    }

    public Long getNombreRendezVousAnnules() {
        return nombreRendezVousAnnules;
    }

    public void setNombreRendezVousAnnules(Long nombreRendezVousAnnules) {
        this.nombreRendezVousAnnules = nombreRendezVousAnnules;
    }

    public Long getNombreRendezVousRefuses() {
        return nombreRendezVousRefuses;
    }

    public void setNombreRendezVousRefuses(Long nombreRendezVousRefuses) {
        this.nombreRendezVousRefuses = nombreRendezVousRefuses;
    }
}