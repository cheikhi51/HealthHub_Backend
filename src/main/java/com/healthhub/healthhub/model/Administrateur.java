package com.healthhub.healthhub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrateur")
public class Administrateur extends Utilisateur{
    private String telephone;
    public Administrateur() { setRole(Role.ADMIN); }

    public Administrateur(String telephone) {
        setRole(Role.ADMIN);
        this.telephone = telephone;
    }

    public Administrateur(Long id, String nom, String prenom, String email, String motDePasse, Role role ,String telephone) {
        super(id, nom, prenom, email, motDePasse, role);
        this.telephone = telephone;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
