package com.healthhub.healthhub.dto;

import com.healthhub.healthhub.model.StatutRdv;
import java.time.Instant;

public class RendezVousCreatedDTO {
    private Long id;
    private Instant dateDebut;
    private Instant dateFin;
    private String motif;
    private StatutRdv statut;
    private MedecinAssigneDTO medecinAssigne;

    public RendezVousCreatedDTO() {
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public StatutRdv getStatut() {
        return statut;
    }

    public void setStatut(StatutRdv statut) {
        this.statut = statut;
    }

    public MedecinAssigneDTO getMedecinAssigne() {
        return medecinAssigne;
    }

    public void setMedecinAssigne(MedecinAssigneDTO medecinAssigne) {
        this.medecinAssigne = medecinAssigne;
    }

    // Classe interne pour les infos du médecin
    public static class MedecinAssigneDTO {
        private Long id;
        private String nom;
        private String prenom;
        private String specialite;
        private String telephone;

        public MedecinAssigneDTO() {
        }

        public MedecinAssigneDTO(Long id, String nom, String prenom, String specialite, String telephone) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.specialite = specialite;
            this.telephone = telephone;
        }

        // Getters et Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getSpecialite() {
            return specialite;
        }

        public void setSpecialite(String specialite) {
            this.specialite = specialite;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getNomComplet() {
            return "Dr. " + prenom + " " + nom;
        }
    }
}