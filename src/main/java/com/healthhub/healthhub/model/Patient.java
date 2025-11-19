package com.healthhub.healthhub.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class Patient extends Utilisateur{
    private LocalDate dateNaissance ;
    private String adresse;
    private String telephone;

    public Patient() {
        setRole(Role.PATIENT);
    }

    public Patient(LocalDate dateNaissance, String adresse, String telephone) {
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        setRole(Role.PATIENT);
    }

    public Patient(Long id, String nom, String prenom, String email, String motDePasse, Role role, LocalDate dateNaissance, String adresse, String telephone) {
        super(id, nom, prenom, email, motDePasse, role);
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
