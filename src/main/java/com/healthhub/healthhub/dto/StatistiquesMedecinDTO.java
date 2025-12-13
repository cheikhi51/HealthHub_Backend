package com.healthhub.healthhub.dto;

public class StatistiquesMedecinDTO {
    private Long nombreTotalRendezVous;
    private Long nombreRendezVousEnAttente;
    private Long nombreRendezVousConfirmes;
    private Long nombreRendezVousTermines;
    private Long nombreRendezVousAnnules;
    private Long nombreRendezVousRefuses;
    private Long nombrePatientsUniques;

    public StatistiquesMedecinDTO() {
    }

    public StatistiquesMedecinDTO(Long nombreTotalRendezVous, Long nombreRendezVousEnAttente,
                                  Long nombreRendezVousConfirmes, Long nombreRendezVousTermines,
                                  Long nombreRendezVousAnnules,Long nombreRendezVousRefuses, Long nombrePatientsUniques) {
        this.nombreTotalRendezVous = nombreTotalRendezVous;
        this.nombreRendezVousEnAttente = nombreRendezVousEnAttente;
        this.nombreRendezVousConfirmes = nombreRendezVousConfirmes;
        this.nombreRendezVousTermines = nombreRendezVousTermines;
        this.nombreRendezVousAnnules = nombreRendezVousAnnules;
        this.nombreRendezVousRefuses = nombreRendezVousRefuses;
        this.nombrePatientsUniques = nombrePatientsUniques;
    }

    // Getters et Setters
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

    public Long getNombrePatientsUniques() {
        return nombrePatientsUniques;
    }

    public Long getNombreRendezVousRefuses() {
        return nombreRendezVousRefuses;
    }

    public void setNombreRendezVousRefuses(Long nombreRendezVousRefuses) {
        this.nombreRendezVousRefuses = nombreRendezVousRefuses;
    }

    public void setNombrePatientsUniques(Long nombrePatientsUniques) {
        this.nombrePatientsUniques = nombrePatientsUniques;
    }
}