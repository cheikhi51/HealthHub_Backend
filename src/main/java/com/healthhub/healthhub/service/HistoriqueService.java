package com.healthhub.healthhub.service;

import com.healthhub.healthhub.model.Historique;
import com.healthhub.healthhub.model.Utilisateur;
import java.time.Instant;
import java.util.List;

public interface HistoriqueService {

    // CRUD de base
    Historique getHistoriqueById(Long id);
    List<Historique> getAllHistorique();
    Historique AjouterHistorique(Historique historique);
    Historique ModifierHistoriqueById(Long id, Historique historique);
    void SupprimerHistoriqueById(Long id);

    // Méthodes métier
    List<Historique> getHistoriqueUtilisateur(Long utilisateurId);
    List<Historique> getHistoriqueParAction(Long utilisateurId, String actionType);
    List<Historique> getHistoriqueParPeriode(Long utilisateurId, Instant dateDebut, Instant dateFin);
    List<Historique> getDernieresActions(Long utilisateurId, int limite);
    long compterActionsUtilisateur(Long utilisateurId);

    // Méthode utilitaire pour enregistrer une action
    void enregistrerAction(Utilisateur utilisateur, String actionType, String details);
    void enregistrerAction(Long utilisateurId, String actionType, String details);
}