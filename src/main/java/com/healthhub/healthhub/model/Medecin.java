package com.healthhub.healthhub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "medecin")
public class Medecin extends Utilisateur{
    private String specialite;
    private Boolean disponibilite;
    @Column(name = "telephone")
    private String telephone;

    public Medecin(){
        setRole(Role.MEDCIN);
    }

    public Medecin(String specialite, Boolean disponibilite,String telephone) {
        this.specialite = specialite;
        this.disponibilite = disponibilite;
        this.telephone = telephone;
        setRole(Role.MEDCIN);
    }

    public Medecin(Long id, String nom, String prenom, String email, String motDePasse, Role role, String specialite, Boolean disponibilite) {
        super(id, nom, prenom, email, motDePasse, role);
        this.specialite = specialite;
        this.disponibilite = disponibilite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Boolean getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
