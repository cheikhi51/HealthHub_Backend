package com.healthhub.healthhub.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "rendez_vous")
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Patient patient;

    @ManyToOne(optional = false)
    private Medecin medecin;

    @Column(nullable = false)
    private Instant dateDebut;

    @Column(nullable = false)
    private Instant dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutRdv statut = StatutRdv.EN_ATTENTE;

    private String motif;

    public RendezVous() {
    }

    public RendezVous(Long id, Patient patient, Medecin medecin, Instant dateDebut, Instant dateFin, StatutRdv statut, String motif) {
        this.id = id;
        this.patient = patient;
        this.medecin = medecin;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.motif = motif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
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

    public StatutRdv getStatut() {
        return statut;
    }

    public void setStatut(StatutRdv statut) {
        this.statut = statut;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
